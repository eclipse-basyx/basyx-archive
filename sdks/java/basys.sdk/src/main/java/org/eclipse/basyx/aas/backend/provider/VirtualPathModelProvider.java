package org.eclipse.basyx.aas.backend.provider;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.tools.VABPathTools;
import org.eclipse.basyx.vab.provider.lambda.VABLambdaProvider;

/**
 * Additional VAB provider specific for providing AAS and submodels together with other SDKs.
 * 
 * The VAB provides generic models without considering high-level information. For example, Operation submodel elements
 * are realized as a Map. The function is contained and can be accessed at /operations/opId/invokable. Therefore
 * invoking the Operation directly at /operations/opId/ attempts to invoke the Map, which does not have any effect for
 * the VABModelProvider.
 *
 * This provider maps these requests to the VAB primitives to generate the desired behavior as it is described in the
 * BaSys documentation.
 * 
 * @author espen
 *
 */
public class VirtualPathModelProvider implements IModelProvider {
	private IModelProvider modelProvider;

	/**
	 * Default constructor - based on an empty model with a lambda provider
	 */
	public VirtualPathModelProvider() {
		this(new HashMap<String, Object>());
	}

	/**
	 * Creates a VPMP based on a lambda provider and a given model
	 */
	public VirtualPathModelProvider(Map<String, Object> model) {
		this.modelProvider = new VABLambdaProvider(model);
	}

	/**
	 * Creates a VPMP based on a given IModelProvider. Should be a low-level VAB model provider, for example a
	 * VABHashmapProvider.
	 */
	public VirtualPathModelProvider(IModelProvider modelProvider) {
		this.modelProvider = modelProvider;
	}

	@Override
	public Object getModelPropertyValue(String path) throws Exception {
		return modelProvider.getModelPropertyValue(path);
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		newValue = unwrapParameter(newValue);
		modelProvider.setModelPropertyValue(path, newValue);
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		modelProvider.createValue(path, newEntity);
	}

	@Override
	public void deleteValue(String path) throws Exception {
		modelProvider.deleteValue(path);
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		modelProvider.deleteValue(path, obj);
	}

	@Override
	public Object invokeOperation(String path, Object[] parameters) throws Exception {
		// Unwrap parameters, if they are wrapped
		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = unwrapParameter(parameters[i]);
		}

		// Invoke /invokable instead of an Operation property
		Object childElement = modelProvider.getModelPropertyValue(path);
		if (childElement instanceof Map<?, ?> && ((Map<?, ?>) childElement).containsKey("invokable")) {
			path = VABPathTools.concatenatePaths(path, "invokable");
		}

		// Forward call to model provider
		return modelProvider.invokeOperation(path, parameters);
	}

	@SuppressWarnings("unchecked")
	protected Object unwrapParameter(Object parameter) {
		if (parameter instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) parameter;
			if (map.get("valueType") != null && map.get("value") != null) {
				return map.get("value");
			}
		}
		return parameter;
	}
}
