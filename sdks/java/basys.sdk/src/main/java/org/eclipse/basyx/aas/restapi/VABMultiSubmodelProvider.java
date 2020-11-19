package org.eclipse.basyx.aas.restapi;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.api.IConnectorProvider;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;

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
	 * Store aasId
	 */
	protected IIdentifier aasId = null;

	/**
	 * Store submodel providers
	 */
	protected Map<String, SubModelProvider> submodel_providers = new HashMap<>();
	
	/**
	 * Store AAS Registry
	 */
	protected IAASRegistryService registry = null;
	
	/**
	 * Store HTTP Connector
	 */
	protected IConnectorProvider connectorProvider = null;

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
	 * Constructor that accepts a registry and a connection provider
	 * @param registry
	 * @param provider
	 */
	public VABMultiSubmodelProvider(IAASRegistryService registry, IConnectorProvider provider) {
		this();
		this.registry = registry;
		this.connectorProvider = provider;
	}
	
	/**
	 * Constructor that accepts a aas provider, a registry and a connection provider
	 * @param contentProvider
	 * @param registry
	 * @param provider
	 */
	public VABMultiSubmodelProvider(AASModelProvider contentProvider, IAASRegistryService registry, HTTPConnectorProvider provider) {
		this(contentProvider);
		this.registry = registry;
		this.connectorProvider = provider;
	}

	/**
	 * Set an AAS for this provider
	 * 
	 * @param elementId
	 *            Element ID
	 * @param modelContentProvider
	 *            Model content provider
	 */
	@SuppressWarnings("unchecked")
	public void setAssetAdministrationShell(AASModelProvider modelContentProvider) {
		// Add model provider
		aas_provider = modelContentProvider;
		aasId = AssetAdministrationShell.createAsFacade((Map<String, Object>) modelContentProvider.getModelPropertyValue("")).getIdentification();
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
	@Deprecated
	public void addSubmodel(String elementId, SubModelProvider modelContentProvider) {
		// Add model provider
		submodel_providers.put(elementId, modelContentProvider);

		SubModel sm = SubModel.createAsFacade((Map<String, Object>) modelContentProvider.getModelPropertyValue("/"));

		// Adds a new submodel to the registered AAS
		aas_provider.createValue("/submodels", sm);
	}

	@SuppressWarnings("unchecked")
	public void addSubmodel(SubModelProvider modelContentProvider) {
		SubModel sm = SubModel.createAsFacade((Map<String, Object>) modelContentProvider.getModelPropertyValue("/"));
		submodel_providers.put(sm.getIdShort(), modelContentProvider);

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
		path = VABPathTools.stripSlashes(path);
		String[] pathElements = VABPathTools.splitPath(path);
		if (pathElements.length > 0 && pathElements[0].equals("aas")) {
			if (pathElements.length == 1) {
				return aas_provider.getModelPropertyValue("");
			}
			if (pathElements[1].equals(AssetAdministrationShell.SUBMODELS)) {
				if (pathElements.length == 2) {
					return retrieveSubmodels();
				} else {
					IModelProvider provider = submodel_providers.get(pathElements[2]);

					if (provider == null) {
						// Get a model provider for the submodel in the registry
						provider = getModelProvider(pathElements[2]);
					}
					
					// - Retrieve submodel or property value
					return provider.getModelPropertyValue(VABPathTools.buildPath(pathElements, 4));
				}
			} else {
				// Handle access to AAS
				return aas_provider.getModelPropertyValue(VABPathTools.buildPath(pathElements, 1));
			}
		} else {
			return new MalformedRequestException("The request " + path + " is not allowed for this endpoint");
		}
	}

	/**
	 * Retrieves all submodels of the AAS. If there's a registry, remote Submodels
	 * will also be retrieved.
	 * 
	 * @return
	 * @throws ProviderException
	 */
	@SuppressWarnings("unchecked")
	private Object retrieveSubmodels() throws ProviderException {
		// Make a list and return all local submodels
		Collection<SubModel> submodels = new HashSet<>();
		for (IModelProvider submodel : submodel_providers.values()) {
			submodels.add(SubModel.createAsFacade((Map<String, Object>) submodel.getModelPropertyValue("")));
		}

		// Check for remote submodels
		if (registry != null) {
			AASDescriptor desc = registry.lookupAAS(aasId);
			
			// Get the address of the AAS e.g. http://localhost:8080
			// This address should be equal to the address of this server
			String aasEndpoint = desc.getFirstEndpoint();
			String aasServerURL = getServerURL(aasEndpoint);
			
			List<String> localIds = submodels.stream().map(sm -> sm.getIdentification().getId()).collect(Collectors.toList());
			List<IIdentifier> missingIds = desc.getSubModelDescriptors().stream().map(d -> d.getIdentifier()).
					filter(id -> !localIds.contains(id.getId())).collect(Collectors.toList());
			
			if(!missingIds.isEmpty()) {
				List<String> missingEndpoints = missingIds.stream().map(id -> desc.getSubModelDescriptorFromIdentifierId(id.getId()))
						.map(smDesc -> smDesc.getFirstEndpoint()).collect(Collectors.toList());
				
				// Check if any of the missing Submodels have the same address as the AAS.
				// This would mean, that the Submodel should be present on the same
				// server of the AAS but is not
				
				// If this error would not be caught here an endless loop would develop
				// as the registry would be asked for this Submodel and then it would be requested
				// from this server again, which would ask the registry about it again
				
				// Such a situation might originate from a deleted but not unregistered Submodel
				// or from a manually registered but never pushed Submodel
				for(String missingEndpoint: missingEndpoints) {
					if(getServerURL(missingEndpoint).equals(aasServerURL)) {
						throw new ResourceNotFoundException("The Submodel at Endpoint '" + missingEndpoint + 
								"' does not exist on this server. It seems to be registered but not actually present.");
					}
				}
				
				List<SubModel> remoteSms = missingEndpoints.stream().map(endpoint -> connectorProvider.getConnector(endpoint)).
						map(p -> (Map<String, Object>) p.getModelPropertyValue("")).map(m -> SubModel.createAsFacade(m)).collect(Collectors.toList());
				submodels.addAll(remoteSms);
			}
		}

		return submodels;
	}

	/**
	 * Change a model property value
	 */
	@Override
	public void setModelPropertyValue(String path, Object newValue) throws ProviderException {
 		VABPathTools.checkPathForNull(path);
		path = VABPathTools.stripSlashes(path);
		// Split path
		String[] pathElements = VABPathTools.splitPath(path);
		String propertyPath = VABPathTools.buildPath(pathElements, 3);
		// - Ignore first 2 elements, as it is "/aas/submodels" --> 'aas','submodels'
		
		if (path.equals("aas")) {
			createAssetAdministrationShell(newValue);
		} else if (!path.startsWith("aas/submodels")) {
			throw new MalformedRequestException("Access to MultiSubmodelProvider always has to start with \"aas/submodels\", was " + path);
		}

		IModelProvider provider;
		try {
			if (isSubmodelLocal(pathElements[2])) {
				provider = submodel_providers.get(pathElements[2]);
			} else {
				// Get a model provider for the submodel in the registry
				provider = getModelProvider(pathElements[2]);
			}
			provider.setModelPropertyValue(propertyPath, newValue);
		} catch (ResourceNotFoundException e) {
			createSubModel(newValue);
		}
	}

	@Override
	public void createValue(String path, Object newValue) throws ProviderException {
		throw new MalformedRequestException("Create is not supported by VABMultiSubmodelProvider. Path was: " + path);
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

		addSubmodel(new SubModelProvider(sm));
	}


	@SuppressWarnings("unchecked")
	@Override
	public void deleteValue(String path) throws ProviderException {
		VABPathTools.checkPathForNull(path);
		path = VABPathTools.stripSlashes(path);
		String[] pathElements = VABPathTools.splitPath(path);
		String propertyPath = VABPathTools.buildPath(pathElements, 3);
		// - Ignore first 2 elements, as it is "/aas/submodels" --> 'aas','submodels'
		if (pathElements.length == 3) {
			// Delete Submodel from registered AAS
			String smIdShort = pathElements[2];
			if (!isSubmodelLocal(smIdShort)) {
				return;	
			}

			// Delete submodel reference from aas
			// TODO: This is a hack until the API is further clarified
			SubModel sm = SubModel.createAsFacade((Map<String, Object>) submodel_providers.get(smIdShort).getModelPropertyValue("/"));
			aas_provider.deleteValue("aas/submodels/" + sm.getIdentification().getId());

			// Remove submodel provider
			submodel_providers.remove(smIdShort);
		} else if (propertyPath.length() > 0) {
			IModelProvider provider;
			if (isSubmodelLocal(pathElements[2])) {
				provider = submodel_providers.get(pathElements[2]);
			} else {
				// Get a model provider for the submodel in the registry
				provider = getModelProvider(pathElements[2]);
			}

			provider.deleteValue(propertyPath);
		}
	}

	@Override
	public void deleteValue(String path, Object obj) throws ProviderException {
		throw new MalformedRequestException("DeleteValue with a parameter is not supported. Path was: " + path);
	}

	@Override
	public Object invokeOperation(String path, Object... parameter) throws ProviderException {
		VABPathTools.checkPathForNull(path);
		path = VABPathTools.stripSlashes(path);
		String[] pathElements = VABPathTools.splitPath(path);
		String operationPath = VABPathTools.buildPath(pathElements, 3);
		// - Ignore first 2 elements, as it is "/aas/submodels" --> 'aas','submodels'
		// - Invoke provider and return result
		IModelProvider provider;
		if (isSubmodelLocal(pathElements[2])) {
			provider = submodel_providers.get(pathElements[2]);
		} else {
			// Get a model provider for the submodel in the registry
			provider = getModelProvider(pathElements[2]);
		}

		return provider.invokeOperation(operationPath, parameter);
	}
	
	/**
	 * Check whether the given submodel exists in submodel provider
	 * @param key to search the submodel
	 * @return boolean true/false
	 */
	private boolean isSubmodelLocal(String submodelId) {
		return submodel_providers.containsKey(submodelId);
	}
	
	/**
	 * Check whether a registry exists
	 * @return boolean true/false
	 */
	private boolean doesRegistryExist() {
		return this.registry != null;
	}
	
	/**
	 * Get submodel descriptor from the registry
	 * @param submodelId to search the submodel
	 * @return a specifi submodel descriptor
	 */
	private SubmodelDescriptor getSubmodelDescriptorFromRegistry(String submodelIdShort) {
		AASDescriptor aasDescriptor = registry.lookupAAS(aasId);
		SubmodelDescriptor desc = aasDescriptor.getSubmodelDescriptorFromIdShort(submodelIdShort);
		if(desc == null) {
			throw new ResourceNotFoundException("Could not resolve Submodel with idShort " + submodelIdShort + " for AAS " + aasId);
		}
		return desc;
	}
	
	/**
	 * Get a model provider from a submodel descriptor
	 * @param submodelDescriptor
	 * @return a model provider
	 */
	private IModelProvider getModelProvider(SubmodelDescriptor submodelDescriptor) {
		String endpoint = submodelDescriptor.getFirstEndpoint();

		// Remove "/submodel" since it will be readded later
		endpoint = endpoint.substring(0, endpoint.length() - SubModelProvider.SUBMODEL.length() - 1);

		return connectorProvider.getConnector(endpoint);
	}
	
	/**
	 * Get a model provider from a submodel id
	 * @param submodelId to select a specific submodel
	 * @throws ResourceNotFoundException if no registry is found
	 * @return a model provider
	 */
	private IModelProvider getModelProvider(String submodelId) {
		if (!doesRegistryExist()) {
			throw new ResourceNotFoundException("Submodel with id " + submodelId + " cannot be resolved locally, but no registry is passed");	
		}
		
		SubmodelDescriptor submodelDescriptor = getSubmodelDescriptorFromRegistry(submodelId);
		return getModelProvider(submodelDescriptor);
	}
	
	/**
	 * Gets the server URL of a given endpoint.
	 * e.g. http://localhost:1234/x/y/z/aas/submodels/Sm1IdShort would return
	 * http://localhost:1234/x/y/z
	 * 
	 * @param endpoint
	 * @return the server URL part of the given endpoint
	 */
	public static String getServerURL(String endpoint) {
		int endServerURL = endpoint.indexOf("/aas");
		// if indexOf returned -1 ("/aas" not present in String)
		// return the whole given path
		if(endServerURL < 0) {
			return endpoint;
		}
		return endpoint.substring(0, endServerURL);
	}
}
