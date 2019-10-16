package org.eclipse.basyx.testsuite.regression.vab.coder.json;

import org.eclipse.basyx.testsuite.regression.vab.modelprovider.SimpleVABElement;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.TestsuiteDirectory;
import org.eclipse.basyx.vab.coder.json.connector.JSONConnector;
import org.eclipse.basyx.vab.coder.json.provider.JSONProvider;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.map.VABHashmapProvider;
import org.eclipse.basyx.vab.protocol.api.ConnectorProvider;

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