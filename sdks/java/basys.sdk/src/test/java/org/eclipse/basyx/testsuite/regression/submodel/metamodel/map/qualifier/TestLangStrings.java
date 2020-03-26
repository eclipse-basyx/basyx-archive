package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.qualifier;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.junit.Test;

/**
 * Tests constructor, setter and getter of {@link LangStrings} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestLangStrings {
	private static final String LANGUAGE1 = "Eng";
	private static final String LANGUAGE2 = "Deu";
	private static final String TEXT1 = "test";
	private static final String TEXT2 = "test2";
	
	@Test
	public void testConstructor1() {
		LangStrings langStrings = new LangStrings(LANGUAGE1, TEXT1);
		String textString = langStrings.get(LANGUAGE1);
		assertEquals(TEXT1, textString);
	}
	
	@Test
	public void testConstructor2() {
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("language", LANGUAGE1);
		map1.put("text", TEXT1);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("language", LANGUAGE2);
		map2.put("text", TEXT2);
		
		Collection<Map<String, Object>> listsCollection = new ArrayList<>();
		listsCollection.add(map1);
		listsCollection.add(map2);
		
		LangStrings langStrings = new LangStrings(listsCollection);
		String textString1 = langStrings.get(LANGUAGE1);
		assertEquals(TEXT1, textString1);
		
		String textString2 = langStrings.get(LANGUAGE2);
		assertEquals(TEXT2, textString2);
	}
	
	@Test
	public void testAdd() {
		LangStrings langStrings = new LangStrings(LANGUAGE1, TEXT1);
		
		langStrings.add(LANGUAGE2, TEXT2);
		assertEquals(TEXT2, langStrings.get(LANGUAGE2));
	}
	
	@Test
	public void testGetLanguages() {
		LangStrings langStrings = new LangStrings(LANGUAGE1, TEXT1);
		langStrings.add(LANGUAGE2, TEXT2);
		
		Set<String> languageSet = new HashSet<String>();
		languageSet.add(LANGUAGE1);
		languageSet.add(LANGUAGE2);
		assertEquals(languageSet, langStrings.getLanguages());
	}
	
}
