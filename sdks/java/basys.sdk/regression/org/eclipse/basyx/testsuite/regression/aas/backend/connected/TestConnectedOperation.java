/**
 * 
 */
package org.eclipse.basyx.testsuite.regression.aas.backend.connected;

import static org.junit.Assert.assertEquals;

import java.util.function.Function;

import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.backend.connected.ConnectedOperation;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.operation.Operation;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * @author schnicke
 *
 */
public class TestConnectedOperation {

	IOperation operation;

	@Before
	public void build() {
		MetaModelElementFactory factory = new MetaModelElementFactory();

		Operation op = factory.createOperation(new Operation(), (Function<Object[], Object>) (obj) -> {
			return (int) obj[0] + (int) obj[1];
		});

		operation = new ConnectedOperation("",
				new VABConnectionManagerStub(new VABHashmapProvider(op)).connectToVABElement(""));
	}

	@Test
	public void invokeTest() throws Exception {
		assertEquals(4, operation.invoke(2, 2));
	}
}
