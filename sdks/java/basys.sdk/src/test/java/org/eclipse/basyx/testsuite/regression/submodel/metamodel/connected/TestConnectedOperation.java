/**
 *
 */
package org.eclipse.basyx.testsuite.regression.submodel.metamodel.connected;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.operation.ConnectedOperation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationHelper;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.eclipse.basyx.submodel.restapi.OperationProvider;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.testsuite.regression.vab.protocol.TypeDestroyer;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.map.VABMapProvider;
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
		// Create the operation map using the MetaModelElementFactory
		Operation op = new Operation((Function<Object[], Object>) obj -> {
			return (int) obj[0] + (int) obj[1];
		});

		List<OperationVariable> in = new ArrayList<>();

		in.add(new OperationVariable(OperationHelper.createPropertyTemplate(PropertyValueTypeDef.Integer)));
		in.add(new OperationVariable(OperationHelper.createPropertyTemplate(PropertyValueTypeDef.Integer)));

		op.setInputVariables(in);

		op.setOutputVariables(Collections.singletonList(new OperationVariable(OperationHelper.createPropertyTemplate(PropertyValueTypeDef.Integer))));

		Map<String, Object> destroyType = TypeDestroyer.destroyType(op);
		// Create a dummy connection manager containing the created Operation map
		VABConnectionManager manager = new VABConnectionManagerStub(
				new OperationProvider(new VABMapProvider(destroyType)));

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
