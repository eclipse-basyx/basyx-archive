package org.eclipse.basyx.submodel.restapi;

import java.util.Map;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Abstract class that acts as base for each handler of the AAS meta model
 *
 * @author schnicke
 *
 */
public abstract class MetaModelProvider implements IModelProvider {

	/**
	 * Unwraps a parameter by retrieving the "value" entry
	 *
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Object unwrapParameter(Object parameter) {
		if (parameter instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) parameter;
			// Parameters have a strictly defined order and may not be omitted at all.
			// Enforcing the structure with valueType is ok, but we should unwrap null values, too.
			if (map.get("valueType") != null && map.containsKey("value")) {
				return map.get("value");
			}
		}
		return parameter;
	}

	/**
	 * Creates generic exception with Unknown path message
	 *
	 * @param path
	 * @return
	 */
	protected RuntimeException getUnknownPathException(String path) {
		return new RuntimeException("Unknown path: " + path);
	}
}
