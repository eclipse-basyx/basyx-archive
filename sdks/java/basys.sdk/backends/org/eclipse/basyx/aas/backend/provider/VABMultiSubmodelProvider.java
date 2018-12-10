package org.eclipse.basyx.aas.backend.provider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.tools.VABPathTools;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;

/**
 * Provider class that implements the AssetAdministrationShellServices <br />
 * This provider supports operations on multiple sub models that are selected by
 * path<br />
 * <br />
 * Supported API:<br />
 * - getModelPropertyValue<br />
 * /aas Returns the Asset Administration Shell<br />
 * /aas/submodels Retrieves all SubModels from the current Asset Administration
 * Shell<br />
 * /aas/submodels/{subModelId} Retrieves a specific SubModel from a specific
 * Asset Administration Shell<br />
 * /aas/submodels/{subModelId}/properties Retrieves all Properties from the
 * current SubModel<br />
 * /aas/submodels/{subModelId}/operations Retrieves all Operations from the
 * current SubModel<br />
 * /aas/submodels/{subModelId}/events Retrieves all Events from the current
 * SubModel<br />
 * /aas/submodels/{subModelId}/properties/{propertyId} Retrieves a specific
 * property from the AAS's SubModel<br />
 * /aas/submodels/{subModelId}/operations/{operationId} Retrieves a specific
 * Operation from the AAS's SubModel<br />
 * /aas/submodels/{subModelId}/events/{eventId} Retrieves a specific event from
 * the AAS's submodel<br />
 * <br />
 * - createValue <br />
 * /aas/submodels Adds a new SubModel to an existing Asset Administration Shell
 * <br />
 * /aas/submodels/{subModelId}/properties Adds a new property to the AAS's
 * submodel <br />
 * /aas/submodels/{subModelId}/operations Adds a new operation to the AAS's
 * submodel <br />
 * /aas/submodels/{subModelId}/events Adds a new event to the AAS's submodel
 * 
 * - invokeOperation<br />
 * /aas/submodels/{subModelId}/operations/{operationId} Invokes a specific
 * operation from the AAS' submodel with a list of input parameters
 * 
 * - deleteValue<br />
 * /aas/submodels/{subModelId} Deletes a specific SubModel from a specific Asset
 * Administration Shell <br />
 * /aas/submodels/{subModelId}/properties/{propertyId} Deletes a specific
 * Property from the AAS's SubModel<br />
 * /aas/submodels/{subModelId}/operations/{operationId} Deletes a specific
 * Operation from the AAS's SubModel<br />
 * /aas/submodels/{subModelId}/events/{eventId} Deletes a specific event from
 * the AAS's submodel
 * 
 * - setModelPropertyValue<br />
 * /aas/submodels/{subModelId}/properties/{propertyId} Sets the value of the
 * AAS's SubModel's Property
 * 
 * 
 * @author kuhn
 *
 */
public class VABMultiSubmodelProvider<T extends VABHashmapProvider> implements IModelProvider {

	/**
	 * Store aas providers
	 */
	protected T aas_provider = null;

	/**
	 * Store submodel providers
	 */
	protected Map<String, T> submodel_providers = new HashMap<>();

	/**
	 * Constructor
	 */
	public VABMultiSubmodelProvider() {
		// Do nothing
	}

	/**
	 * Set an AAS for this provider
	 * 
	 * @param elementId
	 *            Element ID
	 * @param modelContentProvider
	 *            Model content provider
	 */
	public void setAssetAdministrationShell(T modelContentProvider) {
		// Add model provider
		aas_provider = modelContentProvider;
	}

	/**
	 * Add a Submodel to the provider
	 * 
	 * @param elementId
	 *            Element ID
	 * @param modelContentProvider
	 *            Model content provider
	 */
	public void addSubmodel(String elementId, T modelContentProvider) {
		// Add model provider
		submodel_providers.put(elementId, modelContentProvider);
	}

	/**
	 * Remove a provider
	 * 
	 * @param elementId
	 *            Element ID
	 */
	public void removeProvider(String elementId) {
		// Remove model provider
		submodel_providers.remove(elementId);
	}

	/**
	 * Get the value of an element
	 */
	@Override
	public Object getModelPropertyValue(String path) {
		// Not (yet) implemented get requests
		// - Return the AAS - not implemented
		// if (path.toLowerCase().equals("/aas") || path.toLowerCase().equals("/aas/"))
		// throw new VABProviderNotImplementedException();
		// - Return all sub models of this provider
		// if (path.toLowerCase().equals("/aas/submodels") ||
		// path.toLowerCase().equals("/aas/submodels/")) throw new
		// VABProviderNotImplementedException();

		// Split path
		// - Ignore first 3 elements, as it is "/aas/submodels" --> '', 'aas',
		// 'submodels'
		// - Get Provider for the requested submodel or aas

		if (path.toLowerCase().equals("/aas")) {

			// Retrun aas
			return aas_provider.getElements();

		} else if (path.toLowerCase().equals("/aas/submodels")) {

			// Make a list and return all submodels
			HashSet<Map<String, Object>> submodels = new HashSet<Map<String, Object>>();
			submodel_providers.values().forEach((T v) -> submodels.add(v.getElements()));

			return submodels;
		} else {
			String[] pathElements = VABPathTools.splitPath(path);

			T hashmapProvider = submodel_providers.get(pathElements[3]);

			// Retrieve submodel
			if (pathElements.length == 4) {
				return hashmapProvider.getElements();
			}

			// - Retrieve property value
			return hashmapProvider.getModelPropertyValue(VABPathTools.buildPath(pathElements, 4));
		}

	}

	/**
	 * Change a model property value
	 */
	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		// Split path
		String[] pathElements = VABPathTools.splitPath(path);
		// - Ignore first 3 elements, as it is "/aas/submodels" --> '', 'aas',
		// 'submodels'
		// - Invoke provider and return result
		submodel_providers.get(pathElements[3]).setModelPropertyValue(VABPathTools.buildPath(pathElements, 4),
				newValue);
	}

	@Override
	public void createValue(String path, Object newValue) throws Exception {
		// Split path
		String[] pathElements = VABPathTools.splitPath(path);
		// - Ignore first 3 elements, as it is "/aas/submodels" --> '', 'aas',
		// 'submodels'
		// - Invoke provider and return result
		submodel_providers.get(pathElements[3]).createValue(VABPathTools.buildPath(pathElements, 4), newValue);
	}

	@Override
	public void deleteValue(String path) throws Exception {
		// Split path
		String[] pathElements = VABPathTools.splitPath(path);
		// - Ignore first 3 elements, as it is "/aas/submodels" --> '', 'aas',
		// 'submodels'
		// - Invoke provider and return result
		submodel_providers.get(pathElements[3]).deleteValue(VABPathTools.buildPath(pathElements, 4));
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		// Split path
		String[] pathElements = VABPathTools.splitPath(path);
		// - Ignore first 3 elements, as it is "/aas/submodels" --> '', 'aas',
		// 'submodels'
		// - Invoke provider and return result
		submodel_providers.get(pathElements[3]).deleteValue(VABPathTools.buildPath(pathElements, 4), obj);
	}

	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {
		// Split path
		String[] pathElements = VABPathTools.splitPath(path);
		// - Ignore first 3 elements, as it is "/aas/submodels" --> '', 'aas',
		// 'submodels'
		// - Invoke provider and return result
		return submodel_providers.get(pathElements[3]).invokeOperation(VABPathTools.buildPath(pathElements, 4),
				parameter);
	}

}
