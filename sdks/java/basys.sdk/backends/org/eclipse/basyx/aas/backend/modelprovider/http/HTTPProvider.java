package org.eclipse.basyx.aas.backend.modelprovider.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.json.JSONObject;



/**
 * Provider class that enables access to an IModelProvider via HTTP REST interface 
 * 
 * @author kuhn
 *
 */
public class HTTPProvider<T extends IModelProvider> extends HttpServlet {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Reference to IModelProvider backend
	 */
	protected T providerBackend = null;
	
	
	
	
	
	/**
	 * Constructor
	 */
	public HTTPProvider(T modelProviderBackend) {
		// Store reference to backend
		providerBackend = modelProviderBackend;
	}
	
	
	/**
	 * Get backend reference
	 */
	public T getBackendReference() {
		return providerBackend;
	}




	/**
	 * Send JSON encoded response
	 * 
	 * TODO remove this function because it is unused
	 */
	protected void sendJSONResponse(String path, HttpServletResponse resp, Object value) {
		try {
			// Setup HTML response header
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			// - Create JSON result Object 
			JSONObject json = new JSONObject();
			// - Put value pairs into the JSON object. For now we return our return value in field "value"
			json.put("value", value);
			// - Output result
			PrintWriter out = resp.getWriter();
			out.write(json.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	/**
	 * Send JSON encoded response
	 */
	protected void sendJSONResponse(String path, HttpServletResponse resp, JSONObject jsonValue) {
		try {
			// Setup HTML response header
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");

			// Output result
			PrintWriter out = resp.getWriter();
			out.write(jsonValue.toString()); //FIXME throws nullpointer exception if jsonValue is null
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	/**
	 * Implement "Get" operation 
	 * 
	 * Process HTTP get request - get sub model property value
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("-------------------------- DO GET ---------------------------------------------------------");
		
		
		// Access parameters
		String path       = (String) req.getParameter("path"); 
		
		// Initialize JSON object
		JSONObject jsonObj = JSONTools.Instance.serializeProperty(path, providerBackend);
		System.out.println(">>");
		System.out.println(">> GOT: " +jsonObj);
		System.out.println(">>");		
		System.out.println(">> SEND RESPONSE");		
		
		// Send HTML response
		sendJSONResponse(path, resp, jsonObj);	
		
		// We did process the request
		return;
	}

	
	
	/**
	 * Implement "Set" operation
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Read posted parameter
		InputStreamReader reader    = new InputStreamReader(req.getInputStream());
		BufferedReader    bufReader = new BufferedReader(reader);
		StringBuilder     serValue  = new StringBuilder(); 
		
		// Read values
		while (bufReader.ready()) serValue.append(bufReader.readLine());
		
		// Access parameters from request header
		String path       = (String) req.getParameter("path"); 
		Object newValue   = JSONTools.Instance.deserialize(new JSONObject(serValue.toString()));
		
		System.out.println("Put: " + path + " " + newValue);
		
		// Increment Clock
		String submodelPath = path.substring(0, path.indexOf("/"));
		providerBackend.setModelPropertyValue(submodelPath +"/clock", (Integer) providerBackend.getModelPropertyValue(submodelPath +"/clock") + 1);
				
		// Update property value
		providerBackend.setModelPropertyValue(path, newValue);
	}


	
	/**
	 * Implement "Create" and "Invoke" operations
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Read posted parameter
		InputStreamReader reader    = new InputStreamReader(req.getInputStream());
		BufferedReader    bufReader = new BufferedReader(reader);
		StringBuilder     serValue  = new StringBuilder(); 
		// Read values
		while (bufReader.ready()) serValue.append(bufReader.readLine());
		
		// Access parameters from request header
		String path       = (String) req.getParameter("path"); 
		String operation  = (String) req.getParameter("op"); 
		System.out.println("DoPost: "+path+" - op: "+operation+"  - par: "+ serValue.toString());
		
		System.out.println("DS: "+serValue.toString());
		Object[] parameter  = (Object []) JSONTools.Instance.deserialize(new JSONObject(serValue.toString()));
		String submodelPath = path.substring(0, path.indexOf("/"));
		
		// Perform operation
		switch (operation) {
			case "create": 
				// Increment Clock
				providerBackend.setModelPropertyValue(submodelPath +"/clock", (Integer) providerBackend.getModelPropertyValue(submodelPath +"/clock") + 1);
				
				// Update data
				providerBackend.createValue(path, parameter); 
				break;
				
			case "delete": 
				// Increment Clock
				providerBackend.setModelPropertyValue(submodelPath +"/clock", (Integer) providerBackend.getModelPropertyValue(submodelPath +"/clock") + 1);
				
				// Update data
				providerBackend.deleteValue(path, parameter); 
				break;
			
			case "invoke": {
				System.out.println("Invoking1:"+path);
				System.out.println("Invoking2:"+parameter);
				Object result = providerBackend.invokeOperation(path, parameter);
				
				System.out.println("Invoking3:"+result);
				
				JSONObject jsonObj2 = JSONTools.Instance.serialize(result);
				// Send HTML response
				sendJSONResponse(path, resp, jsonObj2);
				break;
			}
			
			default:
				throw new IllegalArgumentException("Action not supported.");
		}
	}
	
	
	/**
	 * Implement "Delete" operations TODO remove this function since delete is already handled by post
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Read posted parameter
		InputStreamReader reader    = new InputStreamReader(req.getInputStream());
		BufferedReader    bufReader = new BufferedReader(reader);
		StringBuilder     serValue  = new StringBuilder(); 
		
		// Read values
		while (bufReader.ready()) serValue.append(bufReader.readLine());
		
		// Access parameters from request header
		String path       = (String) req.getParameter("path"); 
		Object parameter  = new JSONObject(serValue.toString()).get("value");
		
		// Increment Clock
		String submodelPath = path.substring(0, path.indexOf("/"));
		providerBackend.setModelPropertyValue(submodelPath +"/clock", (Integer) providerBackend.getModelPropertyValue(submodelPath +"/clock") + 1);
		
		
		// Perform delete operation
		providerBackend.deleteValue(path, parameter);
	}
}

