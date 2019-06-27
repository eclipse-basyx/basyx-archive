package org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property;

import java.util.Map;

import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Creates IProperties based on the attached meta data as specified in DAAS
 * document
 * 
 * @author schnicke
 *
 */
public class ConnectedPropertyFactory {
	@SuppressWarnings("unchecked")
	public IProperty createProperty(String path, VABElementProxy proxy) {
		Map<String, Object> property = (Map<String, Object>) proxy.readElementValue(path);
		if (property.containsKey("properties")) {
			return new ConnectedContainerProperty(path, proxy);
		} else if (property.containsKey("valueType")) {
			
			PropertyValueTypeDef valueType = null;
			Object vType1 = property.get("valueType");
			if (vType1 instanceof String) {
				// try outdated java valuetype
				valueType = PropertyValueTypeDefHelper.fromName((String) vType1);
			} else if (vType1 instanceof Map<?,?>) {
				// extract from hashmap
				String vType2 = (String) ((Map<String, Object>) ((Map<String, Object>) vType1).get("dataObjectType")).get("name");
				valueType = PropertyValueTypeDefHelper.fromName(vType2);
			
			}
			
			if (valueType == PropertyValueTypeDef.Map) {
				return new ConnectedMapProperty(path, proxy);
			} else if (valueType == PropertyValueTypeDef.Collection) {
				return new ConnectedCollectionProperty(path, proxy);
			} else {
				ConnectedSingleProperty conProp =  new ConnectedSingleProperty(path, proxy);
				conProp.putAllLocal(property);
				return conProp;
			} 
			
		} else if ((property.get("value") != null) && (property.get("idShort") != null)){
			// handle  property without valueType
			ConnectedSingleProperty conProp =  new ConnectedSingleProperty(path, proxy);
			conProp.putAllLocal(property);
			return conProp;
		}
			
		
		System.err.println("Unknown property type");
		return null;
	}
}
