package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.property;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.IReferenceElement;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedDataElement;
import org.eclipse.basyx.submodel.metamodel.facade.reference.ReferenceFacade;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
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
		getProxy().setModelPropertyValue(SingleProperty.VALUE, ref);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getValue() {
		return new ReferenceFacade((Map<String, Object>) getElem().getPath(SingleProperty.VALUE));
	}

}
