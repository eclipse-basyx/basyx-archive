/**
 * 
 */
package org.eclipse.basyx.testsuite.regression.aas.backend.connected;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.backend.connected.TypeDestroyer.TypeDestroyer;
import org.eclipse.basyx.aas.backend.connected.aas.submodelelement.operation.ConnectedOperation;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a ConnectedOperation can be created and used correctly
 * 
 * 
 * @author schnicke
 *
 */
public class TestConnectedOperation {

	IOperation operation;

	@Before
	public void build() {
		MetaModelElementFactory factory = new MetaModelElementFactory();

		// Create the operation map using the MetaModelElementFactory
		Operation op = factory.createOperation(new Operation(), (Function<Object[], Object>) (obj) -> {
			return (int) obj[0] + (int) obj[1];
		});
		
		Map<String, Object> destroyType = TypeDestroyer.destroyType(op);
		// Create a dummy connection manager containing the created Operation map
		VABConnectionManager manager = new VABConnectionManagerStub(new VirtualPathModelProvider(destroyType));

		// Create the ConnectedOperation based on the manager stub
		operation = new ConnectedOperation("", manager.connectToVABElement(""));
	}

	/**
	 * Tests if a operation invocation is handled correctly
	 * 
	 * @throws Exception
	 */
	@Test
	public void invokeTest() throws Exception {
		assertEquals(4, operation.invoke(2, 2));
	}
}
