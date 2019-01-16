package org.eclipse.basyx.aas.metamodel.hashmap.aas.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reference as described by DAAS document <br/>
 * <br/>
 * Reference to either a model element of the same or another AAS or to an
 * external entity. A reference is an ordered list of keys, each key referencing
 * an element. The complete list of keys may for example be concatenated to a
 * path that then gives unique access to an element or entity.
 * 
 * @author schnicke
 *
 */
public class Reference extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public Reference() {
		put("key", new ArrayList<Key>());
	}

	/**
	 * 
	 * @param key
	 *            Unique reference in its name space.
	 */
	public Reference(List<Key> key) {
		put("key", key);
	}

	public Reference(Map<String, Object> reference) {

	}

	@SuppressWarnings("unchecked")
	public List<Key> getKeys() {
		return (List<Key>) get("key");
	}
}
