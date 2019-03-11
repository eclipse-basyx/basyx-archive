package org.eclipse.basyx.aas.metamodel.facades;

import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Key;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

/**
 * Facade providing access to a map containing the ReferenceFacade structure
 * 
 * @author rajashek
 *
 */

public class ReferenceFacade implements IReference {
	private Map<String, Object> map;
	public ReferenceFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Key> getKeys() {
		return (List<Key>)map.get(Reference.KEY);
	}

	@Override
	public void setKeys(List<Key> keys) {
		map.put(Reference.KEY,keys);
		
	}

}
