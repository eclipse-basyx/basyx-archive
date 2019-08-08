package org.eclipse.basyx.testsuite.regression.vab.provider;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.backend.connector.ConnectorProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.directory.TestsuiteDirectory;
import org.eclipse.basyx.testsuite.support.vab.stub.elements.SimpleVABElement;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Test;

/**
 * Tests the functionality of the VABHashmapProvider according to the test cases
 * in the snippet package
 * 
 * @author schnicke
 *
 */
public class TestHashMapProvider extends TestProvider {
	private VABConnectionManager connManager;

	@Override
	protected VABConnectionManager getConnectionManager() {
		if (connManager == null) {
			connManager = new VABConnectionManager(new TestsuiteDirectory(), new ConnectorProvider() {
				@Override
				protected IModelProvider createProvider(String addr) {

					// Creates a new VABHashMapProvider which manages a data model
					// as defined in SimpleVABElement
					return new VABHashmapProvider(new SimpleVABElement());
				}
			});
		}
		return connManager;
	}
	
	@Test
	public void testListReferenceException() {
		// Test invalid reference
		VABElementProxy connVABElement = getConnectionManager().connectToVABElement("urn:fhg:es.iese:vab:1:1:simplevabelement");
		try {
			connVABElement.readElementValue("structure/list/byRef_23");
			fail();
		} catch (ServerException e) {
			assertTrue(e.getType().contains("InvalidListReferenceException"));
		}
	}
}
