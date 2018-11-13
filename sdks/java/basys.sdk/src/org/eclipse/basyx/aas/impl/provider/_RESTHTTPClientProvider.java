package org.eclipse.basyx.aas.impl.provider;

import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.impl.tools.BaSysID;
import org.eclipse.basyx.vab.core.IDirectoryService;
import org.eclipse.basyx.vab.core.IModelProvider;

/**
 * Provider class that export a Java object as BaSys model via HTTP REST
 * interface
 * 
 * @author kuhn, pschorn
 *
 */
public class _RESTHTTPClientProvider implements IModelProvider {
	/**
	 * Reference to directory service
	 */
	protected IDirectoryService directoryService = null;

	/**
	 * Scope of the client connection
	 */
	protected String clientScope = null;

	protected HTTPConnectorProvider connectorProvider;

	/**
	 * Constructor
	 */
	public _RESTHTTPClientProvider(String scope, IDirectoryService directory, HTTPConnectorProvider connectorProvider) {
		// Store scope and directory reference
		directoryService = directory;
		clientScope = scope;
		this.connectorProvider = connectorProvider;
	}

	/**
	 * Get a sub model property value
	 * 
	 * @param path
	 *            Path to the requested value
	 */
	@Override
	public Object getModelPropertyValue(String path) {
		System.out.println("HTTP-Prov Get:" + path);
		System.out.println("- Element SM :" + BaSysID.instance.getSubmodelID(path));
		System.out.println("- Element AAS:" + BaSysID.instance.getAASID(path));
		System.out.println("- Element Pth:" + BaSysID.instance.getPath(path));

		// Get the scoped path of the addressed aas or submodel
		String address = BaSysID.instance.getAddress(path);

		// Get server address from directory
		String addr = directoryService.lookup(address);
		// - Address check
		if (addr == null)
			throw new RuntimeException("Not able to resolve address: " + address);

		// Remove Client Provider Scope (if any)
		path = path.substring((path.indexOf(clientScope) == -1) ? 0 : path.indexOf(this.clientScope));

		// Get scoped service path
		String servicePath = BaSysID.instance.getScopedServicePath(path, address);

		// Return model property
		return connectorProvider.getConnector(addr).getModelPropertyValue(servicePath);
	}

	/**
	 * Set a sub model property value
	 * 
	 * @param path
	 *            Path to the requested value
	 * @param newValue
	 *            Updated value
	 * @throws ServerException
	 */
	@Override
	public void setModelPropertyValue(String path, Object newValue) throws ServerException {
		System.out.println("HTTP-Prov Set:" + path + " to " + newValue);
		System.out.println("- Element SM :" + BaSysID.instance.getSubmodelID(path));
		System.out.println("- Element AAS:" + BaSysID.instance.getAASID(path));

		String address = BaSysID.instance.getAddress(path);

		// Get address from directory
		String addr = directoryService.lookup(address);
		// - Address check
		if (addr == null)
			throw new RuntimeException("Not able to resolve address: " + address);

		// Remove Client Provider Scope (if any)
		path = path.substring((path.indexOf(clientScope) == -1) ? 0 : path.indexOf(this.clientScope));

		String servicePath = BaSysID.instance.getScopedServicePath(path, address);

		// Set model property
		try {
			connectorProvider.getConnector(addr).setModelPropertyValue(servicePath, newValue);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("Generic Exception", "Exception: " + e.toString());
		}
	}

	/**
	 * Create new entity under the given path
	 * 
	 * @param path
	 *            Path to the parent of the new entity
	 * @param newValue
	 *            Inserted value.
	 * @throws ServerException
	 */
	@Override
	public void createValue(String path, Object newElement) throws ServerException {
		System.out.println("HTTP-Prov create:" + path + " to " + newElement);

		String address = BaSysID.instance.getAddress(path);

		// Get address from directory
		String addr = directoryService.lookup(address);
		// - Address check
		if (addr == null)
			throw new RuntimeException("Not able to resolve address: " + address);

		// Remove Client Provider Scope (if any)
		path = path.substring((path.indexOf(clientScope) == -1) ? 0 : path.indexOf(this.clientScope));

		String servicePath = BaSysID.instance.getScopedServicePath(path, address);

		// Post data to server
		try {
			connectorProvider.getConnector(addr).createValue(servicePath, newElement);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("Generic Exception", "Exception: " + e.toString());
		}
	}

