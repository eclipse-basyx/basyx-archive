package org.eclipse.basyx.testsuite.regression.submodel.metamodel.api.submodelelement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.SubmodelElementIdShortBlacklist;
import org.junit.Test;

/**
 * Tests if the SubmodelElementIdShortBlacklist works as intended
 * 
 * @author schnicke
 *
 */
public class TestSubmodelElementIdShortBlacklist {

	@Test
	public void testIsBlacklisted() {
		String allowed[] = { "test", "values", "invocations" };
		
		for (String s : SubmodelElementIdShortBlacklist.BLACKLIST) {
			assertTrue(s + " was incorrectly allowed", SubmodelElementIdShortBlacklist.isBlacklisted(s));
		}

		for (String s : allowed) {
			assertFalse(s + " was incorrectly blacklisted", SubmodelElementIdShortBlacklist.isBlacklisted(s));
		}
	}
}
