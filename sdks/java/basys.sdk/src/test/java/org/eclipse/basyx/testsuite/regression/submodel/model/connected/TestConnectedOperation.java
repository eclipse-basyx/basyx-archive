/**
 *
 */
package org.eclipse.basyx.testsuite.regression.submodel.model.connected;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.aas.factory.java.MetaModelElementFactory;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.operation.ConnectedOperation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.restapi.OperationProvider;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.testsuite.regression.vab.protocol.TypeDestroyer;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.map.VABHashmapProvider;
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
		VABConnectionManager manager = new VABConnectionManagerStub(
				new OperationProvider(new VABHashmapProvider(destroyType)));

		// Create the ConnectedOperation based on the manager stub
		operation = new ConnectedOperation(manager.connectToVABElement(""));
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
