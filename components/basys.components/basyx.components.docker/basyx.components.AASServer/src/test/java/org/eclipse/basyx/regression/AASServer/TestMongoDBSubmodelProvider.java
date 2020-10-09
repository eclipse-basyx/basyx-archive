package org.eclipse.basyx.regression.AASServer;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.components.aas.mongodb.MongoDBAASAggregator;
import org.eclipse.basyx.components.aas.mongodb.MongoDBSubmodelAPI;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.testsuite.regression.submodel.restapi.SubModelProviderTest;
import org.eclipse.basyx.testsuite.regression.vab.protocol.http.TestsuiteDirectory;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.api.ConnectorProvider;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestMongoDBSubmodelProvider extends SubModelProviderTest {
	private VABConnectionManager connManager;

	@BeforeClass
	public static void setUpClass() {
		// just reset the data with this default db configuration
		new MongoDBAASAggregator(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH).reset();
	}

	@Override
	protected VABConnectionManager getConnectionManager() {
		if (connManager == null) {
			connManager = new VABConnectionManager(new TestsuiteDirectory(), new ConnectorProvider() {
				@Override
				protected IModelProvider createProvider(String addr) {
					SimpleNoOpAASSubmodel submodel = new SimpleNoOpAASSubmodel();
					MongoDBSubmodelAPI api = new MongoDBSubmodelAPI("mySubmodelId");
					api.setSubModel(submodel);
					IModelProvider smProvider = new SubModelProvider(api);
					// Simple submodel for testing specific mappings for submodels
					return smProvider;
				}
			});
		}
		return connManager;
	}

	/**
	 * Operations are not supported
	 */
	@Override
	@Test
	public void testDeleteOperation() {
	}

	/**
	 * Operations are not supported
	 */
	@Override
	@Test
	public void testInvokeOperation() {
	}
	
	/**
	 * Operations are not supported
	 */
	@Override
	@Test
	public void testInvokeOperationInCollection() {
	}

	/**
	 * Operations are not supported
	 */
	@Override
	@Test
	public void testInvokeAsync() throws Exception {
	}

	/**
	 * Operations are not supported
	 */
	@Override
	@Test
	public void testInvokeAsyncException() throws Exception {
	}

	/**
	 * Now 4 instead of 8 elements
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testReadSubModelElements() {
		VABElementProxy submodel = getConnectionManager().connectToVABElement(submodelAddr);
		Collection<Map<String, Object>> set = (Collection<Map<String, Object>>) submodel
				.getModelPropertyValue("/submodel/submodelElements");
		assertEquals(4, set.size());
	}

	/**
	 * Operations are not supported
	 */
	@Test
	public void testReadSingleOperation() {
	}

	/**
	 * testReadOperations
	 */
	@Test
	public void testReadOperations() {
	}

}
