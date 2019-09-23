package org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.IProperty;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.SingleProperty;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;
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
	public IProperty createProperty(VABElementProxy proxy) {
		// Since the VABElementProxy is already pointing to the property, get empty
		// property
		Map<String, Object> property = (Map<String, Object>) proxy.getModelPropertyValue("");
		if (property.containsKey(SubModel.PROPERTIES)) {
			return new ConnectedContainerProperty(proxy);
		} else if (property.containsKey(SingleProperty.VALUETYPE)) {
			
			PropertyValueTypeDef valueType = PropertyValueTypeDefHelper.readTypeDef(property.get(SingleProperty.VALUETYPE));
						
			if (valueType == PropertyValueTypeDef.Map) {
				return new ConnectedMapProperty(proxy);
			} else if (valueType == PropertyValueTypeDef.Collection) {
				return new ConnectedCollectionProperty(proxy);
			} else {
				ConnectedSingleProperty conProp = new ConnectedSingleProperty(proxy);
				conProp.putAllLocal(property);
				return conProp;
			} 
			
		} else if ((property.get(SingleProperty.VALUE) != null) && (property.get(Referable.IDSHORT) != null)){
			// handle  property without valueType
			ConnectedSingleProperty conProp = new ConnectedSingleProperty(proxy);
			conProp.putAllLocal(property);
			return conProp;
		}
			
		
		System.err.println("Unknown property type");
		return null;
	}
}
