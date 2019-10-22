package org.eclipse.basyx.submodel.metamodel.facade.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;

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
	public List<IKey> getKeys() {
		// Transform list of maps to set of IKey
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get(Reference.KEY);

		List<IKey> ret = new ArrayList<>();
		for (Map<String, Object> m : list) {
			ret.add(new KeyFacade(m));
		}

		return ret;
	}

	public void setKeys(List<IKey> keys) {
		map.put(Reference.KEY, keys);
	}

}
