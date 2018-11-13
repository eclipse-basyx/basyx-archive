package org.eclipse.basyx.vab.backend.server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.tools.VABPathTools;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * VAB provider class that enables access to an IModelProvider via HTTP REST
 * interface<br />
 * <br />
 * REST http interface is as following: <br />
 * - GET /aas/submodels/{subModelId} Retrieves submodel with ID {subModelId}
 * <br />
 * - GET /aas/submodels/{subModelId}/properties/a Retrieve property a of
 * submodel {subModelId}<br />
 * - GET /aas/submodels/{subModelId}/properties/a/b Retrieve property a/b of
 * submodel {subModelId} <br />
 * - POST /aas/submodels/{subModelId}/operations/a Invoke operation a of
 * submodel {subModelId}<br />
 * - POST /aas/submodels/{subModelId}/operations/a/b Invoke operation a/b of
 * submodel {subModelId}
 * 
 * @author kuhn
 *
 */
public class VABHTTPInterface<T extends IModelProvider> extends BasysHTTPServelet {

	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference to IModelProvider backend
	 * 
	 * FIXME: Create generic interface class for providers
	 */
	protected T providerBackend = null;

	/**
	 * Constructor
	 */
	public VABHTTPInterface(T provider) {
		// Store provider reference
		providerBackend = provider;
	}

	/**
	 * Access model provider
	 */
	public T getModelProvider() {
		return providerBackend;
	}

	/**
	 * Implement "Get" operation
	 * 
	 * Process HTTP get request - get sub model property value
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Process HTTP request
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.substring(contextPath.length() + req.getServletPath().length() + 1);

		// Forward request to provider
		Object result = providerBackend.getModelPropertyValue(path);

		System.out.println("Result1:" + result);

		// Create return value
		JSONObject returnValue = new JSONObject();

		// Serialize value
		returnValue = JSONTools.Instance.serialize(result);

		System.out.println("Result2:" + returnValue);

		// Setup HTML response header
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		// Send response
		sendJSONResponse(path, resp.getWriter(), returnValue);
	}

	/**
	 * Implement "Set" operation
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Extract path
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.substring(contextPath.length() + req.getServletPath().length());// Note, removed +1

		// Read request body
		InputStreamReader reader = new InputStreamReader(req.getInputStream());
		BufferedReader bufReader = new BufferedReader(reader);
		StringBuilder serValue = new StringBuilder();
		while (bufReader.ready())
			serValue.append(bufReader.readLine());

		// De-serialize JSON body. If parameter is null, an exception has been sent
		Object parameter = extractParameter(path, serValue.toString(), resp.getWriter());

		// Try to set value of BaSys VAB element
		try {
			// Change property value
			providerBackend.setModelPropertyValue(path, parameter);
		} catch (Exception e) {
			sendException(e, path, resp.getWriter());
		}
	}

	/**
	 * Extracts parameter from JSON and handles de-serialization errors
	 * 
	 * @param path
	 * @param serializedJSONValue
	 * @param outputStream
	 * @return De-serialized parameter
	 * 
	 * @throws ServerException
	 */
	protected Object extractParameter(String path, String serializedJSONValue, PrintWriter outputStream) {
		// Return value
		Object result = null;

		// Null pointer check
		if ((serializedJSONValue == null) || (serializedJSONValue.trim().length() == 0))
			return null;

		// Deserialize JSON body
		try {
			JSONObject json = new JSONObject(serializedJSONValue.toString());
			result = JSONTools.Instance.deserialize(json);

		} catch (JSONException e) {
			sendException(new IllegalArgumentException("Invalid PUT paramater"), path, outputStream);
		}
		return result;
	}

	/**
	 * Send JSON encoded response
	 */
	protected void sendJSONResponse(String path, PrintWriter outputStream, JSONObject jsonValue) {
		// Output result
		outputStream.write(jsonValue.toString()); // FIXME throws nullpointer exception if jsonValue is null
		outputStream.flush();
	}

