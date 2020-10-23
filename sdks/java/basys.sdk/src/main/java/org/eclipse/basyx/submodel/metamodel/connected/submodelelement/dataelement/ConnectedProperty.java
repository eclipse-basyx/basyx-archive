package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
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
		return getValue();
	}

	@Override
	public void set(Object newValue) throws ProviderException {
		getProxy().setModelPropertyValue(Property.VALUE, PropertyValueTypeDefHelper.prepareForSerialization(newValue));
	}

	@Override
	public PropertyValueTypeDef getValueType() {
		return PropertyValueTypeDefHelper.readTypeDef(getElem().getPath(Property.VALUETYPE));
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getValueId() {
		return Reference.createAsFacade((Map<String, Object>) getProxy().getModelPropertyValue(Property.VALUEID));
	}

	@SuppressWarnings("unchecked")
	protected <T> T retrieveObject() {
		return (T) getProxy().getModelPropertyValue(Property.VALUE);
	}
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.PROPERTY;
	}

	@Override
	public Object getValue() {
		Object value =  retrieveObject();
		if(value instanceof String) {
			return PropertyValueTypeDefHelper.getJavaObject(value, getValueType());
		}else {
			return value;
		}
	}
	
	@Override
	public void setValue(Object value) {
		this.set(value);
	}

}