	/**
	 * Delete the entity under the given path
	 * 
	 * @param path
	 *            Path to the entity
	 * @param deletedId
	 *            ID to delete
	 * @throws ServerException
	 */
	@Override
	public void deleteValue(String path) throws ServerException {
		System.out.println("HTTP-Prov delete:" + path);

		String address = BaSysID.instance.getAddress(path);

		// Get address from directory
		String addr = directoryService.lookup(address);
		// - Address check
		if (addr == null)
			throw new RuntimeException("Not able to resolve address: " + address);

		// Remove Client Provider Scope (if any)
		path = path.substring((path.indexOf(clientScope) == -1) ? 0 : path.indexOf(this.clientScope));

		String servicePath = BaSysID.instance.getScopedServicePath(path, address);

		// Remove entity
		try {
			connectorProvider.getConnector(addr).deleteValue(servicePath);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("Generic Exception", "Exception: " + e.toString());
		}
	}

	/**
	 * Deletes an entry from a map or collection by key
	 */
	@Override
	public void deleteValue(String path, Object parameter) throws Exception {
		System.out.println("HTTP-Prov Delete Contained:" + path + " to " + parameter.toString());
		System.out.println("- Element AAS:" + BaSysID.instance.getAASID(path));
		System.out.println("- Element SM :" + BaSysID.instance.getSubmodelID(path));

		String address = BaSysID.instance.getAddress(path);

		// Get address from directory
		String addr = directoryService.lookup(address);
		// - Address check
		if (addr == null)
			throw new RuntimeException("Not able to resolve address: " + address);

		// Remove Client Provider Scope (if any)
		path = path.substring((path.indexOf(clientScope) == -1) ? 0 : path.indexOf(this.clientScope));

		String servicePath = BaSysID.instance.getScopedServicePath(path, address);

		// Remove entry from map or collection
		try {
			connectorProvider.getConnector(addr).deleteValue(servicePath, parameter);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("Generic Exception", "Exception: " + e.toString());
		}
	}

	/**
	 * Invoke an operation
	 * 
	 * @throws ServerException
	 */
	@Override
	public Object invokeOperation(String path, Object[] parameter) throws ServerException {
		System.out.println("HTTP-Prov invoke:" + path + " with " + parameter);

		String address = BaSysID.instance.getAddress(path);

		// Get address from directory
		String addr = directoryService.lookup(address);
		// - Address check
		if (addr == null)
			throw new RuntimeException("Not able to resolve address: " + address);

		// Remove Client Provider Scope (if any)
		path = path.substring((path.indexOf(clientScope) == -1) ? 0 : path.indexOf(this.clientScope));

		String servicePath = BaSysID.instance.getScopedServicePath(path, address);

		// Invoke Operation on server
		try {
			return connectorProvider.getConnector(addr).invokeOperation(servicePath, parameter);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("Generic Exception", "Exception: " + e.toString());
		}
	}

	/**
	 * Get contained sub model elements
	 * 
	 * Contained sub model elements are returned as Map of key/value pairs. Keys are
	 * Strings, values are ElementRef objects that contain a reference to a complex
	 * object instance.
	 * 
	 * @param path
	 *            Path to sub model or property
	 * @return Contained properties
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IElementReference> getContainedElements(String path) {
		String address = BaSysID.instance.getAddress(path);

		// Get address from directory
		String addr = directoryService.lookup(address);
		// - Address check
		if (addr == null)
			throw new RuntimeException("Not able to resolve address: " + address);

		// Remove Client Provider Scope (if any)
		path = path.substring((path.indexOf(clientScope) == -1) ? 0 : path.indexOf(this.clientScope));

		String servicePath = BaSysID.instance.getScopedServicePath(path, address);

		System.out.println("GetContained:" + servicePath);

		// Get collection reference
		return (Map<String, IElementReference>) getModelPropertyValue(servicePath);

		// Process return types
		// if (target instanceof Map) return
		// JavaObjectProvider.createMapFromMap((Map<String, IElement>) target);
		// if (target instanceof Collection) return
		// JavaObjectProvider.createMapFromCollection((Collection<IElement>) target);
		// if (target instanceof IElementContainer) return
		// JavaObjectProvider.createMapFromIElementContainer((IElementContainer)
		// target);

		// No contained properties
		// return result;
	}
}
