package org.eclipse.basyx.testsuite.regression.aas.impl.references;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.impl.tools.BaSysID;
import org.junit.jupiter.api.Test;



/**
 * Test case that checks that the BaSysID class always returns the correct object ID
 * 
 * @author kuhn
 *
 */
public class TestObjectIdentifier {


	/**
	 * Run test case
	 */
	@Test
	void test() {
		// Test object identifier extraction
		assertTrue(BaSysID.instance.getIdentifier("aas.aasid").equals("aasid"));
		assertTrue(BaSysID.instance.getIdentifier("smID.aasid").equals("smID"));
		assertTrue(BaSysID.instance.getIdentifier("aas.aasid.qualified.id").equals("aasid"));
		assertTrue(BaSysID.instance.getIdentifier("smID.aasid.qualified.id").equals("smID"));

		// Test path element extraction
		assertTrue(BaSysID.instance.getIdentifier("aas.aasid/patha").equals("patha"));
		assertTrue(BaSysID.instance.getIdentifier("aas.aasid/patha/b").equals("b"));
		assertTrue(BaSysID.instance.getIdentifier("aas.aasid/patha/b/c").equals("c"));
		assertTrue(BaSysID.instance.getIdentifier("aas.aasid/patha/b/c/").equals("c"));

		assertTrue(BaSysID.instance.getIdentifier("sma.aasid/patha").equals("patha"));
		assertTrue(BaSysID.instance.getIdentifier("sma.aasid/patha/b").equals("b"));
		assertTrue(BaSysID.instance.getIdentifier("sma.aasid/patha/b/c").equals("c"));
		assertTrue(BaSysID.instance.getIdentifier("sma.aasid/patha/b/c/").equals("c"));
	}
}


