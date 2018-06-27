package org.eclipse.basyx.aas.impl.provider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ReadOnlyException;
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
	public void setModelPropertyValue(String path, Object newValue) throws ServerException  {
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
	 * Create/insert a value in a collection
	 * 
	 * @param path Path to the collection
	 * @param newValue Inserted value. 
	 * @throws Exception 
	 */
	@Override
	public void createValue(String path, Object parameter) throws ServerException {
		System.out.println("HTTP-Prov create:"+path+" to "+parameter);
		
		// Get address from directory
		String addr = directoryService.lookup(BaSysID.instance.getAddress(path));
		// - Address check
		if (addr == null) throw new RuntimeException("Not able to resolve address: "+path);
		
		// Extract new member
		Object addedMember = ((Object[]) parameter)[0];
		
		// Post data to server
		httpConnector.basysPost(addr, path, "create" , addedMember);
	}
	
	
	/**
	 * Delete a value from a collection
	 * 
	 * @param path Path to the collection
	 * @param deletedId ID to delete
	 * @throws Exception 
	 */
	@Override
	public void deleteValue(String path, Object parameter) throws ServerException {
		System.out.println("HTTP-Prov delete:"+path+" to "+parameter);
		
		// Get address from directory
		String addr = directoryService.lookup(BaSysID.instance.getAddress(path));
		// - Address check
		if (addr == null) throw new RuntimeException("Not able to resolve address: "+path);
		
		// Extract new member
		Object deletedValue = ((Object[]) parameter)[0];
		
		// Post data to server
		httpConnector.basysPost(addr, path, "delete" , deletedValue);
	}
	
	
	/**
	 * Invoke an operation
	 * @throws Exception 
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
