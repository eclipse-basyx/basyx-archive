package org.eclipse.basyx.submodel.restapi;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;
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
		if (path.equals(SingleProperty.VALUE)) {
			// Build map containing value & valueType
			Map<String, Object> p = (Map<String, Object>) proxy.getModelPropertyValue("");
			Map<String, Object> ret = new HashMap<>();
			Object o = p.get(SingleProperty.VALUE);

			// Wrap return value in map describing it
			if (o != null) {
				ret.put(SingleProperty.VALUE, o);
				ret.put(SingleProperty.VALUEID, p.get(SingleProperty.VALUEID));
				ret.put(SingleProperty.VALUETYPE, PropertyValueTypeDefHelper.fromObject(o));
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
		if (path.equals(SingleProperty.VALUE)) {
			// Set value and type
			proxy.setModelPropertyValue(SingleProperty.VALUE, newValue);
			proxy.setModelPropertyValue(SingleProperty.VALUETYPE, PropertyValueTypeDefHelper.fromObject(newValue));
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
