package org.eclipse.basyx.aas.metamodel.facades;

import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.parts.IConceptDictionary;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.parts.ConceptDictionary;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;

/**
 * Facade providing access to a map containing the ConceptDictionary structure
 *  
 * @author rajashek
 *
 */

public class ConceptDictionaryFacade implements IConceptDictionary {
	
	private Map<String, Object> map;

	public ConceptDictionaryFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public String getIdshort() {
		return (String) map.get(Referable.IDSHORT);
	}

	@Override
	public String getCategory() {
		return (String) map.get(Referable.CATEGORY);
	}

	@Override
	public String getDescription() {
		return (String) map.get(Referable.DESCRIPTION);
	}

	@Override
	public IReference  getParent() {
		return (IReference)map.get(Referable.PARENT);
	}

	@Override
	public void setIdshort(String idShort) {
		map.put(Referable.IDSHORT, idShort);
		
	}

	@Override
	public void setCategory(String category) {
		map.put(Referable.CATEGORY, category);
		
	}

	@Override
	public void setDescription(String description) {
		map.put(Referable.DESCRIPTION, description);
		
	}

	@Override
	public void setParent(IReference  obj) {
		map.put(Referable.PARENT, obj);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashSet<String> getConceptDescription() {
		return (HashSet<String>)map.get(ConceptDictionary.CONCEPTDESCRIPTION);
	}

	@Override
	public void setConceptDescription(HashSet<String> ref) {
		map.put(ConceptDictionary.CONCEPTDESCRIPTION, ref);
		
	}

}
