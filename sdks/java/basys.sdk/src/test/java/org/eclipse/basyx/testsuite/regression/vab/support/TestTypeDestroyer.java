package org.eclipse.basyx.testsuite.regression.vab.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.basyx.testsuite.regression.vab.modelprovider.SimpleVABElement;
import org.eclipse.basyx.vab.support.TypeDestroyer;
import org.junit.Test;

/**
 * Removes type information similar to what a communication over VAB would do
 * @author rajashek
 *
 */
public class TestTypeDestroyer {
	
	@Test
	public void testTypeDestroyer() {
		SimpleVABElement sm = new SimpleVABElement();
		Map<String, Object> generic = TypeDestroyer.destroyType(sm);
		assertTrue(sm instanceof SimpleVABElement);
		assertFalse(generic instanceof SimpleVABElement);
		assertEquals(generic, sm);
	}
}
