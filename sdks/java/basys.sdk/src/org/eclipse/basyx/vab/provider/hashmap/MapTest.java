/**
 * 
 */
package org.eclipse.basyx.vab.provider.hashmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author schnicke
 *
 */
public class MapTest {
	public static void main(String[] args) throws Exception {
		VABHashmapProvider provider = new VABHashmapProvider(new HashMap<String, Object>());

		provider.createValue("test", new HashMap<String, Object>());
		provider.createValue("collection", new ArrayList<Integer>());

		provider.createValue("test/123", 2);

		System.out.println(provider.getModelPropertyValue("test/123"));

		provider.createValue("test/123", 3);

		System.out.println(provider.getModelPropertyValue("test/123"));

		provider.setModelPropertyValue("test/123", 5);

		System.out.println(provider.getModelPropertyValue("test/123"));

		System.out.println("------------------");
		provider.createValue("collection", 1);
		System.out.println(provider.getModelPropertyValue("collection"));

		provider.setModelPropertyValue("collection", Collections.singletonList(5));

		System.out.println(provider.getModelPropertyValue("collection"));
	}
}