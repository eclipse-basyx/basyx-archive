package basys.examples.frontend.client.connmanager;

import java.util.HashMap;

import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

import basys.examples.aasdescriptor.ModelUrn;



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
	 * Full address with qualifier.
	 */
	protected String fullQualifiedAddr = null;
	
	
	
	/**
	 * Constructor
	 * 
	 * @param addr Address
	 * @param provider Provider reference
	 */
	public ModelServerProxy(String addr, String fullAddr, IModelProvider provider) {
		// Invoke base constructor
		super(addr, provider);
		
		// Store qualified address
		fullQualifiedAddr = fullAddr;
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
	 * Push model to model repository
	 */
	public void pushToServer(String modelIDRaw, HashMap<String, Object> model) {
		// Push to server
		pushToServer(new ModelUrn(modelIDRaw), model);
	}

	
	
	
	/**
	 * Get URL to model on server
	 */
	public String getURLToModel(ModelUrn modelID) {
		// Return address on server
		return this.fullQualifiedAddr+"/aas/submodels/aasRepository/"+modelID.getEncodedURN();
	}

	
	/**
	 * Get URL to model on server
	 */
	public String getURLToModel(String modelIDRaw) {
		// Return address on server
		return getURLToModel(new ModelUrn(modelIDRaw));
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
	 * Update model element value on server
	 */
	public void updateElementValue(String modelIDRaw, String elementPath, Object newValue) {
		updateElementValue(new ModelUrn(modelIDRaw), elementPath, newValue); 
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
	 * Read model element value
	 */
	public Object readElementValue(String modelIDRaw, String elementPath) {
		return readElementValue(new ModelUrn(modelIDRaw), elementPath); 
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
	 * Create model element 
	 */
	public void createElement(String modelIDRaw, String elementPath, Object newValue) {
		createElement(new ModelUrn(modelIDRaw), elementPath, newValue); 
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
	 * Delete model element based on path
	 */
	public void deleteElement(String modelIDRaw, String elementPath) {
		deleteElement(new ModelUrn(modelIDRaw), elementPath);
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
	
	
	/**
	 * Delete model element based on path and value
	 */
	public void deleteElement(String modelIDRaw, String elementPath, Object value) {
		deleteElement(new ModelUrn(modelIDRaw), elementPath, value);
	}
}

