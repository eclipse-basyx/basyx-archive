package org.eclipse.basyx.testsuite.support.vab.stub;

import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.VABConnectionManager;

public class VABConnectionManagerStub extends VABConnectionManager {

	public VABConnectionManagerStub() {
		super(new DirectoryServiceStub(), new ConnectorProviderStub());
	}

	public VABConnectionManagerStub(IModelProvider provider) {
		this();
		getConnectorProvider().addMapping(null, provider);
	}

	private DirectoryServiceStub getDirectoryService() {
		return (DirectoryServiceStub) directoryService;
	}

	private ConnectorProviderStub getConnectorProvider() {
		return (ConnectorProviderStub) providerProvider;
	}

	public void addProvider(String id, IModelProvider provider) {
		getDirectoryService().addMapping(id, id);
		getConnectorProvider().addMapping(id, provider);
	}
}
