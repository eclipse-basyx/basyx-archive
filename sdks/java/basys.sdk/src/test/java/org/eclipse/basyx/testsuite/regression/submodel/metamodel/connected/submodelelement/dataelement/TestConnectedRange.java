package org.eclipse.basyx.testsuite.regression.submodel.metamodel.connected.submodelelement.dataelement;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedRange;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Range;
import org.eclipse.basyx.submodel.restapi.PropertyProvider;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.support.TypeDestroyingProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a ConnectedRange can be created and used correctly
 * 
 * @author conradi
 *
 */
public class TestConnectedRange {
	
	ConnectedRange connectedRange;
	Range range;
	
	@Before
	public void build() {
		range = new Range("valueType", new Integer(1), new Integer(10));
		
		VABConnectionManagerStub manager = new VABConnectionManagerStub(
				new PropertyProvider(new TypeDestroyingProvider(new VABLambdaProvider(range))));

		connectedRange = new ConnectedRange(manager.connectToVABElement(""));
	}
	
	/**
	 * Tests if getValueType() returns the correct value
	 */
	@Test
	public void testGetValueType() {
		assertEquals(range.getValueType(), connectedRange.getValueType());
	}
	
	/**
	 * Tests if getMin() returns the correct value
	 */
	@Test
	public void testGetMin() {
		assertEquals(range.getMin(), connectedRange.getMin());
	}
	
	/**
	 * Tests if getMax() returns the correct value
	 */
	@Test
	public void testGetMax() {
		assertEquals(range.getMax(), connectedRange.getMax());
	}
}
