package org.eclipse.basyx.testsuite.regression.vab.protocol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.basyx.testsuite.regression.vab.modelprovider.SimpleVABElement;
import org.junit.Test;

/**
 * Removes type information similar to what a communication over VAB would do
 * @author rajashek
 *
 */
public class TypeDestroyer {
	@SuppressWarnings("unchecked")
	/**
	 * Removes type information of all objects within the map, i.e. every subclass
	 * of HashMap is reduced to HashMap
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> destroyType(Map<String, Object> map){
		return (Map<String, Object>) handle(map);
	}
	
	@SuppressWarnings("unchecked")
	private static Object handle(Object o) {
		if(o instanceof Map) {
			return handleMap((Map<String, Object>) o);
		} else {
			return o;
		}
	}

	private static Map<String, Object> handleMap(Map<String, Object> map) {
		Map<String, Object> ret = new HashMap<>();
		for (Entry<String, Object> entry : map.entrySet()) {
			ret.put(entry.getKey(), handle(entry.getValue()));
		}
		return ret;
	}
	
	@Test
	public void testTypeDestroyer() {
		SimpleVABElement sm = new SimpleVABElement();
		Map<String, Object> generic = destroyType(sm);
		assertTrue(sm instanceof SimpleVABElement);
		assertFalse(generic instanceof SimpleVABElement);
		assertEquals(generic, sm);

	}
}
