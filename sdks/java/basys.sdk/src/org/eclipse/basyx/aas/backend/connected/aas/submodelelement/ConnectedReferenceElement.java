package org.eclipse.basyx.aas.backend.connected.aas.submodelelement;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IReferenceElement;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.ReferenceElement;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IReferenceElement
 * @author rajashek
 *
 */
public class ConnectedReferenceElement extends ConnectedElement implements IReferenceElement {
	public ConnectedReferenceElement(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	
	@Override
	public void setValue(IReference ref) {
		getProxy().updateElementValue(constructPath(ReferenceElement.VALUE), ref);
		
	}

	@Override
	public IReference getValue() {
		return (IReference)getProxy().readElementValue(constructPath(ReferenceElement.VALUE));
	}
}
