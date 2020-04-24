package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.qualifier;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangString;
import org.junit.Test;

/**
 * Tests constructor, setter and getter of {@link LangString} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestLangString {
	private static final String LANGUAGE = "Eng";
	private static final String DESCRIPTION = "test";
	
	@Test
	public void testConstructor() {
		LangString langString = new LangString(LANGUAGE, DESCRIPTION);
		assertEquals(LANGUAGE, langString.getLanguage());
		assertEquals(DESCRIPTION, langString.getDescription());
	}
}
