package org.eclipse.basyx.vab.coder.json.serialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



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
	 * Create a set
	 */
	@Override
	public Set<Object> createSet() {
		return new HashSet<>();
	}


	/**
	 * Create an ArrayList
	 */
	@Override
	public List<Object> createList() {
		return new ArrayList<>();
	}
}
