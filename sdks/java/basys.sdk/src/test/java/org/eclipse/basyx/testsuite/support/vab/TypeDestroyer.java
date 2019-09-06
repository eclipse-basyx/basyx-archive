package org.eclipse.basyx.testsuite.support.vab;

import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
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
		Map<String, Object> ret = new HashMap<String, Object>();
		for(String k : map.keySet()) {
			ret.put(k, handle(map.get(k)));
		}
		return ret;
	}
	
	@Test
	public void testTypeDestroyer() {
		SubModel sm = new SubModel();
		assertFalse(destroyType(sm) instanceof SubModel);
	}
}
