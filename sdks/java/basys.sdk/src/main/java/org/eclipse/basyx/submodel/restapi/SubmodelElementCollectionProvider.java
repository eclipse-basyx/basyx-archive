package org.eclipse.basyx.submodel.restapi;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Handles access to SubmodelElementCollections.
 *
 * @author espen
 */
public class SubmodelElementCollectionProvider extends MetaModelProvider {

	private IModelProvider proxy;
	private SubmodelElementListProvider elementProvider;

	public SubmodelElementCollectionProvider(IModelProvider proxy) {
		this.proxy = proxy;
		elementProvider = new SubmodelElementListProvider(new VABElementProxy(Property.VALUE, proxy));
	}

	@Override
	public Object getModelPropertyValue(String path) throws ProviderException {
		path = VABPathTools.stripSlashes(path);
		String[] pathElements = VABPathTools.splitPath(path);

		// Handle "/value" path
		if (pathElements[0].equals(Property.VALUE)) {
			if (pathElements.length == 1) {
				return proxy.getModelPropertyValue(Property.VALUE);
			} else {
				// Not possible to access list elements via provider
				String subPath = VABPathTools.buildPath(pathElements, 1);
				return elementProvider.getModelPropertyValue(subPath);
			}
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
		String[] pathElements = VABPathTools.splitPath(path);

		// Handle "/value" path
		if (pathElements[0].equals(Property.VALUE)) {
			if (pathElements.length == 1) {
				proxy.setModelPropertyValue(Property.VALUE, newValue);
			} else {
				String subPath = VABPathTools.buildPath(pathElements, 1);
				elementProvider.setModelPropertyValue(subPath, newValue);
			}
		} else {
			throw new MalformedRequestException("Invalid access path");
		}
	}

	@Override
	public void createValue(String path, Object newEntity) throws ProviderException {
		path = VABPathTools.stripSlashes(path);
		String[] pathElements = VABPathTools.splitPath(path);

		// Handle "/value" path
		if (pathElements[0].equals(Property.VALUE)) {
			if (pathElements.length == 1) {
				proxy.createValue(Property.VALUE, newEntity);
			} else {
				String subPath = VABPathTools.buildPath(pathElements, 1);
				elementProvider.createValue(subPath, newEntity);
			}
		} else {
			throw new MalformedRequestException("Invalid access path");
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

		// Handle "/value" path
		if (pathElements[0].equals(Property.VALUE)) {
			if (pathElements.length == 1) {
				proxy.deleteValue(Property.VALUE, obj);
			} else {
				String subPath = VABPathTools.buildPath(pathElements, 1);
				elementProvider.deleteValue(subPath, obj);
			}
		} else {
			throw new MalformedRequestException("Invalid access path");
		}
	}

	@Override
	public Object invokeOperation(String path, Object... parameter) throws ProviderException {
		path = VABPathTools.stripSlashes(path);
		String[] pathElements = VABPathTools.splitPath(path);

		// Handle "/value" path
		if (pathElements[0].equals(Property.VALUE)) {
			String subPath = VABPathTools.buildPath(pathElements, 1);
			return elementProvider.invokeOperation(subPath, parameter);
		} else {
			throw new MalformedRequestException("Invalid access path");
		}
	}

}
