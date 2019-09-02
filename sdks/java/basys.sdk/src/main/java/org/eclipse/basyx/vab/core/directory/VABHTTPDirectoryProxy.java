package org.eclipse.basyx.vab.core.directory;

import org.eclipse.basyx.aas.api.webserviceclient.WebServiceRawClient;
import org.eclipse.basyx.vab.backend.http.tools.GSONTools;
import org.eclipse.basyx.vab.backend.http.tools.factory.DefaultTypeFactory;

public class VABHTTPDirectoryProxy implements IVABDirectoryService {
	/**
	 * JSON serializer for serializing and deserializing registry entries
	 */
	protected GSONTools serializer = new GSONTools(new DefaultTypeFactory());

	/**
	 * Invoke BaSyx service calls via web services
	 */
	protected WebServiceRawClient client = new WebServiceRawClient();

	/**
	 * Store the URL of the registry of this proxy
	 */
	protected String directoryUrl;

	/**
	 * Constructor for a generic VAB directory
	 * 
	 * @param directoryUrl
	 *            The endpoint of the registry with a HTTP-REST interface
	 */
	public VABHTTPDirectoryProxy(String directoryUrl) {
		this.directoryUrl = directoryUrl;
	}

	/**
	 * Adds a single entry to the directory
	 */
	@Override
	public IVABDirectoryService addMapping(String key, String value) {
		client.post(directoryUrl + "/api/v1/registry", value);
		return this;
	}

	/**
	 * Deletes an entry from the directory
	 */
	@Override
	public void removeMapping(String key) {
		client.delete(directoryUrl + "/api/v1/registry/" + key);
	}

	/**
	 * Returns a single entry in the directory
	 */
	@Override
	public String lookup(String id) {
		return client.get(directoryUrl + "/api/v1/registry/" + id);
	}
}