	/**
	 * Send Error
	 * 
	 * @param e
	 * @param path
	 * @param resp
	 */
	private void sendException(Exception e, String path, PrintWriter resp) {
		// Serialize exception
		JSONObject error = JSONTools.Instance.serialize(e);

		// Send error response
		sendJSONResponse(path, resp, error);
	}

	/**
	 * <pre>
	 * Handle HTTP POST operation. Creates a new Property, Operation, Event,
	 * Submodel or AAS or invokes an operation.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Extract path
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.substring(contextPath.length() + req.getServletPath().length());// Note, removed +1

		// Read posted parameter
		InputStreamReader reader = new InputStreamReader(req.getInputStream());
		BufferedReader bufReader = new BufferedReader(reader);
		StringBuilder serValue = new StringBuilder();
		while (bufReader.ready()) {
			serValue.append(bufReader.readLine());
		}

		// Deserialize json body. If parameter is null, an exception has been sent
		Object parameter = extractParameter(path, serValue.toString(), resp.getWriter());

		// Invoke provider backend
		try {
			// Check if request is for property creation or operation invoke
			if (VABPathTools.isOperationPath(path)) {
				// Invoke BaSys VAB 'invoke' primitive
				Object result = providerBackend.invokeOperation(path, (Object[]) parameter);

				// Create return value
				JSONObject returnValue = new JSONObject();

				// Serialize value
				returnValue = JSONTools.Instance.serialize(result);

				// Setup HTML response header
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");

				// Send response
				sendJSONResponse(path, resp.getWriter(), returnValue);
			} else {
				// Invoke the BaSys create primitive
				providerBackend.createValue(path, ((Object[]) parameter)[0]);
			}
		} catch (Exception e) {
			sendException(e, path, resp.getWriter());
		}
	}

	/**
	 * Handle a HTTP PATCH operation. Updates a map or collection respective to
	 * action string.
	 * 
	 */
	@Override
	protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Extract path
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.substring(contextPath.length() + req.getServletPath().length());// Note, removed +1

		// Read request body
		InputStreamReader reader = new InputStreamReader(req.getInputStream());
		BufferedReader bufReader = new BufferedReader(reader);
		StringBuilder serValue = new StringBuilder();
		while (bufReader.ready())
			serValue.append(bufReader.readLine());

		// Deserialize json body. If parameter is null, an exception has been sent
		Object[] parameter = (Object[]) extractParameter(path, serValue.toString(), resp.getWriter());

		// Extract action parameter
		String action = req.getParameter("action");

		try {
			switch (action.toLowerCase()) {
			/**
			 * Add an element to a collection / key-value pair to a map
			 */
			case "add":
				providerBackend.setModelPropertyValue(path, parameter);
				break;

			/**
			 * Remove an element from a collection by index / remove from map by key. We
			 * know parameter must only contain 1 element
			 */
			case "remove":
				providerBackend.deleteValue(path, parameter[0]);
				break;

			default:
				sendException(new ServerException("Unsupported", "Action not supported."), path, resp.getWriter());
			}

		} catch (Exception e) {
			sendException(e, path, resp.getWriter());
		}
	}

	/**
	 * Implement "Delete" operation. Deletes any resource under the given path.
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Extract path
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String path = uri.substring(contextPath.length() + req.getServletPath().length());// Note, removed +1

		// Read request body
		InputStreamReader reader = new InputStreamReader(req.getInputStream());
		BufferedReader bufReader = new BufferedReader(reader);
		StringBuilder serValue = new StringBuilder();
		while (bufReader.ready())
			serValue.append(bufReader.readLine());

		System.out.println("Delete0:" + path);

		// Deserialize json body. If parameter is null, an exception has been sent
		Object parameter = extractParameter(path, serValue.toString(), resp.getWriter());

		System.out.println("Delete1:" + parameter);
		// Invoke provider backend
		try {
			// Process delete request with or without argument
			if (parameter == null) {
				this.providerBackend.deleteValue(path);
			} else {
				System.out.println("Delete:" + parameter);
				this.providerBackend.deleteValue(path, parameter);
			}
		} catch (Exception e) {
			e.printStackTrace();

			sendException(e, path, resp.getWriter());
		}
	}
}
