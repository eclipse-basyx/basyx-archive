package org.eclipse.basyx.aas.backend.connected.property;

import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.PropertyType;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.enums.DataObjectType;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Abstract base class for each connected property. <br />
 * It operates based on the VWiD meta model
 * 
 * @author schnicke
 *
 */
public abstract class ConnectedProperty extends ConnectedElement implements IProperty {

	private PropertyType propertyType;

	public ConnectedProperty(PropertyType type, String path, VABElementProxy proxy) {
		super(path, proxy);
		this.propertyType = type;
	}

	@Override
	public DataObjectType getDataType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PropertyType getPropertyType() {
		return propertyType;
	}
}