package org.eclipse.basyx.testsuite.support.vab.stub;

import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.VABConnectionManager;

/**
 * A VABConnectionManager stub which automatically creates the directory entries
 * for added IModelProviders
 * 
 * @author schnicke
 *
 */
public class VABConnectionManagerStub extends VABConnectionManager {

	public VABConnectionManagerStub() {
		// Create Stub with default DirectoryStub/ConnectorProviderStub
		super(new DirectoryServiceStub(), new ConnectorProviderStub());
	}

	/**
	 * Creates a stub containing a default provider
	 * 
	 * @param provider
	 */
	public VABConnectionManagerStub(IModelProvider provider) {
		this();
		// Add default mapping for "null" id
		getConnectorProvider().addMapping(null, provider);
	}

	private DirectoryServiceStub getDirectoryService() {
		return (DirectoryServiceStub) directoryService;
	}

	private ConnectorProviderStub getConnectorProvider() {
		return (ConnectorProviderStub) providerProvider;
	}

	/**
	 * Add the id to the Directory and also add the mapping to the ConnectorProvider
	 * 
	 * @param id
	 * @param provider
	 */
	public void addProvider(String id, IModelProvider provider) {
		getDirectoryService().addMapping(id, id);
		getConnectorProvider().addMapping(id, provider);
	}
}
