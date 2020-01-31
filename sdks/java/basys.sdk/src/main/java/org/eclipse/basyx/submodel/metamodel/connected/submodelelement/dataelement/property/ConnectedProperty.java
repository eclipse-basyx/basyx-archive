package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.property;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.PropertyType;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedDataElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
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
	public String getValueId() {
		return (String) getProxy().getModelPropertyValue(Property.VALUEID);
	}

	@SuppressWarnings("unchecked")
	protected <T> T retrieveObject() {
		return (T) ((Map<String, Object>) getProxy().getModelPropertyValue(Property.VALUE))
				.get(Property.VALUE);
	}
}
