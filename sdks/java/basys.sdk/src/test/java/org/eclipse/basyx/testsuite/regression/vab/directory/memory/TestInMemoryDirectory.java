package org.eclipse.basyx.testsuite.regression.vab.directory.memory;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.testsuite.regression.vab.directory.proxy.TestDirectory;
import org.eclipse.basyx.vab.directory.api.IVABDirectoryService;
import org.eclipse.basyx.vab.directory.memory.InMemoryDirectory;
import org.junit.Test;

/**
 * Tests the InMemory directory using the TestDirectory Suite
 * 
 * @author schnicke, haque
 *
 */
public class TestInMemoryDirectory extends TestDirectory {

	@Override
	protected IVABDirectoryService getRegistry() {
		return new InMemoryDirectory();
	}

	@Test
	public void testConstructor() {
		IVABDirectoryService registry = new InMemoryDirectory(getAddedValues());
		testElementsInMap(registry, getAddedValues());
	}

	@Test
	public void testAddMappingMultipleKey() {
		InMemoryDirectory registry = new InMemoryDirectory();
		String key3 = "key3";
		String value3 = "value3";
		String key4 = "key4";
		String value4 = "value4";
		Map<String, String> map = new HashMap<String, String>();
		map.put(key3, value3);
		map.put(key4, value4);
		registry.addMappings(map);
		testElementsInMap(registry, map);
	}

	private void testElementsInMap(IVABDirectoryService registry, Map<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			assertEquals(entry.getValue(), registry.lookup(entry.getKey()));
		}
	}

	private Map<String, String> getAddedValues() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		return map;
	}
}
