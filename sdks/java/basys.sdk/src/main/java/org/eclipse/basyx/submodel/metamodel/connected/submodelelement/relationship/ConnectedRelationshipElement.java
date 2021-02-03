package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.relationship;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.relationship.IRelationshipElement;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedSubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
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
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.RELATIONSHIPELEMENT;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public RelationshipElementValue getValue() {
		Object obj = getProxy().getModelPropertyValue(Property.VALUE);

		return RelationshipElementValue.createAsFacade((Map<String, Object>) obj);
	}

	@Override
	public RelationshipElement getLocalCopy() {
		return RelationshipElement.createAsFacade(getElem()).getLocalCopy();
	}

	@Override
	public void setValue(RelationshipElementValue value) {
		setValue((Object) value);
	}
}
