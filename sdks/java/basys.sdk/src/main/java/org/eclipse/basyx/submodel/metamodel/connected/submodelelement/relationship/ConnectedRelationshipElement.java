package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.relationship;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.relationship.IRelationshipElement;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.relationship.RelationshipElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.relationship.RelationshipElementValue;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * "Connected" implementation of RelationshipElement
 * 
 * @author rajashek
 *
 */
public class ConnectedRelationshipElement extends ConnectedSubmodelElement implements IRelationshipElement {
	public ConnectedRelationshipElement(VABElementProxy proxy) {
		super(proxy);
	}

	public void setFirst(IReference first) {
		getProxy().setModelPropertyValue(RelationshipElement.FIRST, first);

	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getFirst() {
		return Reference.createAsFacade((Map<String, Object>) getElem().getPath(RelationshipElement.FIRST));
	}

	public void setSecond(IReference second) {
		getProxy().setModelPropertyValue(RelationshipElement.SECOND, second);

	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getSecond() {
		return Reference.createAsFacade((Map<String, Object>) getElem().getPath(RelationshipElement.SECOND));
	}
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.RELATIONSHIPELEMENT;
	}
	
	@Override
	public RelationshipElementValue getValue() {
		return new RelationshipElementValue(getFirst(), getSecond());
	}
}
