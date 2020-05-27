package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Connects to a PropertySingleValued as specified by DAAS containing a simple
 * value
 * 
 * @author schnicke
 *
 */
public class ConnectedProperty extends ConnectedDataElement implements IProperty {

	public ConnectedProperty(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public Object get() throws Exception {
		return retrieveObject();
	}

	@Override
	public void set(Object newValue) throws ProviderException {
		getProxy().setModelPropertyValue(Property.VALUE, newValue);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public String getValueType() {
		Object o = getProxy().getModelPropertyValue("");
		return PropertyValueTypeDefHelper.readTypeDef(((Map<String, Object>) o).get(Property.VALUETYPE)).toString();
	}

	@Override
	public String getValueId() {
		return (String) getProxy().getModelPropertyValue(Property.VALUEID);
	}

	@SuppressWarnings("unchecked")
	protected <T> T retrieveObject() {
		return (T) ((Map<String, Object>) getProxy().getModelPropertyValue(Property.VALUE)).get(Property.VALUE);
	}
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.PROPERTY;
	}

}
