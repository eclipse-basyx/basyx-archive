package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.PropertyType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;
/**
 * Facade providing access to a map containing the DataSpecification structure
 * @author rajashek
 *
 */
public class PropertyFacade implements IProperty {

	private Map<String, Object> map;
	
	
	public PropertyFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public String getId() {
	return (String)map.get(Referable.IDSHORT);
	}

	@Override
	public void setId(String id) {
	map.put(Referable.IDSHORT, id);
		
	}

	@Override
	public PropertyType getPropertyType() {
		PropertyValueTypeDef type = PropertyValueTypeDefHelper.readTypeDef(map.get(Property.VALUETYPE));
		if (type == PropertyValueTypeDef.Collection) {
			return PropertyType.Collection;
		} else if (type == PropertyValueTypeDef.Map) {
			return PropertyType.Map;
		} else {
			return PropertyType.Single;
		}
	}

	@Override
	public void setValue(Object obj) {
		map.put(Property.VALUE, obj);
		
	}

	@Override
	public Object getValue() {
	return	map.get(Property.VALUE);
	}

	@Override
	public void setValueId(Object obj) {
		map.put(Property.VALUEID, obj);
		
	}

	@Override
	public Object getValueId() {
		return map.get(Property.VALUEID);
	}



}
