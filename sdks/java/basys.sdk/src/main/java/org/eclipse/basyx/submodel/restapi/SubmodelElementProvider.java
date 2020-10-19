package org.eclipse.basyx.submodel.restapi;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.DataElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Provider that handles container properties. Container properties can contain other submodel elements.
 *
 * @author espen
 *
 */
public class SubmodelElementProvider extends MetaModelProvider {
	// Constants for API-Access
	public static final String ELEMENTS = "submodelElements";

	// The VAB model provider containing the submodelElements this SubmodelElementProvider is based on
	// Assumed to be a map that maps idShorts to the submodel elements
	private IModelProvider modelProvider;

	/**
	 * Constructor based on a model provider that contains the container property
	 */
	public SubmodelElementProvider(IModelProvider provider) {
		this.modelProvider = provider;
	}

	/**
	 * Method for wrapping a generic Map-SubmodelElement in a concrete provider
	 * 
	 * @param element      The data with the submodel element
	 * @param genericProxy A generic element provider for the element
	 * @return A specific submodel element model provider (e.g. OperationProvider)
	 */
	public static IModelProvider getElementProvider(Map<String, Object> element, IModelProvider genericProxy) {
		if (DataElement.isDataElement(element)) {
			return new PropertyProvider(genericProxy);
		} else if (Operation.isOperation(element)) {
			return new OperationProvider(genericProxy);
		} else if (SubmodelElementCollection.isSubmodelElementCollection(element)) {
			return new SubmodelElementCollectionProvider(genericProxy);
		} else {
			return genericProxy;
		}
	}

	/**
	 * The elements are stored in a map => convert them to a list
	 */
	@SuppressWarnings("unchecked")
	protected Collection<Map<String, Object>> getElementsList() {
		Object elements = modelProvider.getModelPropertyValue("");
		Map<String, Map<String, Object>> all = (Map<String, Map<String, Object>>) elements;

		// Feed all ELements through their Providers, in case someting needs to be done to them (e.g. smElemCollections)
		return all.entrySet().stream().map(e -> (Map<String, Object>) handleDetailGet(ELEMENTS + "/" + e.getKey())).collect(Collectors.toList());
	}
	
	/**
	 * Handles first-level access on submodel elements (e.g. /properties/)
	 */
	private Object handleQualifierGet(String qualifier, String path) {
		if (qualifier.equals(ELEMENTS)) {
			// returns all elements
			return getElementsList();
		} else {
			// No other qualifier in a submodel element container can be directly accessed
			throw getUnknownPathException(path);
		}
	}

	/**
	 * Single elements can be directly accessed in maps => return a proxy
	 */
	private IModelProvider getElementProxy(String[] pathElements) {
		String idShort = pathElements[1];
		return new VABElementProxy(idShort, modelProvider);
	}

	@SuppressWarnings("unchecked")
	private Object handleDetailGet(String path) {
		// Build new proxy pointing at sub-property of a submodelelement and forward the
		// remaininig part of the path to an appropriate provider
		String[] pathElements = VABPathTools.splitPath(path);
		String qualifier = pathElements[0];
		String subPath = VABPathTools.buildPath(pathElements, 2);
		IModelProvider elementProxy = getElementProxy(pathElements);
		Map<String, Object> element = (Map<String, Object>) elementProxy.getModelPropertyValue("");

		if (qualifier.equals(ELEMENTS)) {
			return getElementProvider(element, elementProxy).getModelPropertyValue(subPath);
		} else {
			throw new MalformedRequestException("Invalid access");
		}
	}

