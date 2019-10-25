package org.eclipse.basyx.submodel.metamodel.map.reference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.reference.ReferenceFacade;

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
	
	public static final String KEY = "keys";

	/**
	 * Constructor
	 */
	public Reference() {
		setKeys(new ArrayList<IKey>());
	}

	/**
	 * 
	 * @param key Unique reference in its name space.
	 */
	public Reference(List<IKey> keys) {
		setKeys(keys);
	}

	public Reference(Map<String, Object> reference) {
		putAll(reference);
	}

	@Override
	public List<IKey> getKeys() {
		return new ReferenceFacade(this).getKeys();
	}

	public void setKeys(List<IKey> keys) {
		// Copy the key list to make sure an actual hashmap is put inside this map
		List<IKey> copy = new ArrayList<>();
		for (IKey key : keys) {
			copy.add(new Key(key.getType(), key.isLocal(), key.getValue(), key.getidType()));
		}
		new ReferenceFacade(this).setKeys(copy);
	}
}
