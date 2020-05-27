package org.eclipse.basyx.submodel.restapi;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.map.VABMapProvider;

/**
 * Handles access to SubmodelElementCollections.
 *
 * @author espen
 */
public class SubmodelElementCollectionProvider extends MetaModelProvider {

	private IModelProvider proxy;

	public SubmodelElementCollectionProvider(IModelProvider proxy) {
		this.proxy = proxy;
	}

	/**
	 * Single list elements cannot be directly accessed => resolve it and return a provider
	 * for the single element
	 */
	@SuppressWarnings("unchecked")
	protected IModelProvider getElementProvider(String idShort) {
		// Not possible to access single list elements via provider => copy it and wrap it into the
		// correspondent ElementProvider

		// Resolve the list and return a wrapper provider for the queried element
		Collection<Map<String, Object>> list = (Collection<Map<String, Object>>) proxy
				.getModelPropertyValue(Property.VALUE);

		// Wrap the resolved property with idShort into a SubmodelElementProvider and return that provider
		Map<String, Object> element = findElementInList(idShort, list);
		IModelProvider defaultProvider = new VABMapProvider(element);
		return SubmodelElementProvider.getElementProvider(element, defaultProvider);
	}

	private Map<String, Object> findElementInList(String idShort, Collection<Map<String, Object>> list) {
		for (Map<String, Object> elem : list) {
			if (idShort.equals(Referable.createAsFacade(elem, KeyElements.SUBMODELELEMENT).getIdShort())) {
				return elem;
			}
		}
		throw new ResourceNotFoundException("The element \"" + idShort + "\" could not be found");
	}

	@Override
	public Object getModelPropertyValue(String path) throws ProviderException {
		path = VABPathTools.stripSlashes(path);
		String[] pathElements = VABPathTools.splitPath(path);

		if (path.isEmpty() || path.equals(Property.VALUE)) {
			return proxy.getModelPropertyValue(path);
		} else {
			// Directly access an element inside of the collection
			String idShort = pathElements[0];
			String subPath = VABPathTools.buildPath(pathElements, 1);
			return getElementProvider(idShort).getModelPropertyValue(subPath);
		}
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws ProviderException {
		path = VABPathTools.stripSlashes(path);
		String[] pathElements = VABPathTools.splitPath(path);

		if (path.isEmpty() || path.equals(Property.VALUE)) {
			proxy.setModelPropertyValue(path, newValue);
		} else {
			// Directly access an element inside of the collection
			String idShort = pathElements[0];
			String subPath = VABPathTools.buildPath(pathElements, 1);
			getElementProvider(idShort).setModelPropertyValue(subPath, newValue);
		}
	}

	@Override
	public void createValue(String path, Object newEntity) throws ProviderException {
		path = VABPathTools.stripSlashes(path);
		String[] pathElements = VABPathTools.splitPath(path);

		if (path.isEmpty() || path.equals(Property.VALUE)) {
			proxy.createValue(path, newEntity);
		} else {
			// Directly access an element inside of the collection
			String idShort = pathElements[0];
			String subPath = VABPathTools.buildPath(pathElements, 1);
			getElementProvider(idShort).createValue(subPath, newEntity);
		}
	}

	@Override
	public void deleteValue(String path) throws ProviderException {
		throw new MalformedRequestException("Invalid access path");
	}

	@Override
	public void deleteValue(String path, Object obj) throws ProviderException {
		path = VABPathTools.stripSlashes(path);
		String[] pathElements = VABPathTools.splitPath(path);

		if (path.isEmpty() || path.equals(Property.VALUE)) {
			proxy.deleteValue(path, obj);
		} else {
			// Directly access an element inside of the collection
			String idShort = pathElements[0];
			String subPath = VABPathTools.buildPath(pathElements, 1);
			getElementProvider(idShort).deleteValue(subPath, obj);
		}
	}

	@Override
	public Object invokeOperation(String path, Object... parameter) throws ProviderException {
		path = VABPathTools.stripSlashes(path);
		String[] pathElements = VABPathTools.splitPath(path);

		if (path.isEmpty() || path.equals(Property.VALUE)) {
			throw new MalformedRequestException("Invalid access path");
		} else {
			// Directly access an element inside of the collection
			String idShort = pathElements[0];
			String subPath = VABPathTools.buildPath(pathElements, 1);
			return getElementProvider(idShort).invokeOperation(subPath, parameter);
		}
	}
}
