package org.eclipse.basyx.submodel.restapi;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Handles properties according to AAS meta model
 *
 * @author schnicke
 *
 */
public class PropertyProvider extends MetaModelProvider {

	private IModelProvider proxy;

	public PropertyProvider(IModelProvider proxy) {
		this.proxy = proxy;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getModelPropertyValue(String path) throws ProviderException {
		path = VABPathTools.stripSlashes(path);

		// Handle "/value" path
		if (path.equals(Property.VALUE)) {
			// return value
			Map<String, Object> p = (Map<String, Object>) proxy.getModelPropertyValue("");
			return p.get(Property.VALUE);

		} else if (path.isEmpty()) {
			// Handle "" path by returning complete property
			return proxy.getModelPropertyValue("");
		} else {
			throw getUnknownPathException(path);
		}
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws ProviderException {
		path = VABPathTools.stripSlashes(path);
		// Only handle "/value" paths
		if (path.equals(Property.VALUE)) {
			// Set value and type
			proxy.setModelPropertyValue(Property.VALUE, newValue);
			proxy.setModelPropertyValue(Property.VALUETYPE, PropertyValueTypeDefHelper.getTypeWrapperFromObject(newValue));
		} else {
			throw new MalformedRequestException("Given Set path '" + path + "' does not end in /value");
		}
	}

	@Override
	public void createValue(String path, Object newEntity) throws ProviderException {
		throw new MalformedRequestException("Create not allowed at path '" + path + "'");
	}

	@Override
	public void deleteValue(String path) throws ProviderException {
		throw new MalformedRequestException("Delete not allowed at path '" + path + "'");
	}

	@Override
	public void deleteValue(String path, Object obj) throws ProviderException {
		throw new MalformedRequestException("Delete not allowed at path '" + path + "'");
	}

	@Override
	public Object invokeOperation(String path, Object... parameter) throws ProviderException {
		throw new MalformedRequestException("Invoke not allowed at path '" + path + "'");
	}

}
