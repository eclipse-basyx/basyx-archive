package org.eclipse.basyx.testsuite.regression.submodel.metamodel.connected.submodelelement.operation;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.operation.ConnectedOperation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.restapi.OperationProvider;
import org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.submodelelement.operation.TestOperationSuite;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.map.VABMapProvider;
import org.eclipse.basyx.vab.support.TypeDestroyer;

/**
 * Tests if a ConnectedOperation can be created and used correctly
 *
 *
 * @author schnicke
 *
 */
public class TestConnectedOperation extends TestOperationSuite {

	@Override
	protected IOperation prepareOperation(Operation operation) {
		Map<String, Object> destroyType = TypeDestroyer.destroyType(operation);
		// Create a dummy connection manager containing the created Operation map
		VABConnectionManager manager = new VABConnectionManagerStub(
				new OperationProvider(new VABMapProvider(destroyType)));

		// Create the ConnectedOperation based on the manager stub
		return new ConnectedOperation(manager.connectToVABElement(""));
	}
}
