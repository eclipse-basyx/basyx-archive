package org.eclipse.basyx.submodel.restapi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Provider that handles container properties. Container properties can contain other submodel elements.
 *
 * @author espen
 *
 */
public class ContainerPropertyProvider extends MetaModelProvider {

	private IModelProvider modelProvider;

	/**
	 * Constructor based on a model provider that contains the container property
	 */
	public ContainerPropertyProvider(IModelProvider provider) {
		this.modelProvider = provider;
	}

	private Object getSubModel() throws Exception {
		// For access on the container property root, return the whole model
		Map<String, Object> map = getModel();

		// Change internal maps to sets for submodelElements, properties and operations
		setMapToSet(map, SubModel.PROPERTIES);
		setMapToSet(map, SubModel.OPERATIONS);
		setMapToSet(map, SubModel.SUBMODELELEMENT);

		return map;
	}

	private Object handleQualifierGet(String path, String qualifier) throws Exception {
		if (qualifier.equals(SubModel.SUBMODELELEMENT) || qualifier.equals(SubModel.PROPERTIES) || qualifier.equals(SubModel.OPERATIONS)) {
			// if all submodel elements, data elements or operations are accessed (e.g.
			// "/operations")
			// Convert contained element map to set and return result (see API)
			Map<String, Object> map = getModel();
			setMapToSet(map, qualifier);
			return map.get(qualifier);
		} else {
			// No other property in a container property can be directly accessed
			throw getUnknownPathException(path);
		}
	}

	private Object handleDetailGet(String qualifier, String[] pathElements) throws Exception {
		// Build new proxy pointing at sub-property of a submodelelement and forward the
		// remaininig part of the path
		// to an appropriate provider
		String elementAccess = pathElements[1];
		String subPath = VABPathTools.buildPath(pathElements, 2);
		VABElementProxy propertyProxy = new VABElementProxy("", this.modelProvider).getDeepProxy(VABPathTools.append(qualifier, elementAccess));
		if (propertyProxy.getModelPropertyValue(SubModel.PROPERTIES) != null) {
			// Assume container property, if it has "dataElements"
			return new ContainerPropertyProvider(propertyProxy).getModelPropertyValue(subPath);
		} else {
			// Assume single property otherwise
			return new SinglePropertyProvider(propertyProxy).getModelPropertyValue(subPath);
		}
	}

	@Override
	public Object getModelPropertyValue(String path) throws Exception {
		path = VABPathTools.stripSlashes(path);
		String[] pathElements = VABPathTools.splitPath(path);

		if (path.isEmpty()) {
			return getSubModel();
		} else {
			String qualifier = pathElements[0];
			if (pathElements.length == 1) {
				return handleQualifierGet(path, qualifier);
			} else if (qualifier.equals(SubModel.SUBMODELELEMENT) || qualifier.equals(SubModel.PROPERTIES) || qualifier.equals(SubModel.OPERATIONS)) {
				return handleDetailGet(qualifier, pathElements);
			} else {
				// Can not access a path with more than one path element that does not point to
				// a submodelelement
				throw getUnknownPathException(path);
			}
		}
	}

	/**
	 * Returns the contained model and copies the first layer of the HashMap
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getModel() throws Exception {
		Map<String, Object> map = new HashMap<>();
		Object o = modelProvider.getModelPropertyValue("");
		map.putAll((Map<String, Object>) o);

		return map;
	}

	/**
	 * Converts a map entry to a set, if it is also a map
	 */
	@SuppressWarnings("unchecked")
	private void setMapToSet(Map<String, Object> map, String key) {
		Object mapEntry = map.get(key);
		if (mapEntry instanceof Map<?, ?>) {
			Map<String, Object> dataElements = (Map<String, Object>) mapEntry;
			map.put(key, new HashSet<Object>(dataElements.values()));
		}
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		newValue = unwrapParameter(newValue);
		modelProvider.setModelPropertyValue(path, newValue);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		path = VABPathTools.stripSlashes(path);
		if (path.isEmpty()) {
			// Overwrite whole submodel
			modelProvider.setModelPropertyValue("", newEntity);
		} else if (path.equals(SubModel.PROPERTIES) || path.equals(SubModel.OPERATIONS)) {
			// Create Operation or Property
			String id = SubmodelElement.createAsFacade((Map<String, Object>) newEntity).getIdShort();
			modelProvider.createValue(VABPathTools.concatenatePaths(path, id), newEntity);
			modelProvider.createValue(VABPathTools.concatenatePaths(SubModel.SUBMODELELEMENT, id), newEntity);
		} else if (path.equals(SubModel.SUBMODELELEMENT)) {
			// Create SubmodelElement
			String id = SubmodelElement.createAsFacade((Map<String, Object>) newEntity).getIdShort();
			modelProvider.createValue(VABPathTools.concatenatePaths(SubModel.SUBMODELELEMENT, id), newEntity);
		} else {
			// Can not newly create any other elements in a property container
			throw new RuntimeException("Unknown access path " + path);
		}
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
	public Object invokeOperation(String path, Object... parameters) throws Exception {
		// Assume that an operation shall be invoked
		return new OperationProvider(modelProvider).invokeOperation(path, parameters);
	}
}
