package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.property;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.IProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * Supports with creation of connected properties
 * 
 * @author schnicke
 *
 */
public class ConnectedPropertyFactory {

	/**
	 * Creates a connected property based on the given type
	 * 
	 * @param proxy
	 *            the proxy element pointing to the specific property
	 * @param typeDef
	 * @return
	 */
	public static IProperty createProperty(VABElementProxy proxy, PropertyValueTypeDef typeDef) {
		if (typeDef == PropertyValueTypeDef.Container) {
			return new ConnectedContainerProperty(proxy);
		} else if (typeDef == PropertyValueTypeDef.Map) {
			return new ConnectedMapProperty(proxy);
		} else if (typeDef == PropertyValueTypeDef.Collection) {
			return new ConnectedCollectionProperty(proxy);
		} else {
			// It is assumed to be an atomic value (e.g. integer, double, ...)
			ConnectedSingleProperty conProp = new ConnectedSingleProperty(proxy);
			return conProp;
		}
	}}
