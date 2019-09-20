package org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.IProperty;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.PropertyType;
import org.eclipse.basyx.aas.backend.connected.aas.submodelelement.ConnectedDataElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IProperty
 * @author rajashek
 *
 */
public abstract class ConnectedProperty extends ConnectedDataElement implements IProperty {
	private PropertyType type;

	public ConnectedProperty(PropertyType type, VABElementProxy proxy) {
		super(proxy);		
		this.type = type;
	}

	@Override
	public PropertyType getPropertyType() {
		return type;
	}

	@Override
	public void setValueId(String obj) {
		getProxy().setModelPropertyValue(Property.VALUEID, obj);
		
	}

	@Override
	public String getValueId() {
		return (String) getProxy().getModelPropertyValue(Property.VALUEID);
	}
}
