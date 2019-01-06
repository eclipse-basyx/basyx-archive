package org.eclipse.basyx.vab.core.proxy;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.vab.core.IModelProvider;


/**
 * Proxy class for a VAB element
 * 
 * @author kuhn
 *
 */
public class VABElementProxy {

	
	/**
	 * Connector specific target address
	 */
	protected String addr = null;

	
	/**
	 * IModelProvider that connects to the target address
	 */
	protected IModelProvider provider = null;

	
	
	
	/**
	 * Constructor expects address and provider reference
	 */
	public VABElementProxy(String addr, IModelProvider provider) {
		// Store references
		this.addr = addr;
		this.provider = provider;
	}

	
	/**
	 * Read VAB element value
	 */
	public Object readElementValue(String elementPath) {
		// Get element from server
		Object result = provider.getModelPropertyValue(elementPath);

		// Return property value
		return result;
	}

	
	/**
	 * Update VAB element value <br />
	 * <br />
	 * If the element does not exist it will be created<br />
	 */
	public void updateElementValue(String elementPath, Object newValue) throws ServerException {
		// Set property value
		try {
			// Change element on server
			provider.setModelPropertyValue(elementPath, newValue);
		} catch (ServerException e) {
			throw new ServerException(e);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	
	/**
	 * Add element on server
	 */
	public void createElement(String elementPath, Object newValue) throws ServerException {
		// Set property value
		try {
			// Create new element on server
			provider.createValue(elementPath, newValue);
		} catch (ServerException e) {
			e.printStackTrace();
			throw new ServerException(e);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	
	/**
	 * Delete element from server
	 */
	public void deleteElement(String elementPath) throws ServerException {
		// Delete property from server
		try {
			// Delete element from server
			provider.deleteValue(elementPath);
		} catch (ServerException e) {
			throw new ServerException(e);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	
	/**
	 * Delete element from server
	 */
	public void deleteElement(String elementPath, Object value) throws ServerException {
		// Delete property from server
		try {
			// Delete element from server
			provider.deleteValue(elementPath, value);
		} catch (ServerException e) {
			throw new ServerException(e);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	
	/**
	 * Invoke element as an operation
	 */
	public Object invoke(String elementPath, Object... parameter) throws ServerException {
		// Invoke operation on server
		try {
			// Invoke server operation
			return provider.invokeOperation(elementPath, parameter);
		} catch (ServerException e) {
			throw new ServerException(e);
		} catch (Exception e) {
			throw new ServerException(e.getClass().getName(), e.getMessage());
		}
	}
}
