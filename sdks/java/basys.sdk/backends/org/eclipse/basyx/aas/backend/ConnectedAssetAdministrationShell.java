package org.eclipse.basyx.aas.backend;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.resources.basic.IConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.connector.IBasysConnector;
import org.eclipse.basyx.aas.impl.reference.ElementRef;




/**
 * Implement a AAS that communicates via HTTP/REST and operates on the administration shell model
 * 
 * @author kuhn
 *
 */
public class ConnectedAssetAdministrationShell extends ConnectedElement implements IConnectedAssetAdministrationShell {

	
	/**
	 * Store AAS manager
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = null;
	
	
	/**
	 * Store AAS ID
	 */
	protected String aasID = null;
	
	
	/**
	 * Cache for registered sub models
	 */
	private BaSysCache cache = null;
	
	
	/**
	 * Constructor - expect the URL to the administration shell server
	 * @param connector 
	 */
	public ConnectedAssetAdministrationShell(ConnectedAssetAdministrationShellManager aasMngr, String id, String serverUrl, IBasysConnector connector) {
		// Invoke base constructor
		super(serverUrl, connector);
		
		// Store variables
		aasID        = id;
		aasManager   = aasMngr;
	}

	
	
	/**
	 * Retrieve AAS sub models
	 */
	@Override @SuppressWarnings("unchecked")
	public Map<String, ISubModel> getSubModels() {
		// Get AAS sub models
		Map<String, ElementRef> subModels = (Map<String, ElementRef>) basysConnector.basysGet(this.modelProviderURL, aasID+"/subModels");
		
		// Create connected sub model proxies
		Map<String, ISubModel> result = new HashMap<String, ISubModel>();
		// - Fill map
		for (String submodelName: subModels.keySet()) {
			System.out.println("Adding Submodel: "+ submodelName);
			ISubModel model = aasManager.retrieveSubModelProxy(subModels.get(submodelName));
			System.out.println("Added Submodel: "+ submodelName+ ": " + model);
				
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
		// TODO 
		throw new FeatureNotImplementedException();
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

