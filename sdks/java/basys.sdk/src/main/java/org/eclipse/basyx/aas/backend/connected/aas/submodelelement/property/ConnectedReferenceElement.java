package org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.IReferenceElement;
import org.eclipse.basyx.aas.backend.connected.aas.submodelelement.ConnectedDataElement;
import org.eclipse.basyx.aas.impl.metamodel.facades.ReferenceFacade;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.SingleProperty;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
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
