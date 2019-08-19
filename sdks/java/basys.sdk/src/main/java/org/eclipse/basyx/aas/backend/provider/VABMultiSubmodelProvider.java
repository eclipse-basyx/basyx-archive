package org.eclipse.basyx.aas.backend.provider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.hashmap.VABModelMap;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.tools.VABPathTools;

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
 * @author kuhn, pschorn
 *
 */
public class VABMultiSubmodelProvider implements IModelProvider {

	/**
	 * Store aas providers
	 */
	protected VirtualPathModelProvider aas_provider = null;

	/**
	 * Store submodel providers
	 */
	protected Map<String, VirtualPathModelProvider> submodel_providers = new HashMap<>();

	/**
	 * Constructor
	 */
	public VABMultiSubmodelProvider() {
		// Do nothing
	}

	/**
	 * Constructor that accepts an AAS
	 */
	public VABMultiSubmodelProvider(VirtualPathModelProvider contentProvider) {
		// Store content provider
		setAssetAdministrationShell(contentProvider);
	}

	/**
	 * Constructor that accepts Submodel
	 */
	public VABMultiSubmodelProvider(String smID, VirtualPathModelProvider contentProvider) {
		// Store content provider
		addSubmodel(smID, contentProvider);
	}

	/**
	 * Set an AAS for this provider
	 * 
	 * @param elementId
	 *            Element ID
	 * @param modelContentProvider
	 *            Model content provider
	 */
	public void setAssetAdministrationShell(VirtualPathModelProvider modelContentProvider) {
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
	public void addSubmodel(String elementId, VirtualPathModelProvider modelContentProvider) {
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
	public Object getModelPropertyValue(String path) throws Exception {

		try {
			String[] pathElements = VABPathTools.splitPath(path);
			if (pathElements.length == 0) { // e.g. "/"
				return null;
			} else if (pathElements[0].equals("aas")) {
				if (pathElements.length == 1) {
					return aas_provider.getModelPropertyValue("");
				}
				if (pathElements[1].equals(AssetAdministrationShell.SUBMODELS)) {
					if (pathElements.length == 2) {
						// Make a list and return all submodels
						HashSet<Object> submodels = new HashSet<Object>();
						for (VirtualPathModelProvider submodel : submodel_providers.values()) {
							submodels.add(submodel.getModelPropertyValue(""));
						}
						return submodels;
					} else {
						VirtualPathModelProvider hashmapProvider = submodel_providers.get(pathElements[2]);

						// - Retrieve submodel or property value
						return hashmapProvider.getModelPropertyValue(VABPathTools.buildPath(pathElements, 3));
					}
				} else {
					// Handle access to AAS
					return aas_provider.getModelPropertyValue(VABPathTools.buildPath(pathElements, 1));
				}
			} else {
				return null;
			}
		} catch (NullPointerException e) {
			throw e;

		}
	}

	/**
	 * Change a model property value
	 */
	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		// Split path
		String[] pathElements = VABPathTools.splitPath(path);
		String propertyPath = VABPathTools.buildPath(pathElements, 3);
		// - Ignore first 2 elements, as it is "/aas/submodels" --> 'aas','submodels'
		submodel_providers.get(pathElements[2]).setModelPropertyValue(propertyPath, newValue);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void createValue(String path, Object newValue) throws Exception {
		String[] pathElements = VABPathTools.splitPath(path);
		String propertyPath = VABPathTools.buildPath(pathElements, 3);
		if (path.equals("/aas/submodels")) {
			// Adds a new submodel to to the registered AAS
			VABModelMap<Object> sm = (VABModelMap<Object>) newValue;
			VirtualPathModelProvider smProvider = new VirtualPathModelProvider(sm);
			submodel_providers.put((String) sm.getPath(Referable.IDSHORT), (VirtualPathModelProvider) smProvider);
			aas_provider.createValue("/" + AssetAdministrationShell.SUBMODEL, sm);
		} else {
			// - Ignore first 2 elements, as it is "/aas/submodels" --> 'aas','submodels'
			submodel_providers.get(pathElements[2]).createValue(propertyPath, newValue);
		}
	}

	@Override
	public void deleteValue(String path) throws Exception {
		String[] pathElements = VABPathTools.splitPath(path);
		String propertyPath = VABPathTools.buildPath(pathElements, 3);
		// - Ignore first 2 elements, as it is "/aas/submodels" --> 'aas','submodels'
		if (pathElements.length == 3) {
			// Delete Submodel from registered AAS
			String smId = pathElements[2];
			aas_provider.deleteValue("/" + AssetAdministrationShell.SUBMODEL + "/" + smId);
			submodel_providers.remove(smId);
		}

		else if (propertyPath != "")
			submodel_providers.get(pathElements[2]).deleteValue(propertyPath);

	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		String[] pathElements = VABPathTools.splitPath(path);
		String propertyPath = VABPathTools.buildPath(pathElements, 3);
		// - Ignore first 2 elements, as it is "/aas/submodels" --> 'aas','submodels'
		submodel_providers.get(pathElements[2]).deleteValue(propertyPath, obj);
	}

	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {
		String[] pathElements = VABPathTools.splitPath(path);
		String operationPath = VABPathTools.buildPath(pathElements, 3);
		// - Ignore first 2 elements, as it is "/aas/submodels" --> 'aas','submodels'
		// - Invoke provider and return result
		return submodel_providers.get(pathElements[2]).invokeOperation(operationPath, parameter);
	}
}
