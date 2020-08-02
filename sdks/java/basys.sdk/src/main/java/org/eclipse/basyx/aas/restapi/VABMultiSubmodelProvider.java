package org.eclipse.basyx.aas.restapi;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

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
 * the AAS's submodel
 * <br /><br />
 * - createValue <br />
 * /aas/submodels Adds a new SubModel to an existing Asset Administration Shell
 * <br /><br />
 * /aas/submodels/{subModelId}/properties Adds a new property to the AAS's
 * submodel <br />
 * /aas/submodels/{subModelId}/operations Adds a new operation to the AAS's
 * submodel <br />
 * /aas/submodels/{subModelId}/events Adds a new event to the AAS's submodel
 * <br /><br />
 * - invokeOperation<br />
 * /aas/submodels/{subModelId}/operations/{operationId} Invokes a specific
 * operation from the AAS' submodel with a list of input parameters
 * <br /><br />
 * - deleteValue<br />
 * /aas/submodels/{subModelId} Deletes a specific SubModel from a specific Asset
 * Administration Shell <br />
 * /aas/submodels/{subModelId}/properties/{propertyId} Deletes a specific
 * Property from the AAS's SubModel<br />
 * /aas/submodels/{subModelId}/operations/{operationId} Deletes a specific
 * Operation from the AAS's SubModel<br />
 * /aas/submodels/{subModelId}/events/{eventId} Deletes a specific event from
 * the AAS's submodel
 * <br /><br />
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
	protected AASModelProvider aas_provider = null;

	/**
	 * Store submodel providers
	 */
	protected Map<String, SubModelProvider> submodel_providers = new HashMap<>();

	/**
	 * Constructor
	 */
	public VABMultiSubmodelProvider() {
		this(new AASModelProvider(new AssetAdministrationShell()));
	}

	/**
	 * Constructor that accepts an AAS
	 */
	public VABMultiSubmodelProvider(AASModelProvider contentProvider) {
		// Store content provider
		setAssetAdministrationShell(contentProvider);
	}

	/**
	 * Constructor that accepts Submodel
	 */
	public VABMultiSubmodelProvider(String smID, SubModelProvider contentProvider) {
		this();
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
	public void setAssetAdministrationShell(AASModelProvider modelContentProvider) {
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
	@SuppressWarnings("unchecked")
	public void addSubmodel(String elementId, SubModelProvider modelContentProvider) {
		// Add model provider
		submodel_providers.put(elementId, modelContentProvider);

		SubModel sm = SubModel.createAsFacade((Map<String, Object>) modelContentProvider.getModelPropertyValue("/"));

		// Adds a new submodel to the registered AAS
		aas_provider.createValue("/submodels", sm);
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
	public Object getModelPropertyValue(String path) throws ProviderException {
		VABPathTools.checkPathForNull(path);
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
					Collection<Object> submodels = new HashSet<>();
					for (IModelProvider submodel : submodel_providers.values()) {
						submodels.add(submodel.getModelPropertyValue(""));
					}
					return submodels;
				} else {
					IModelProvider hashmapProvider = submodel_providers.get(pathElements[2]);

					if(hashmapProvider == null) {
						throw new ResourceNotFoundException("Submodel with id " + pathElements[2] + " does not exist");
					}
					
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
	}

	/**
	 * Change a model property value
	 */
	@Override
	public void setModelPropertyValue(String path, Object newValue) throws ProviderException {
		VABPathTools.checkPathForNull(path);
		// Split path
		String[] pathElements = VABPathTools.splitPath(path);
		String propertyPath = VABPathTools.buildPath(pathElements, 3);
		// - Ignore first 2 elements, as it is "/aas/submodels" --> 'aas','submodels'
		submodel_providers.get(pathElements[2]).setModelPropertyValue(propertyPath, newValue);
	}

	@Override
	public void createValue(String path, Object newValue) throws ProviderException {
		VABPathTools.checkPathForNull(path);
		String[] pathElements = VABPathTools.splitPath(path);
		if (pathElements.length >= 1 && pathElements[0].equals("aas")) {
			if (pathElements.length == 1) {
				createAssetAdministrationShell(newValue);
			} else if (pathElements[1].equals(AssetAdministrationShell.SUBMODELS)) {
				if (pathElements.length == 2) {
					createSubModel(newValue);
				} else {
					String propertyPath = VABPathTools.buildPath(pathElements, 3);
					createSubModelProperty(pathElements[2], propertyPath, newValue);
				}
			}
		}
	}

	private void createSubModelProperty(String smId, String propertyPath, Object newProperty) throws ProviderException {
		SubModelProvider subModelProvider = submodel_providers.get(smId);
		subModelProvider.createValue(propertyPath, newProperty);
	}

	@SuppressWarnings("unchecked")
	private void createAssetAdministrationShell(Object newAAS) {
		Map<String, Object> aas = (Map<String, Object>) newAAS;
		aas_provider = new AASModelProvider(AssetAdministrationShell.createAsFacade(aas));
	}

	@SuppressWarnings("unchecked")
	private void createSubModel(Object newSM) throws ProviderException {
		// Adds a new submodel to the registered AAS
		SubModel sm = SubModel.createAsFacade((Map<String, Object>) newSM);

		addSubmodel(sm.getIdShort(), new SubModelProvider(sm));
	}


	@SuppressWarnings("unchecked")
	@Override
	public void deleteValue(String path) throws ProviderException {
		VABPathTools.checkPathForNull(path);
		String[] pathElements = VABPathTools.splitPath(path);
		String propertyPath = VABPathTools.buildPath(pathElements, 3);
		// - Ignore first 2 elements, as it is "/aas/submodels" --> 'aas','submodels'
		if (pathElements.length == 3) {
			// Delete Submodel from registered AAS
			String smIdShort = pathElements[2];
			if (!submodel_providers.containsKey(smIdShort)) {
				return;
			}

			// Delete submodel reference from aas
			// TODO: This is a hack until the API is further clarified
			SubModel sm = SubModel.createAsFacade((Map<String, Object>) submodel_providers.get(smIdShort).getModelPropertyValue("/"));
			aas_provider.deleteValue("aas/submodels/" + sm.getIdentification().getId());

			// Remove submodel provider
			submodel_providers.remove(smIdShort);
		} else if (propertyPath.length() > 0) {
			submodel_providers.get(pathElements[2]).deleteValue(propertyPath);
		}
	}

	@Override
	public void deleteValue(String path, Object obj) throws ProviderException {
		VABPathTools.checkPathForNull(path);
		String[] pathElements = VABPathTools.splitPath(path);
		String propertyPath = VABPathTools.buildPath(pathElements, 3);
		// - Ignore first 2 elements, as it is "/aas/submodels" --> 'aas','submodels'
		submodel_providers.get(pathElements[2]).deleteValue(propertyPath, obj);
	}

	@Override
	public Object invokeOperation(String path, Object... parameter) throws ProviderException {
		VABPathTools.checkPathForNull(path);
		String[] pathElements = VABPathTools.splitPath(path);
		String operationPath = VABPathTools.buildPath(pathElements, 3);
		// - Ignore first 2 elements, as it is "/aas/submodels" --> 'aas','submodels'
		// - Invoke provider and return result
		return submodel_providers.get(pathElements[2]).invokeOperation(operationPath, parameter);
	}
}
