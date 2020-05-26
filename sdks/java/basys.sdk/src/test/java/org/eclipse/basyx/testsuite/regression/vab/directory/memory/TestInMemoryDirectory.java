package org.eclipse.basyx.testsuite.regression.vab.directory.memory;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.directory.memory.InMemoryDirectory;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests constructor and other methods of {@link InMemoryDirectory} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestInMemoryDirectory {
	private InMemoryDirectory directory;
	
	@Before
	public void buildInMemoryDirectory() {
		directory = new InMemoryDirectory(getAddedValues());
	} 
	
	@Test
	public void testConstructor() {
		testElementsInMap(getAddedValues());
	} 
	
	@Test
	public void testAddMappingSingleKey() {
		String newKey = "key3";
		String newValue = "value3";
		directory.addMapping(newKey, newValue);
		assertEquals(newValue, directory.lookup(newKey));
	} 
	
	@Test
	public void testAddMappingMultipleKey() {
		String key3 = "key3";
		String value3 = "value3";
		String key4 = "key4";
		String value4 = "value4";
		Map<String, String> map = new HashMap<String, String>();
		map.put(key3, value3);
		map.put(key4, value4);
		directory.addMappings(map);
		testElementsInMap(map);
	} 
	
	@Test
	public void testRemoveMapping() {
		String key1 = "key1";
		directory.removeMapping(key1);
		directory.lookup(key1);
	} 
	
	private void testElementsInMap(Map<String, String> map) {
		for(Map.Entry<String, String> entry : map.entrySet()) {
			assertEquals(entry.getValue(), directory.lookup(entry.getKey()));	
		}
	} 
	
	private Map<String, String> getAddedValues() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		return map;
	} 
}
