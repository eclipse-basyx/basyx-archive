package org.eclipse.basyx.testsuite.regression.aas.metamodel.map.descriptor;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.junit.Test;

/**
 * Tests constructor, setter and getter of {@link ModelUrn} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestModelUrn {
	private static final String rawURN = "testRawUrn";
	
	@Test
	public void testConstructor1() {
		ModelUrn modelUrn = new ModelUrn(rawURN);
		assertEquals(rawURN, modelUrn.getURN());
	}
	
	@Test
	public void testConstructor2() {
		String legalEntity = "testLegalEntity";
		String subUnit = "testSubUnit";
		String subModel = "testSubModel";
		String version = "1.0";
		String revision = "5";
		String elementId = "testId";
		String elementInstance = "testInstance";
		
		ModelUrn modelUrn = new ModelUrn(legalEntity, subUnit, subModel, version, revision, elementId, elementInstance);
		String appendedString = "urn:" + legalEntity + ":" + subUnit + ":" + subModel + ":" + version + ":" + revision + ":" + elementId + "#"+ elementInstance;
		assertEquals(appendedString, modelUrn.getURN());
	}
	
	@Test
	public void testAppend() {
		String suffix = "testSuffix";
		ModelUrn modelUrn = new ModelUrn(rawURN);
		ModelUrn newModelUrn = modelUrn.append(suffix);
		assertEquals(rawURN + suffix, newModelUrn.getURN());
	}
}
