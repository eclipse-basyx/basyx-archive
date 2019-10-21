package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.property;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.IProperty;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates IProperties based on the attached meta data as specified in DAAS
 * document
 * 
 * @author schnicke
 *
 */
public class ConnectedPropertyFactory {

	private static Logger logger = LoggerFactory.getLogger(ConnectedPropertyFactory.class);

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
			
		
		logger.warn("Unknown property type");
		return null;
	}
}
