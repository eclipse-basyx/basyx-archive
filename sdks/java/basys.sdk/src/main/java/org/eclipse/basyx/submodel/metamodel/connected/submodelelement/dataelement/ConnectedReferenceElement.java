package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * "Connected" implementation of IReferenceElement
 * @author rajashek
 *
 */
public class ConnectedReferenceElement extends ConnectedDataElement implements IReferenceElement {
	public ConnectedReferenceElement(VABElementProxy proxy) {
		super(proxy);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getValue() {
		return Reference.createAsFacade((Map<String, Object>) getElem().getPath(Property.VALUE));
	}
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.REFERENCEELEMENT;
	}

	@Override
	public ReferenceElement getLocalCopy() {
		return ReferenceElement.createAsFacade(getElem()).getLocalCopy();
	}

	@Override
	public void setValue(IReference value) {
		setValue((Object) value);
	}
}