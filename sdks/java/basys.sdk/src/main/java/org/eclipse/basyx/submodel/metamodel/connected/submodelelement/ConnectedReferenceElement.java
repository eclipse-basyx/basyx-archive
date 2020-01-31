package org.eclipse.basyx.submodel.metamodel.connected.submodelelement;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.Property;
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
	

	@Override
	public void setValue(IReference ref) {
		getProxy().setModelPropertyValue(Property.VALUE, ref);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getValue() {
		return Reference.createAsFacade((Map<String, Object>) getElem().getPath(Property.VALUE));
	}

}