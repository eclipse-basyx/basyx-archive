package org.eclipse.basyx.aas.impl.provider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.api.resources.basic.IElementContainer;
import org.eclipse.basyx.aas.api.services.IDirectoryService;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnector;
import org.eclipse.basyx.aas.impl.tools.BaSysID;




/**
 * Provider class that export a Java object as BaSys model via HTTP REST interface 
 * 
 * @author kuhn, pschorn
 *
 */
public class RESTHTTPClientProvider extends AbstractModelScopeProvider implements IModelProvider {

	
	/**
	 * Create HTTP connector
	 */
	protected HTTPConnector httpConnector = new HTTPConnector();
	
	
	/**
	 * Reference to directory service
	 */
	protected IDirectoryService directoryService = null;
	
	
	/**
	 * Scope of the client connection
	 */
	protected String clientScope = null;
	
	
	
	
	/**
	 * Constructor
	 */
	public RESTHTTPClientProvider(String scope, IDirectoryService directory) {
		// Store scope and directory reference
		directoryService = directory;
		clientScope = scope;
	}
	
	
	
	/**
	 * Get scope of a provided element
	 */
	@Override
	public String getElementScope(String elementPath) {
		return clientScope;
	}
	
	
	/**
	 * Get a sub model property value
	 * 
	 * @param path Path to the requested value
	 */
	@Override
	public Object getModelPropertyValue(String path) {
		System.out.println("HTTP-Prov Get:"+path);
		System.out.println("- Element SM :"+BaSysID.instance.getSubmodelID(path));
		System.out.println("- Element AAS:"+BaSysID.instance.getAASID(path));
		System.out.println("- Element Pth:"+BaSysID.instance.getPath(path));

		// Get address from directory
		String addr = directoryService.lookup(BaSysID.instance.getAddress(path));
		// - Address check
		if (addr == null) throw new RuntimeException("Not able to resolve address: "+path);
		// Return model property
		return httpConnector.basysGet(addr, path);
	}

	
	/**
	 * Set a sub model property value
	 * 
	 * @param path Path to the requested value
	 * @param newValue Updated value
	 * @throws ServerException 
	 */
	@Override
	public void setModelPropertyValue(String path, Object newValue) throws ServerException {
		System.out.println("HTTP-Prov Set:"+path+" to "+newValue);
		System.out.println("- Element SM :"+BaSysID.instance.getSubmodelID(path));
		System.out.println("- Element AAS:"+BaSysID.instance.getAASID(path));
		
		// Get address from directory
		String addr = directoryService.lookup(BaSysID.instance.getAddress(path));
		// - Address check
		if (addr == null) throw new RuntimeException("Not able to resolve address: "+path);
		
		// Set model property
		httpConnector.basysSet(addr, path, newValue);
	}
	
	
	/**
	 * Add an entry to a map or collection
	 * 
	 * @param path Path to the requested value
	 * @param newEntry Updated value
	 * @throws ServerException 
	 */
	@Override
	public void setModelPropertyValue(String path, Object... newEntry) throws ServerException {
		System.out.println("HTTP-Prov Set:"+path+" to "+newEntry);
		System.out.println("- Element SM :"+BaSysID.instance.getSubmodelID(path));
		System.out.println("- Element AAS:"+BaSysID.instance.getAASID(path));
		
		// Get address from directory
		String addr = directoryService.lookup(BaSysID.instance.getAddress(path));
		// - Address check
		if (addr == null) throw new RuntimeException("Not able to resolve address: "+path);
		
		// Set model property
		httpConnector.basysSet(addr, path, newEntry);
	}

	
	
	/**
	 * Create new entity under the given path
	 * 
	 * @param path Path to the parent of the new entity
	 * @param newValue Inserted value. 
	 * @throws ServerException 
	 */
	@Override
	public void createValue(String path, Object newElement) throws ServerException {
		System.out.println("HTTP-Prov create:"+path+" to "+newElement);
		
		// Get address from directory
		String addr = directoryService.lookup(BaSysID.instance.getAddress(path));
		// - Address check
		if (addr == null) throw new RuntimeException("Not able to resolve address: "+path);
		
		// Post data to server
		httpConnector.basysCreate(addr, path, newElement);
	}
	
	
	/**
	 * Delete the entity under the given path
	 * 
	 * @param path Path to the entity
	 * @param deletedId ID to delete
	 * @throws ServerException 
	 */
	@Override
	public void deleteValue(String path) throws ServerException {
		System.out.println("HTTP-Prov delete:"+path);
		
		// Get address from directory
		String addr = directoryService.lookup(BaSysID.instance.getAddress(path));
		// - Address check
		if (addr == null) throw new RuntimeException("Not able to resolve address: "+path);
		
		// Remove entity
		httpConnector.basysDelete(addr, path);
	}
	
	/**
	 * Deletes an entry from a map or collection by key
	 */
	@Override
	public void deleteValue(String path, Object parameter) throws Exception {
		System.out.println("HTTP-Prov Delete Contained:"+path+" to "+parameter.toString());
		System.out.println("- Element AAS:"+BaSysID.instance.getAASID(path));
		System.out.println("- Element SM :"+BaSysID.instance.getSubmodelID(path));
		
		// Get address from directory
		String addr = directoryService.lookup(BaSysID.instance.getAddress(path));
		// - Address check
		if (addr == null) throw new RuntimeException("Not able to resolve address: "+path);
		
		// Remove entry from map or collection
		httpConnector.basysDelete(addr, path, parameter);
	}
	
	
	/**
	 * Invoke an operation
	 * @throws ServerException 
	 */
	@Override
	public Object invokeOperation(String path, Object[] parameter) throws ServerException {
		System.out.println("HTTP-Prov invoke:"+path+" with "+parameter);
		
		// Get address from directory
		String addr = directoryService.lookup(BaSysID.instance.getAddress(path));
		// - Address check
		if (addr == null) throw new RuntimeException("Not able to resolve address: "+path);
				
		// Invoke Operation on server
		return httpConnector.basysInvoke(addr, path, parameter);
	}
	
	
	/**
	 * Get contained sub model elements
	 * 
	 * Contained sub model elements are returned as Map of key/value pairs. Keys are Strings, values are 
	 * ElementRef objects that contain a reference to a complex object instance.
	 * 
	 * @param path Path to sub model or property
	 * @return Contained properties
	 */
	@Override
	public Map<String, IElementReference> getContainedElements(String path) {
		// Return value
		Map<String, IElementReference> result = new HashMap<>();

		System.out.println("GetContained:"+path);

		// Get collection reference
		Object target = getModelPropertyValue(path);
		
		System.out.println("ContainedProperties: "+target);
		
		// Process return types
		if (target instanceof Map)               return JavaObjectProvider.createMapFromMap((Map<String, IElement>) target);
		if (target instanceof Collection)        return JavaObjectProvider.createMapFromCollection((Collection<IElement>) target);
		if (target instanceof IElementContainer) return JavaObjectProvider.createMapFromIElementContainer((IElementContainer) target);
		
		// No contained properties
		return result;
	}
}
