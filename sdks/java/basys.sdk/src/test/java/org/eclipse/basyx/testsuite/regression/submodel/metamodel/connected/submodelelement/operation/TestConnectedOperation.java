/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.testsuite.regression.submodel.metamodel.connected.submodelelement.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.operation.ConnectedOperation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.restapi.OperationProvider;
import org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.submodelelement.operation.TestOperationSuite;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.map.VABMapProvider;
import org.eclipse.basyx.vab.support.TypeDestroyer;
import org.junit.Test;

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

	@Override
	@Test
	public void testInvokeWithSubmodelElements() {
		Property param1 = new Property("testIn1", 2);
		Property param2 = new Property("testIn2", 4);

		SubmodelElement[] result = operation.invoke(param1, param2);

		assertEquals(1, result.length);
		assertEquals(6, result[0].getValue());
	}

	@Override
	public void testInvokeWithSubmodelElementsException() {
		try {
			Property param1 = new Property("testIn1", 1);
			Property param2 = new Property("testIn2", 1);

			operationException.invoke(param1, param2);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException || e.getCause() instanceof NullPointerException);
		}
	}
}