	@Override
	public Object getModelPropertyValue(String path) throws ProviderException {
		String[] pathElements = VABPathTools.splitPath(path);
		String qualifier = pathElements[0];
		if (pathElements.length == 1) {
			return handleQualifierGet(qualifier, path);
		} else {
			return handleDetailGet(path);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setModelPropertyValue(String path, Object newValue) throws ProviderException {
		String[] pathElements = VABPathTools.splitPath(path);
		String qualifier = pathElements[0];
		if (pathElements.length < 2 || !qualifier.equals(ELEMENTS)) {
			// only possible to set values in a data elements, currently
			throw new MalformedRequestException("Invalid access");
		}

		IModelProvider elementProxy = getElementProxy(pathElements);
		Map<String, Object> element = (Map<String, Object>) elementProxy.getModelPropertyValue("");
		newValue = unwrapParameter(newValue);
		String subPath = VABPathTools.buildPath(pathElements, 2);
		if (Operation.isOperation(element)) {
			throw new MalformedRequestException("Invalid access");
		} else if (Property.isProperty(element)) {
			new PropertyProvider(elementProxy).setModelPropertyValue(subPath, newValue);
		} else if (SubmodelElementCollection.isSubmodelElementCollection(element)) {
			new SubmodelElementCollectionProvider(elementProxy).setModelPropertyValue(subPath, newValue);
		} else {
			// API for other elements not specified, yet => let modelprovider resolve request
			elementProxy.setModelPropertyValue(subPath, newValue);
		}
	}

	@Override
	public void createValue(String path, Object newEntity) throws ProviderException {
		if (path.equals(ELEMENTS)) {
			createSubmodelElement(newEntity);
		} else {
			String[] pathElements = VABPathTools.splitPath(path);
			IModelProvider elementProxy = getElementProxy(pathElements);
			String subPath = VABPathTools.buildPath(pathElements, 2);
			// API for other elements not specified, yet => let modelprovider resolve request
			elementProxy.createValue(subPath, newEntity);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteValue(String path) throws ProviderException {
		String[] pathElements = VABPathTools.splitPath(path);
		String qualifier = pathElements[0];
		String subPath = VABPathTools.buildPath(pathElements, 2);
		IModelProvider elementProvider = modelProvider;
		
		// If the first Element is a Collection, use its Provider
		if(pathElements.length > 2) {
			IModelProvider elementProxy = getElementProxy(pathElements);
			Map<String, Object> element = (Map<String, Object>) elementProxy.getModelPropertyValue("");
			elementProvider = getElementProvider(element, elementProxy); 
		}

		if (qualifier.equals(ELEMENTS)) {
			// Delete a specific submodel element
			elementProvider.deleteValue(subPath);
		} else {
			throw new MalformedRequestException("Unknown access path " + path);
		}
	}

	@Override
	public void deleteValue(String path, Object obj) {
		String[] pathElements = VABPathTools.splitPath(path);
		String qualifier = pathElements[0];
		if (!qualifier.equals(ELEMENTS)) {
			// only possible to delete values from data elements (or in collections)
			throw new MalformedRequestException("Invalid access");
		}
		IModelProvider elementProxy = getElementProxy(pathElements);
		String subPath = VABPathTools.buildPath(pathElements, 2);
		elementProxy.deleteValue(subPath, obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object invokeOperation(String path, Object... parameters) throws ProviderException {
		String[] pathElements = VABPathTools.splitPath(path);
		String subPath = VABPathTools.buildPath(pathElements, 2);

		String qualifier = pathElements[0];
		if (!qualifier.equals(ELEMENTS)) {
			throw new MalformedRequestException("Invalid access");
		}
		
		IModelProvider elementProxy = getElementProxy(pathElements);
		Map<String, Object> element = (Map<String, Object>) elementProxy.getModelPropertyValue("");

		return getElementProvider(element, elementProxy).invokeOperation(subPath, parameters);
	}

	@SuppressWarnings("unchecked")
	private void createSubmodelElement(Object newEntity) {
		// Create Operation or DataElement in a map
		String id = SubmodelElement.createAsFacade((Map<String, Object>) newEntity).getIdShort();
		modelProvider.createValue(id, newEntity);
	}
}
