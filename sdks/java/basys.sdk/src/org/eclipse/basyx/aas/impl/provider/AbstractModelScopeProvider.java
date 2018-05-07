package org.eclipse.basyx.aas.impl.provider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.impl.tools.BaSysID;




/**
 * Abstract base class for model providers that handle scopes
 * 
 * @author kuhn
 *
 */
public abstract class AbstractModelScopeProvider implements IModelProvider {

	
	/**
	 * Store BaSys model scopes. These scopes are added to the AAS id of model references
	 */
	protected Map<String, String> basysModelScopes = new HashMap<>();

	/**
	 * Store the BaSys model(s)
	 * 
	 * - FIXME: All models (AAS, SubModel need to have disjoint IDs. Add getByType operation?)
	 */
	protected Map<String, IElement> basysModels = new HashMap<>();	
	
	
	/**
	 * Add a BaSys model to this provider
	 */
	public void addModel(IElement model) {
		// Store model in map of provided models
		basysModels.put(model.getId(), model);
	}

	
	/**
	 * Add a BaSys model to this provider
	 */
	public void addModel(IElement model, String modelScope) {
		// Store model scopes
		basysModelScopes.put(model.getId(), modelScope);

		// add model
		addModel(model);
	}

	
	/**
	 * Remove a BaSys model from this provider
	 */
	public void removeModel(String modelId) {
		// Remove model
		basysModels.remove(modelId);
		
		// Remove model scope
		basysModelScopes.remove(modelId);
	}
	
	
	/**
	 * Get list of all models
	 */
	public Collection<String> getAllModels() {
		return basysModels.keySet();
	}

	
	/**
	 * Get scope of a provided element
	 */
	@Override
	public String getElementScope(String elementPath) {
		// Get model IDs and path
		String   aasID      = BaSysID.instance.getAASID(elementPath);
		String   submodelID = BaSysID.instance.getSubmodelID(elementPath);

		// Get element scope
		if (     aasID.length() > 0) return basysModelScopes.get(aasID);
		if (submodelID.length() > 0) return basysModelScopes.get(submodelID);

		// Element scope not found
		return null;
	}
}
