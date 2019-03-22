package org.eclipse.basyx.testsuite.regression.vab.provider;


import org.eclipse.basyx.aas.backend.connector.ConnectorProvider;
import org.eclipse.basyx.aas.backend.connector.JSONConnector;
import org.eclipse.basyx.testsuite.regression.vab.snippet.CreateDelete;
import org.eclipse.basyx.testsuite.regression.vab.snippet.GetPropertyValue;
import org.eclipse.basyx.testsuite.regression.vab.snippet.Invoke;
import org.eclipse.basyx.testsuite.regression.vab.snippet.SetPropertyValue;
import org.eclipse.basyx.testsuite.regression.vab.snippet.TestCollectionProperty;
import org.eclipse.basyx.testsuite.regression.vab.snippet.TestMapProperty;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.eclipse.basyx.testsuite.support.vab.stub.IBasyxConnectorFacade;
import org.eclipse.basyx.testsuite.support.vab.stub.elements.SimpleVABElement;
import org.eclipse.basyx.vab.backend.server.utils.JSONProvider;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Test;

/**
 * Test JSONConnector against JSONProvider
 * @author pschorn
 *
 */
public class TestJSONConnectorProviderIntegration {

		
	protected VABConnectionManager connManager = new VABConnectionManager(new TestsuiteDirectory(), new ConnectorProvider() {

		@Override
		protected IModelProvider createProvider(String addr) {
			
			// BACKEND
			// Creates a new VABHashMapProvider which manages a data model as defined in SimpleVABElement.
			VABHashmapProvider modelprovider = new VABHashmapProvider(new SimpleVABElement());
			
			// We stack the JSONProvider on top of the model to handle serialization and exceptions
			JSONProvider<VABHashmapProvider> provider = new JSONProvider<VABHashmapProvider>(modelprovider);
			
			// FRONTEND
			// We stack the JSONConnector on top of the JSONProvider to handle de-serialization and response verification
			JSONConnector connector = new JSONConnector(new IBasyxConnectorFacade<VABHashmapProvider>(provider));
			
			
			return connector;
		}
	});

	@Test
	public void testCreateDelete() {
		CreateDelete.test(connManager);
	}

	@Test
	public void testGet() {
		GetPropertyValue.test(connManager);
	}
	
	@Test
	public void testInvoke() {
		Invoke.test(connManager);
	}

	@Test
	public void testSet() {
		SetPropertyValue.test(connManager);
	}

	@Test
	public void testMapGet() {
		TestMapProperty.testGet(connManager);
	}

	@Test
	public void testMapUpdateComplete() {
		TestMapProperty.testUpdateComplete(connManager);
	}

	@Test
	public void testMapUpdateElement() {
		TestMapProperty.testUpdateElement(connManager);
	}

	@Test
	public void testMapRemoveElement() {
		TestMapProperty.testRemoveElement(connManager);
	}

	@Test
	public void testCollectionGet() {
		TestCollectionProperty.testGet(connManager);
	}

	@Test
	public void testCollectionUpdateComplete() {
		TestCollectionProperty.testUpdateComplete(connManager);
	}

	@Test
	public void testCollectionUpdateElement() {
		TestCollectionProperty.testUpdateElement(connManager);
	}

	@Test
	public void testCollectionRemoveElement() {
		TestCollectionProperty.testRemoveElement(connManager);
	}
}	
//	@Test
//	public void testGet() {
//		
//		Object obj = connector.getModelPropertyValue("test/ok");
//		
//		assertEquals((String) obj, "ok");
//	}
//	
//	
//	@Test
//	public void testSet() throws Exception {
//		
//		connector.setModelPropertyValue("test/ok", 7); // correct parameter combination tested in VABHashMapProviderStub
//		
//		try {
//			connector.setModelPropertyValue("test/ok", 8);
//			fail();
//		} catch (Exception e) { // problem: serialization causes exception when basystype not existent
//			System.out.println("Exception verified: "+e.getMessage());
//		}
//	} 
//	
//	@Test
//	public void testCreate() throws Exception {
//		
//		connector.createValue("test/ok", 7);
//		
//		try {
//			connector.setModelPropertyValue("test/ok", 8);
//			fail();
//		} catch (Exception e) {
//			System.out.println("Exception correctly verified");
//		}
//	}
//	
//	@Test
//	public void testDelete() throws Exception {
//		
//		connector.deleteValue("test/ok", null);
//		connector.deleteValue("test/ok", 7);
//		
//		try {
//			connector.deleteValue("test/wrong", null);
//			fail();
//		} catch (Exception e1) {
//			System.out.println("Exception 1 correctly verified");
//			try {
//				connector.deleteValue("test/ok", 8);
//				fail();
//			} catch (Exception e2) {
//				System.out.println("Exception 2 correctly verified");
//			}
//		}
//	}
//	
//	@Test
//	public void testInvoke() throws Exception {
//		
//		connector.invokeOperation("test/ok", new Object[] {7});
//		
//		try {
//			connector.setModelPropertyValue("test/ok", 8);
//			fail();
//		} catch (Exception e) {
//			System.out.println("Exception correctly verified");
//		}
//	}
//}
