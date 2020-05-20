package org.eclipse.basyx.submodel.restapi;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.DataElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Provider that handles container properties. Container properties can contain other submodel elements.
 *
 * @author espen
 *
 */
public abstract class AbstractSubmodelElementProvider extends MetaModelProvider {
	// Constants for API-Access
	public static final String ELEMENTS = "submodelElements";
	public static final String DATAELEMENTS = "dataElements";
	public static final String OPERATIONS = "operations";

	// The VAB model provider containing the submodelElements this SubmodelElementProvider is based on
	// Assumed to be a map that maps idShorts to the submodel elements
	private IModelProvider modelProvider;

	/**
	 * Constructor based on a model provider that contains the container property
	 */
	public AbstractSubmodelElementProvider(IModelProvider provider) {
		this.modelProvider = provider;
	}

	/**
	 * Getter for the contained model provider
	 */
	protected IModelProvider getProvider() {
		return modelProvider;
	}

	protected abstract Collection<Map<String, Object>> getElementsList();
	
	private Collection<Map<String, Object>> getDataElementList() {
		Collection<Map<String, Object>> all = getElementsList();
		// DataElements detection => has ("value" but not "ordered") or ("min" and "max") 
		return all.stream().filter(DataElement::isDataElement).collect(Collectors.toList());
	}

	private Collection<Map<String, Object>> getOperationList() {
		Collection<Map<String, Object>> all = getElementsList();
		return all.stream().filter(Operation::isOperation).collect(Collectors.toList());
	}

	/**
	 * Handles first-level access on submodel elements (e.g. /dataElements/)
	 */
	private Object handleQualifierGet(String qualifier, String path) {
		if (qualifier.equals(DATAELEMENTS)) {
			return getDataElementList();
		} else if (qualifier.equals(OPERATIONS)) {
			return getOperationList();
		} else if (qualifier.equals(ELEMENTS)) {
			// returns all elements
			return getElementsList();
		} else {
			// No other qualifier in a submodel element container can be directly accessed
			throw getUnknownPathException(path);
		}
	}

	protected abstract IModelProvider getElementProxy(String[] pathElements);

	@SuppressWarnings("unchecked")
	private Object handleDetailGet(String path) {
		// Build new proxy pointing at sub-property of a submodelelement and forward the
		// remaininig part of the path to an appropriate provider
		String[] pathElements = VABPathTools.splitPath(path);
		String qualifier = pathElements[0];
		String subPath = VABPathTools.buildPath(pathElements, 2);
		IModelProvider elementProxy = getElementProxy(pathElements);
		Map<String, Object> element = (Map<String, Object>) elementProxy.getModelPropertyValue("");

		if (pathElements.length == 2) {
			return element;
		}

		switch (qualifier) {
		case (ELEMENTS):
			if (DataElement.isDataElement(element)) {
				return new DataElementProvider(elementProxy).getModelPropertyValue(subPath);
			} else if (Operation.isOperation(element)) {
				return new OperationProvider(elementProxy).getModelPropertyValue(subPath);
			} else if (SubmodelElementCollection.isSubmodelElementCollection(element)) {
				return new SubmodelElementCollectionProvider(elementProxy).getModelPropertyValue(subPath);
			}
			// API for other types is not specified, yet => let modelprovider resolve request
			return elementProxy.getModelPropertyValue(subPath);
		case (DATAELEMENTS):
			return new DataElementProvider(elementProxy).getModelPropertyValue(subPath);
		case (OPERATIONS):
			return new OperationProvider(elementProxy).getModelPropertyValue(subPath);
		default:
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
		if (pathElements.length < 2 || (!qualifier.equals(DATAELEMENTS) && !qualifier.equals(ELEMENTS))) {
			// only possible to set values in a data elements, currently
			throw new MalformedRequestException("Invalid access");
		}

		IModelProvider elementProxy = getElementProxy(pathElements);
		Map<String, Object> element = (Map<String, Object>) elementProxy.getModelPropertyValue("");
		newValue = unwrapParameter(newValue);
		if (Operation.isOperation(element)) {
			throw new MalformedRequestException("Invalid access");
		} else if (DataElement.isDataElement(element)) {
			String subPath = VABPathTools.buildPath(pathElements, 2);
			new DataElementProvider(elementProxy).setModelPropertyValue(subPath, newValue);
		} else {
			// API for other elements not specified, yet => let modelprovider resolve request
			String subPath = VABPathTools.buildPath(pathElements, 2);
			elementProxy.setModelPropertyValue(subPath, newValue);
		}
	}

	protected abstract void createSubmodelElement(Object newEntity);

	@Override
	public void createValue(String path, Object newEntity) throws ProviderException {
		if (path.equals(DATAELEMENTS) || path.equals(OPERATIONS) || path.equals(ELEMENTS)) {
			createSubmodelElement(newEntity);
		} else {
			String[] pathElements = VABPathTools.splitPath(path);
			IModelProvider elementProxy = getElementProxy(pathElements);
			String subPath = VABPathTools.buildPath(pathElements, 2);
			// API for other elements not specified, yet => let modelprovider resolve request
			elementProxy.createValue(subPath, newEntity);
		}
	}

	@Override
	public void deleteValue(String path) throws ProviderException {
		String[] pathElements = VABPathTools.splitPath(path);
		if (pathElements.length < 2) {
			// only possible to directly delete elements with this deletion type
			throw new MalformedRequestException("Invalid access");
		}

		String qualifier = pathElements[0];
		String idShort = pathElements[1];
		if (qualifier.equals(DATAELEMENTS) || qualifier.equals(OPERATIONS) || qualifier.equals(ELEMENTS)) {
			// Delete a specific submodel element
			modelProvider.deleteValue(idShort);
		} else {
			throw new MalformedRequestException("Unknown access path " + path);
		}
	}

	@Override
	public void deleteValue(String path, Object obj) {
		String[] pathElements = VABPathTools.splitPath(path);
		String qualifier = pathElements[0];
		if (!qualifier.equals(ELEMENTS) && !qualifier.equals(DATAELEMENTS)) {
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
		String qualifier = pathElements[0];
		if (pathElements.length < 2 || (!qualifier.equals(OPERATIONS) && !qualifier.equals(ELEMENTS))) {
			// only possible to invoke operations
			throw new MalformedRequestException("Invalid access");
		}

		IModelProvider elementProxy = getElementProxy(pathElements);
		Map<String, Object> element = (Map<String, Object>) elementProxy.getModelPropertyValue("");

		if (!Operation.isOperation(element)) {
			// only possible to invoke operations
			throw new MalformedRequestException("Invalid access");
		}

		String subPath = VABPathTools.buildPath(pathElements, 2);
		return new OperationProvider(elementProxy).invokeOperation(subPath, parameters);
	}
}
