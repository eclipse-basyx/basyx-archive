package org.eclipse.basyx.aas.metamodel.hashmap.aas.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IKey;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.metamodel.facades.ReferenceFacade;

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
public class Reference extends HashMap<String, Object> implements IReference {
	private static final long serialVersionUID = 1L;
	
	public static final String KEY="key";

	/**
	 * Constructor
	 */
	public Reference() {
		put(KEY, new ArrayList<Key>());
	}

	/**
	 * 
	 * @param key Unique reference in its name space.
	 */
	public Reference(List<Key> key) {
		put(KEY, key);
	}

	public Reference(Map<String, Object> reference) {
		putAll(reference);
	}

	@Override
	public List<IKey> getKeys() {
		return new ReferenceFacade(this).getKeys();
	}

	@Override
	public void setKeys(List<IKey> keys) {
		new ReferenceFacade(this).setKeys(keys);
		
	}
}
