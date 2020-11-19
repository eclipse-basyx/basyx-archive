package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.submodelelement.dataelement.range;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.range.Range;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.range.RangeValue;
import org.junit.Before;
import org.junit.Test;


/**
 * Test for Range
 * 
 * @author conradi
 *
 */
public class TestRange {

	private static final int MIN = 0;
	private static final int MAX = 10;
	private Range range;
	
	@Before
	public void buildRange() {
		range = new Range(PropertyValueTypeDef.Integer, MIN, MAX);
	}
	
	@Test
	public void testGetValue() {
		assertEquals(MIN, range.getMin());
		assertEquals(MAX, range.getMax());
		
		RangeValue value = range.getValue();
		assertEquals(MIN, value.getMin());
		assertEquals(MAX, value.getMax());
	} 
	
}
