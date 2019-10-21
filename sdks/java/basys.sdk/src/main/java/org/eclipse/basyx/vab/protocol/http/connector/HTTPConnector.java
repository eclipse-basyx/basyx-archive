package org.eclipse.basyx.vab.protocol.http.connector;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.basyx.vab.exception.ServerException;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.protocol.api.IBaSyxConnector;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * HTTP connector class
 * 
 * @author kuhn, pschorn, schnicke
 *
 */
public class HTTPConnector implements IBaSyxConnector {
	
	private static Logger logger = LoggerFactory.getLogger(HTTPConnector.class);
	
	private String address;
	private String mediaType;

	/**
	 * Invoke a BaSys get operation via HTTP GET
	 * 
	 * @param address
	 *            the server address from the directory
	 * @param servicePath
	 *            the URL suffix for the requested path
	 * @return the requested object
	 */
	@Override
	public String getModelPropertyValue(String servicePath) {
		return httpGet(encodeHash(servicePath));
	}

	public HTTPConnector(String address) {
		this(address, MediaType.APPLICATION_JSON);
	}

	public HTTPConnector(String address, String mediaType) {
		this.address = address;
		this.mediaType = mediaType;
		
		logger.trace("Create with addr: {}", address);
	}

	/**
	 * Invokes BasysPut method via HTTP PUT. Overrides existing property, operation
	 * or event.
	 * 
	 * @param address
	 *            the server address from the directory
	 * @param servicePath
	 *            the URL suffix for the requested property, operation or event
	 * @param newValue
	 *            should be an IElement of type Property, Operation or Event
	 */
	@Override
	public String setModelPropertyValue(String servicePath, String newValue) throws ServerException {

		return httpPut(encodeHash(servicePath), newValue);
	}

	/**
	 * Invoke a BaSys Delete operation via HTTP PATCH. Deletes an element from a map
	 * or collection by key
	 * 
	 * @param address
	 *            the server address from the directory
	 * @param servicePath
	 *            the URL suffix for the requested property
	 * @param obj
	 *            the key or index of the entry that should be deleted
	 * @throws ServerException
	 */
	@Override
	public String deleteValue(String servicePath, String obj) throws ServerException {

		return httpPatch(encodeHash(servicePath), obj);
	}

	/**
	 * Invoke a BaSys invoke operation via HTTP. Implemented as HTTP POST.
	 * 
	 * @throws ServerException
	 */
	@Override
	public String createValue(String servicePath, String newValue) throws ServerException {

		return httpPost(encodeHash(servicePath), newValue);
	}

	/**
	 * Invoke basysDelete operation via HTTP DELETE. Deletes any resource under the
	 * given path.
	 * 
	 * @throws ServerException
	 */
	@Override
	public String deleteValue(String servicePath) throws ServerException {

		return httpDelete(encodeHash(servicePath));
	}

	/**
	 * Execute a web service, return JSON string
	 */
	protected Builder buildRequest(Client client, String wsURL) {
		// Called URL
		WebTarget resource = client.target(wsURL);

		// Build request, set JSON encoding
		Builder request = resource.request();
		request.accept(mediaType);

		// Return JSON request
		return request;
	}

	/**
	 * Create web service path
	 */
	protected String createWSPath(String part1, String part2) {
		// Null pointer check
		if (part1 == null)
			return part2;
		if (part2 == null)
			return part1;

		// Return combined string
		if (part1.endsWith("/"))
			return part1 + part2;

		return part1 + "/" + part2;
	}
	
	/**
	 * Perform a HTTP get request
	 * 
	 * @param servicePath
	 * @return
	 */
	private String httpGet(String servicePath) {
		logger.trace("[HTTP Get] {}", VABPathTools.concatenatePaths(address, servicePath));

		// Invoke service call via web services
		Client client = ClientBuilder.newClient();

		// Build web service URL
		Builder request = buildRequest(client, VABPathTools.concatenatePaths(address, servicePath));

		// Perform request, return response
		String result = request.get(String.class);

		logger.trace("[HTTP Get_Result] {}", result);

		// Return repsonse message (header)
		return result;
	}

	private String httpPut(String servicePath, String newValue) throws ServerException {
		logger.trace("[HTTP Put] {} [[ {} ]]", VABPathTools.concatenatePaths(address, servicePath), newValue);

		// Invoke service call via web services
		Client client = ClientBuilder.newClient();

		// Build web service URL
		Builder request = buildRequest(client, VABPathTools.concatenatePaths(address, servicePath));

		// Perform request
		Response rsp = request.put(Entity.entity(newValue, mediaType));

		// Return repsonse message (header)
		return rsp.readEntity(String.class);

	}

	private String httpPatch(String servicePath, String newValue) throws ServerException {
		logger.trace("[HTTP Patch] {} {}", VABPathTools.concatenatePaths(address, servicePath), newValue);

		// Invoke service call via web services
		Client client = ClientBuilder.newClient();

		// Create and invoke HTTP PATCH request
		Response rsp = client.target(VABPathTools.concatenatePaths(address, servicePath)).request().build("PATCH", Entity.text(newValue)).property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true).invoke();

		// Return repsonse message (header)
		return rsp.readEntity(String.class);
	}

	private String httpPost(String servicePath, String parameter) throws ServerException {
		logger.trace("[HTTP Post] {} {}", VABPathTools.concatenatePaths(address, servicePath), parameter);

		// Invoke service call via web services
		Client client = ClientBuilder.newClient();

		// Build web service URL
		Builder request = buildRequest(client, VABPathTools.concatenatePaths(address, servicePath));

		// Perform request
		Response rsp = request.post(Entity.entity(parameter, mediaType));

		// Return repsonse message (header)
		return rsp.readEntity(String.class);
	}

	private String httpDelete(String servicePath) throws ServerException {
		logger.trace("[HTTP Delete] {}", VABPathTools.concatenatePaths(address, servicePath));

		// Invoke service call via web services
		Client client = ClientBuilder.newClient();

		// Build web service URL
		Builder request = buildRequest(client, VABPathTools.concatenatePaths(address, servicePath));

		// Perform request
		Response rsp = request.delete();

		// Return repsonse message (header)
		return rsp.readEntity(String.class);
	}

	@Override
	public String invokeOperation(String path, String parameter) throws Exception {

		return httpPost(path, parameter);
	}

	/**
	 * Replaces # with %23
	 * 
	 * @param path
	 * @return
	 */
	private String encodeHash(String path) {
		return path.replace("#", "%23");
	}

}
