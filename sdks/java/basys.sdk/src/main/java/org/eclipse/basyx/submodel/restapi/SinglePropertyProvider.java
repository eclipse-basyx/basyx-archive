package org.eclipse.basyx.submodel.restapi;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Handles properties according to AAS meta model
 *
 * @author schnicke
 *
 */
public class SinglePropertyProvider extends MetaModelProvider {

	private IModelProvider proxy;

	public SinglePropertyProvider(IModelProvider proxy) {
		this.proxy = proxy;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getModelPropertyValue(String path) throws Exception {
		path = VABPathTools.stripSlashes(path);

		// Handle "/value" path
		if (path.equals(Property.VALUE)) {
			// Build map containing value & valueType
			Map<String, Object> p = (Map<String, Object>) proxy.getModelPropertyValue("");
			Map<String, Object> ret = new HashMap<>();
			Object o = p.get(Property.VALUE);

			// Wrap return value in map describing it
			if (o != null) {
				ret.put(Property.VALUE, o);
				ret.put(Property.VALUEID, p.get(Property.VALUEID));
				ret.put(Property.VALUETYPE, PropertyValueTypeDefHelper.getTypeWrapperFromObject(o));
				return ret;
			} else {
				return null;
			}

		} else if (path.isEmpty()) {
			// Handle "" path by returning complete property
			return proxy.getModelPropertyValue("");
		} else {
			throw getUnknownPathException(path);
		}
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		path = VABPathTools.stripSlashes(path);
		// Only handle "/value" paths
		if (path.equals(Property.VALUE)) {
			// Set value and type
			proxy.setModelPropertyValue(Property.VALUE, newValue);
			proxy.setModelPropertyValue(Property.VALUETYPE, PropertyValueTypeDefHelper.getTypeWrapperFromObject(newValue));
		} else {
			throw new RuntimeException();
		}
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		proxy.createValue(path, newEntity);
	}

	@Override
	public void deleteValue(String path) throws Exception {
		proxy.deleteValue(path);
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		proxy.deleteValue(path, obj);
	}

	@Override
	public Object invokeOperation(String path, Object... parameter) throws Exception {
		throw new RuntimeException();
	}

}
