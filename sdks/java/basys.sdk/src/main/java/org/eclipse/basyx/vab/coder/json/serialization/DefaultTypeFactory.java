package org.eclipse.basyx.vab.coder.json.serialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;



/**
 * Default type factory
 *
 * @author kuhn
 *
 */
public class DefaultTypeFactory implements GSONToolsFactory {

	/**
	 * Create a map
	 */
	@Override
	public Map<String, Object> createMap() {
		return new HashMap<>();
	}


	/**
	 * Create a collection
	 */
	@Override
	public Collection<Object> createCollection() {
		return new ArrayList<>();
	}
}
