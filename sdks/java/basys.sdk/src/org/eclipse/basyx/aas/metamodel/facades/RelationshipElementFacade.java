package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IRelationshipElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.RelationshipElement;
/**
 * Facade providing access to a map containing the RelationshipElement structure
 * @author rajashek
 *
 */
public class RelationshipElementFacade implements IRelationshipElement {
	private Map<String, Object> map;
	public RelationshipElementFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public void setFirst(Reference first) {
	map.put(RelationshipElement.FIRST, first);
		
	}

	@Override
	public Reference getFirst() {
	return (Reference)	map.get(RelationshipElement.FIRST);
	}

	@Override
	public void setSecond(Reference second) {
		map.put(RelationshipElement.SECOND, second);
		
	}

	@Override
	public Reference getSecond() {
		return (Reference)	map.get(RelationshipElement.FIRST);
	}

}
