package org.eclipse.basyx.aas.backend.connected.aas.submodelelement;

import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IReferenceElement;
import org.eclipse.basyx.aas.api.resources.PropertyType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IReferenceElement
 * @author rajashek
 *
 */
public class ConnectedReferenceElement extends ConnectedDataElement implements IReferenceElement {
	public ConnectedReferenceElement(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	

	@Override
	public void setValue(IReference ref) {
		getProxy().updateElementValue(constructPath(Property.VALUE), ref);
		
	}

	@Override
	public IReference getValue() {
		return (IReference)getProxy().readElementValue(constructPath(Property.VALUE));
	}


	@Override
	public PropertyType getPropertyType() {
		throw new FeatureNotImplementedException();

	}


	@Override
	public void setValue(Object obj) {
		throw new FeatureNotImplementedException();

	}


	@Override
	public void setValueId(Object obj) {
		throw new FeatureNotImplementedException();

		
	}


	@Override
	public Object getValueId() {
		throw new FeatureNotImplementedException();

	}

}
