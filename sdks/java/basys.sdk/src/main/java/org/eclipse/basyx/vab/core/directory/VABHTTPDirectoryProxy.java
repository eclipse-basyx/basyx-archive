package org.eclipse.basyx.vab.core.directory;

import org.eclipse.basyx.vab.backend.connector.JSONConnector;
import org.eclipse.basyx.vab.backend.connector.http.HTTPConnector;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

public class VABHTTPDirectoryProxy implements IVABDirectoryService {
	/**
	 * Store the URL of the registry of this proxy
	 */
	protected VABElementProxy proxy;

	/**
	 * Constructor for a generic VAB directory
	 * 
	 * @param directoryUrl
	 *            The endpoint of the registry with a HTTP-REST interface
	 */
	public VABHTTPDirectoryProxy(String directoryUrl) {
		IModelProvider provider = new JSONConnector(new HTTPConnector(directoryUrl));
		this.proxy = new VABElementProxy(null, provider);
		this.proxy = proxy.getDeepProxy("/api/v1/registry");
	}

	/**
	 * Adds a single entry to the directory
	 */
	@Override
	public IVABDirectoryService addMapping(String key, String value) {
		proxy.createValue(key, value);
		return this;
	}

	/**
	 * Deletes an entry from the directory
	 */
	@Override
	public void removeMapping(String key) {
		proxy.deleteValue(key);
	}

	/**
	 * Returns a single entry in the directory
	 */
	@Override
	public String lookup(String id) {
		Object response = proxy.getModelPropertyValue(id);
		if (response instanceof String) {
			return (String) response;
		} else {
			return null;
		}
	}
}
