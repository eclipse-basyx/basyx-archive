package org.eclipse.basyx.aas.backend.modelprovider;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.aas.impl.tools.BaSysID;
import org.eclipse.basyx.sdk.provider.exception.VABProviderNotImplementedException;
import org.eclipse.basyx.vab.tools.VABPathTools;
import org.eclipse.basyx.aas.api.exception.ReadOnlyException;
import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.json.JSONException;
import org.json.JSONObject;




/**
 * Provider class that implements the AssetAdministrationShellServices
 * This provider supports operations on multiple sub models that are selected by path
 * 
 * Supported API:
 * - getModelPropertyValue
 *       /aas                                                  Returns the Asset Administration Shell
 *       /aas/submodels                                        Retrieves all SubModels from the current Asset Administration Shell
 *       /aas/submodels/{subModelId}                           Retrieves a specific SubModel from a specific Asset Administration Shell
 *       /aas/submodels/{subModelId}/properties                Retrieves all Properties from the current SubModel
 *       /aas/submodels/{subModelId}/operations                Retrieves all Operations from the current SubModel
 *       /aas/submodels/{subModelId}/events                    Retrieves all Events from the current SubModel
 *       /aas/submodels/{subModelId}/properties/{propertyId}   Retrieves a specific property from the AAS's SubModel
 *       /aas/submodels/{subModelId}/operations/{operationId}  Retrieves a specific Operation from the AAS's SubModel
 *       /aas/submodels/{subModelId}/events/{eventId}          Retrieves a specific event from the AAS's submodel
 *       
 * - createValue
 *       /aas/submodels                                        Adds a new SubModel to an existing Asset Administration Shell
 *       /aas/submodels/{subModelId}/properties                Adds a new property to the AAS's submodel
 *       /aas/submodels/{subModelId}/operations                Adds a new operation to the AAS's submodel
 *       /aas/submodels/{subModelId}/events                    Adds a new event to the AAS's submodel
 *       
 *  - invokeOperation
 *       /aas/submodels/{subModelId}/operations/{operationId}  Invokes a specific operation from the AAS' submodel with a list of input parameters
 *       
 * - deleteValue
 *       /aas/submodels/{subModelId}                           Deletes a specific SubModel from a specific Asset Administration Shell
 *       /aas/submodels/{subModelId}/properties/{propertyId}   Deletes a specific Property from the AAS's SubModel
 *       /aas/submodels/{subModelId}/operations/{operationId}  Deletes a specific Operation from the AAS's SubModel
 *       /aas/submodels/{subModelId}/events/{eventId}          Deletes a specific event from the AAS's submodel
 *              
 * - setModelPropertyValue
 *       /aas/submodels/{subModelId}/properties/{propertyId}   Sets the value of the AAS's SubModel's Property
 * 
 * 
 * @author kuhn
 *
 */
public class VABMultiSubmodelProvider implements IModelProvider {

	
	/**
	 * Store providers
	 */
	protected Map<String, IModelProvider> providers = new HashMap<>();
	
	
	
	/**
	 * Constructor
	 */
	public VABMultiSubmodelProvider() {
		// Do nothing
	}
	
	
	
	/**
	 * Add a provider
	 * 
	 * @param elementId             Element ID
	 * @param modelContentProvider  Model content provider
	 */
	public void addProvider(String elementId, IModelProvider modelContentProvider) {
		// Add model provider
		providers.put(elementId, modelContentProvider);
	}
	
	
	/**
	 * Remove a provider
	 * 
	 * @param elementId Element ID
	 */
	public void removeProvider(String elementId) {
		// Remove model provider
		providers.remove(elementId);
	}
	
	
	
	
	
	/**
	 * Return the scope of an element
	 */
	@Override
	public String getElementScope(String elementPath) {
		// TODO Auto-generated method stub
		return null;
	}

	

	/**
	 * Get the value of an element
	 */
	@Override
	public Object getModelPropertyValue(String path) {
		// Not (yet) implemented get requests
		// - Return the AAS - not implemented
		if (path.toLowerCase().equals("/aas") || path.toLowerCase().equals("/aas/")) throw new VABProviderNotImplementedException();
		// - Return all sub models of this provider
		if (path.toLowerCase().equals("/aas/submodels") || path.toLowerCase().equals("/aas/submodels/")) throw new VABProviderNotImplementedException();

		// Split path
		String[] pathElements = VABPathTools.splitPath(path);
		// - Ignore first 3 elements, as it is "/aas/submodels" --> '', 'aas', 'submodels'
		// - Invoke provider and return result
		return providers.get(pathElements[3]).getModelPropertyValue(VABPathTools.buildPath(pathElements, 4));
	}



	/**
	 * Change a model property value
	 */
	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		// Split path
		String[] pathElements = VABPathTools.splitPath(path);
		// - Ignore first 3 elements, as it is "/aas/submodels" --> '', 'aas', 'submodels'
		// - Invoke provider and return result
		providers.get(pathElements[3]).setModelPropertyValue(VABPathTools.buildPath(pathElements, 4), newValue);
	}



	@Override
	public void setModelPropertyValue(String path, Object... newEntry) throws Exception {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void createValue(String path, Object newValue) throws Exception {
		// Split path
		String[] pathElements = VABPathTools.splitPath(path);
		// - Ignore first 3 elements, as it is "/aas/submodels" --> '', 'aas', 'submodels'
		// - Invoke provider and return result
		providers.get(pathElements[3]).createValue(VABPathTools.buildPath(pathElements, 4), newValue);
	}



	@Override
	public void deleteValue(String path) throws Exception {
		// Split path
		String[] pathElements = VABPathTools.splitPath(path);
		// - Ignore first 3 elements, as it is "/aas/submodels" --> '', 'aas', 'submodels'
		// - Invoke provider and return result
		providers.get(pathElements[3]).deleteValue(VABPathTools.buildPath(pathElements, 4));
	}



	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		// Split path
		String[] pathElements = VABPathTools.splitPath(path);
		// - Ignore first 3 elements, as it is "/aas/submodels" --> '', 'aas', 'submodels'
		// - Invoke provider and return result
		providers.get(pathElements[3]).deleteValue(VABPathTools.buildPath(pathElements, 4), obj);
	}



	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {
		// Split path
		String[] pathElements = VABPathTools.splitPath(path);
		// - Ignore first 3 elements, as it is "/aas/submodels" --> '', 'aas', 'submodels'
		// - Invoke provider and return result
		return providers.get(pathElements[3]).invokeOperation(VABPathTools.buildPath(pathElements, 4), parameter);
	}



	@Override
	public Map<String, IElementReference> getContainedElements(String path) {
		// TODO Auto-generated method stub
		return null;
	}
}


