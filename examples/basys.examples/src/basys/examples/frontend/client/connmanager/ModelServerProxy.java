package basys.examples.frontend.client.connmanager;

import java.util.HashMap;

import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

import basys.examples.urntools.ModelUrn;



/**
 * Proxy class for connecting to a model server.
 * 
 * Model servers provide static data models to the VAB. this class hides the nasty details to communicate via VAB primitives with model servers. 
 * 
 * @author kuhn
 *
 */
public class ModelServerProxy extends VABElementProxy {

	
	/**
	 * Constructor
	 * 
	 * @param addr Address
	 * @param provider Provider reference
	 */
	public ModelServerProxy(String addr, IModelProvider provider) {
		super(addr, provider);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	/**
	 * Push model to model repository
	 */
	public void pushToServer(ModelUrn modelID, HashMap<String, Object> model) {
		// Model URL on server
		String modelURLOnServer = "/aas/submodels/aasRepository/"+modelID.getEncodedURN();
		
		// Transfer model to server
		this.createElement(modelURLOnServer, model);	
	}
	
	
	/**
	 * Get URL to model on server
	 */
	public String getURLToModel(ModelUrn modelID) {
		// Return address on server
		return this.addr+"/aas/submodels/aasRepository/"+modelID.getEncodedURN();
	}

	
	/**
	 * Update model element value on server
	 */
	public void updateElementValue(ModelUrn modelID, String elementPath, Object newValue) {
		// Build URL
		String modelURLOnServer = "/aas/submodels/aasRepository/"+modelID.getEncodedURN();
		
		// Invoke base function 
		updateElementValue(modelURLOnServer+elementPath, newValue);
	}
	
	
	/**
	 * Read model element value
	 */
	public Object readElementValue(ModelUrn modelID, String elementPath) {
		// Build URL
		String modelURLOnServer = "/aas/submodels/aasRepository/"+modelID.getEncodedURN();

		// Invoke base function 
		return readElementValue(modelURLOnServer+elementPath);
	}
	
	
	/**
	 * Create model element 
	 */
	public void createElement(ModelUrn modelID, String elementPath, Object newValue) {
		// Build URL
		String modelURLOnServer = "/aas/submodels/aasRepository/"+modelID.getEncodedURN();

		// Invoke base function 
		createElement(modelURLOnServer+elementPath, newValue);
	}
	
	
	/**
	 * Delete model element based on path
	 */
	public void deleteElement(ModelUrn modelID, String elementPath) {
		// Build URL
		String modelURLOnServer = "/aas/submodels/aasRepository/"+modelID.getEncodedURN();
		
		// Invoke base function 
		deleteElement(modelURLOnServer+elementPath);
	}
	
	
	/**
	 * Delete model element based on path and value
	 */
	public void deleteElement(ModelUrn modelID, String elementPath, Object value) {
		// Build URL
		String modelURLOnServer = "/aas/submodels/aasRepository/"+modelID.getEncodedURN();
		
		// Invoke base function 
		deleteElement(modelURLOnServer+elementPath, value);
	}
}

