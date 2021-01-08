package org.eclipse.basyx.testsuite.regression.submodel.metamodel.api.qualifier;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IdShortValidator;
import org.junit.Test;

/**
 * Tests the IdShortValidator's capability to validate IdShorts
 * 
 * @author schnicke
 *
 */
public class TestIdShortValidator {

	@Test
	public void testValidator() {
		String[] accepted = {"abc", "AbC"};
		String[] notAccepted = { ":", " test", "1Test", "äTest", null, "" };

		for (String s : accepted) {
			assertTrue(s + " was not tested valid", IdShortValidator.isValid(s));
		}

		for (String s : notAccepted) {
			assertFalse(s + " was not tested invalid", IdShortValidator.isValid(s));
		}
	}
}
