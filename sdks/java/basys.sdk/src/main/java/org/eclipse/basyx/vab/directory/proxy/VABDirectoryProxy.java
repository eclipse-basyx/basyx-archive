package org.eclipse.basyx.vab.directory.proxy;

import org.eclipse.basyx.vab.coder.json.connector.JSONConnector;
import org.eclipse.basyx.vab.directory.api.IVABDirectoryService;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnector;

public class VABDirectoryProxy implements IVABDirectoryService {
	/**
	 * Store the URL of the registry of this proxy
	 */
	protected IModelProvider provider;

	/**
	 * Constructor for a generic VAB directory proxy based on a HTTP connection
	 * 
	 * @param directoryUrl
	 *            The endpoint of the registry with a HTTP-REST interface
	 */
	public VABDirectoryProxy(String directoryUrl) {
		this(new VABElementProxy("/api/v1/registry", new JSONConnector(new HTTPConnector(directoryUrl))));
	}
	
	/**
	 * Constructor for a generic VAB directory based on the registry model provider
	 * 
	 * @param provider
	 *            A model provider for the actual registry
	 */
	public VABDirectoryProxy(IModelProvider provider) {
		this.provider = provider;
	}

	/**
	 * Adds a single entry to the directory
	 */
	@Override
	public IVABDirectoryService addMapping(String key, String value) {
		try {
			provider.createValue(key, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Deletes an entry from the directory
	 */
	@Override
	public void removeMapping(String key) {
		try {
			provider.deleteValue(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns a single entry in the directory
	 */
	@Override
	public String lookup(String id) {
		Object response = null;
		try {
			response = provider.getModelPropertyValue(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response instanceof String) {
			return (String) response;
		} else {
			return null;
		}
	}
}
