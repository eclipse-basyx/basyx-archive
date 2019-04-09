package org.eclipse.basyx.aas.backend.http.tools.factory;

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
		return new HashMap<String, Object>();
	}
	

	/**
	 * Create a set
	 */
	@Override
	public Set<Object> createSet() {
		return new HashSet<Object>();
	}

	
	/**
	 * Create an ArrayList
	 */
	@Override
	public List<Object> createList() {
		return new ArrayList<Object>();
	}

}
