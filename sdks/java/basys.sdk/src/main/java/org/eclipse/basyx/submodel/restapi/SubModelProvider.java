package org.eclipse.basyx.submodel.restapi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;

/**
 * Additional VAB provider specific for providing submodels together
 * with other SDKs by implementing the submodel API.
 *
 * The VAB provides generic models without considering high-level information.
 * For example, Operation submodel elements are realized as a Map. The function
 * is contained and can be accessed at /operations/opId/invokable. Therefore
 * invoking the Operation directly at /operations/opId/ attempts to invoke the
 * Map, which does not have any effect for the VABModelProvider.
 *
 * This provider maps these requests to the VAB primitives to generate the
 * desired behavior as it is described in the BaSys documentation.
 *
 * @author espen, schnicke
 *
 */
public class SubModelProvider extends MetaModelProvider {

	// The VAB model provider containing the model this SubModelProvider is based on
	private IModelProvider modelProvider;

	/**
	 * Default constructor - based on an empty submodel with a lambda provider
	 */
	public SubModelProvider() {
		this(new SubModel());
	}

	/**
	 * Creates a SubModelProvider based on a lambda provider and a given model
	 */
	public SubModelProvider(SubModel model) {
		modelProvider = new VABLambdaProvider(model);
	}

	/**
	 * Creates a SubModelProvider based on a Map that is supposed to represent the submodel
	 */
	public SubModelProvider(Map<String, Object> model) {
		modelProvider = new VABLambdaProvider(model);
	}

	/**
	 * Creates a SubModelProvider based on a given IModelProvider. Should be a
	 * low-level VAB model provider, for example a VABMapProvider.
	 */
	public SubModelProvider(IModelProvider modelProvider) {
		this.modelProvider = modelProvider;
	}

	/**
	 * Strips submodel prefix if it exists
	 *
	 * @param path
	 * @return
	 */
	private String removeSubmodelPrefix(String path) {
		path = VABPathTools.stripSlashes(path);
		if (path.startsWith("submodel/")) {
			path = path.replaceFirst("submodel/", "");
		} else if (path.equals("submodel")) {
			path = "";
		}
		path = VABPathTools.stripSlashes(path);
		return path;
	}

	/**
	 * Creates an IModelProvider for handling accesses to the elements within the submodel
	 * 
	 * @return returns the SubmodelElementProvider pointing to the contained submodelelements
	 */
	private SubmodelElementMapProvider getElementProvider() {
		IModelProvider elementProxy = new VABElementProxy(SubModel.SUBMODELELEMENT, modelProvider);
		return new SubmodelElementMapProvider(elementProxy);
	}

	/**
	 * Returns the whole submodel, but the submodel element map is replaced by a collection.
	 */
	@SuppressWarnings("unchecked")
	private Object getSubModel() {
		// For access on the container property root, return the whole model
		Map<String, Object> map = new HashMap<>();
		Object o = modelProvider.getModelPropertyValue("");
		map.putAll((Map<String, Object>) o);

		// Change internal maps to sets for submodelElements
		setMapToSet(map, SubModel.SUBMODELELEMENT);

		return map;
	}

	/**
	 * Converts a map entry to a set, if it is also a map
	 */
	@SuppressWarnings("unchecked")
	private void setMapToSet(Map<String, Object> map, String key) {
		Object mapEntry = map.get(key);
		if (mapEntry instanceof Map<?, ?>) {
			Map<String, Object> elements = (Map<String, Object>) mapEntry;
			map.put(key, new HashSet<Object>(elements.values()));
		}
	}

	@Override
	public Object getModelPropertyValue(String path) throws ProviderException {
		VABPathTools.checkPathForNull(path);
		path = removeSubmodelPrefix(path);
		if (path.isEmpty()) {
			return getSubModel();
		} else {
			return getElementProvider().getModelPropertyValue(path);
		}
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws ProviderException {
		path = removeSubmodelPrefix(path);
		if (path.isEmpty()) {
			modelProvider.setModelPropertyValue("", newValue);
		} else {
			getElementProvider().setModelPropertyValue(path, newValue);
		}
	}

	@Override
	public void createValue(String path, Object newEntity) throws ProviderException {
		path = removeSubmodelPrefix(path);
		if (path.isEmpty()) {
			// not possible to overwrite existing submodels
			throw new MalformedRequestException("Invalid access");
		} else {
			getElementProvider().createValue(path, newEntity);
		}
	}

	@Override
	public void deleteValue(String path) throws ProviderException {
		path = removeSubmodelPrefix(path);
		if (path.isEmpty()) {
			modelProvider.deleteValue("");
		} else {
			getElementProvider().deleteValue(path);
		}
	}

	@Override
	public void deleteValue(String path, Object obj) throws ProviderException {
		path = removeSubmodelPrefix(path);
		if (path.isEmpty()) {
			throw new MalformedRequestException("Invalid access");
		} else {
			getElementProvider().deleteValue(path, obj);
		}
	}

	@Override
	public Object invokeOperation(String path, Object... parameters) throws ProviderException {
		path = removeSubmodelPrefix(path);
		if (path.isEmpty()) {
			throw new MalformedRequestException("Invalid access");
		} else {
			return getElementProvider().invokeOperation(path, parameters);
		}
	}
}
