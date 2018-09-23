package org.eclipse.basyx.aas.backend;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.impl.reference.ElementRef;

/**
 * Implement a AAS that communicates via HTTP/REST and operates on the
 * administration shell model
 * 
 * @author kuhn
 *
 */
public class ConnectedAssetAdministrationShell extends ConnectedElement implements IAssetAdministrationShell {

	/**
	 * Store AAS manager
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = null;

	/**
	 * Constructor - expect the URL to the administration shell server
	 * 
	 * @param connector
	 */
	public ConnectedAssetAdministrationShell(ConnectedAssetAdministrationShellManager aasMngr, String id, IModelProvider provider) {
		// Invoke base constructor
		super(provider);
		setId(id);
		// Store variables
		aasManager = aasMngr;
	}

	/**
	 * Retrieve AAS sub models
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ISubModel> getSubModels() {
		// Get AAS sub models
		Map<String, ElementRef> subModels = (Map<String, ElementRef>) provider.getModelPropertyValue(getId() + "/aas/submodels");

		// Create connected sub model proxies
		Map<String, ISubModel> result = new HashMap<String, ISubModel>();
		// - Fill map
		for (String submodelName : subModels.keySet()) {
			System.out.println("Adding Submodel: " + submodelName);
			ISubModel model = aasManager.retrieveSubModelProxy(subModels.get(submodelName));
			model.setParent(this);
			System.out.println("Added Submodel: " + submodelName + ": " + model);

			result.put(submodelName, model);
		}

		// Return sub models
		return result;
	}

	/**
	 * Add a sub model to a remote asset administration shell
	 */
	@Override
	public void addSubModel(ISubModel subModel) {
		try {
			provider.createValue(getId() + "/aas/submodels", subModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public IElement getElement(String name) {
		// TODO Auto-generated method stub
		throw new FeatureNotImplementedException();
	}

	@Override
	public Map<String, IElement> getElements() {
		// TODO Auto-generated method stub
		throw new FeatureNotImplementedException();
	}
}
