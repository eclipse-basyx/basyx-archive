package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IRelationshipElement;
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
	public void setFirst(IReference first) {
	map.put(RelationshipElement.FIRST, first);
		
	}

	@Override
	public IReference getFirst() {
	return (IReference)	map.get(RelationshipElement.FIRST);
	}

	@Override
	public void setSecond(IReference second) {
		map.put(RelationshipElement.SECOND, second);
		
	}

	@Override
	public IReference getSecond() {
		return (IReference)	map.get(RelationshipElement.FIRST);
	}

}
