package org.eclipse.basyx.testsuite.regression.vab.provider;

import org.eclipse.basyx.aas.backend.connector.ConnectorProvider;
import org.eclipse.basyx.aas.backend.connector.JSONConnector;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.eclipse.basyx.testsuite.support.vab.stub.IBasyxConnectorFacade;
import org.eclipse.basyx.testsuite.support.vab.stub.elements.SimpleVABElement;
import org.eclipse.basyx.vab.backend.server.utils.JSONProvider;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;

/**
 * Test JSONConnector against JSONProvider
 * 
 * @author pschorn
 *
 */
public class TestJSONConnectorProviderIntegration {

	protected VABConnectionManager connManager = new VABConnectionManager(new TestsuiteDirectory(),
			new ConnectorProvider() {

				@Override
				protected IModelProvider createProvider(String addr) {

					// BACKEND
					// Creates a new VABHashMapProvider which manages a data model as defined in SimpleVABElement.
					VABHashmapProvider modelprovider = new VABHashmapProvider(new SimpleVABElement());

					// We stack the JSONProvider on top of the model to handle serialization and exceptions
					JSONProvider<VABHashmapProvider> provider = new JSONProvider<VABHashmapProvider>(modelprovider);

					// FRONTEND
					// We stack the JSONConnector on top of the JSONProvider to handle de-serialization and response
					// verification
					JSONConnector connector = new JSONConnector(
							new IBasyxConnectorFacade<VABHashmapProvider>(provider));

					return connector;
				}
			});
}