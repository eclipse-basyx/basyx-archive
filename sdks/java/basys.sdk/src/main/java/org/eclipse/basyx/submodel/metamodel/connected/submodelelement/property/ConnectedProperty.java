package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.property;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.PropertyType;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedDataElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
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
	public void setValueId(String obj) {
		getProxy().setModelPropertyValue(SingleProperty.VALUEID, obj);
		
	}

	@Override
	public String getValueId() {
		return (String) getProxy().getModelPropertyValue(SingleProperty.VALUEID);
	}

	@SuppressWarnings("unchecked")
	protected <T> T retrieveObject() {
		return (T) ((Map<String, Object>) getProxy().getModelPropertyValue(SingleProperty.VALUE))
				.get(SingleProperty.VALUE);
	}
}
