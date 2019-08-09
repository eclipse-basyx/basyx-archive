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
	private String addr = "";

	/**
	 * IModelProvider that connects to the target address
	 */
	protected IModelProvider provider = null;

	/**
	 * Constructor expects address and provider reference <br />
	 * E.g, if the element resides on <i>basyx://127.0.0.1</i> in the path
	 * <i>a/b/c</i>, <i>provider</i> would realize the connection to
	 * <i>basyx://127.0.0.1</i> and <i>addr</i> would be <i>a/b/c</i>. The
	 * VABElementProxy then directly points to the element.
	 * 
	 * @param addr
	 *            address "within" the provider
	 * @param provider
	 */
	public VABElementProxy(String addr, IModelProvider provider) {
		// Store references
		this.addr = trimAddress(addr);
		this.provider = provider;
	}

	/**
	 * Read VAB element value
	 */
	public Object readElementValue(String elementPath) throws ServerException {
		// Get element from server
		try {
			// Change element on server
			return provider.getModelPropertyValue(constructPath(elementPath));
		} catch (ServerException e) {
			throw new ServerException(e); // FIXME Exception Handling should be done in JSONConnector
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException(e.getClass().getName(), e.getMessage());
		}
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
			provider.setModelPropertyValue(constructPath(elementPath), newValue);
		} catch (ServerException e) {
			e.printStackTrace();
			throw new ServerException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException(e.getClass().getName(), e.getMessage());
		}
	}

	/**
	 * Add element on server
	 */
	public void createElement(String elementPath, Object newValue) throws ServerException {
		// Set property value
		try {
			// Create new element on server
			provider.createValue(constructPath(elementPath), newValue);
		} catch (ServerException e) {
			e.printStackTrace();
			throw new ServerException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException(e.getClass().getName(), e.getMessage());
		}
	}

	/**
	 * Delete element from server
	 */
	public void deleteElement(String elementPath) throws ServerException {
		// Delete property from server
		try {
			// Delete element from server
			provider.deleteValue(constructPath(elementPath));
		} catch (ServerException e) {
			throw new ServerException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException(e.getClass().getName(), e.getMessage());
		}
	}

	/**
	 * Delete element from server
	 */
	public void deleteElement(String elementPath, Object value) throws ServerException {
		// Delete property from server
		try {
			// Delete element from server
			provider.deleteValue(constructPath(elementPath), value);
		} catch (ServerException e) {
			throw new ServerException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException(e.getClass().getName(), e.getMessage());
		}
	}

	/**
	 * Invoke element as an operation
	 */
	public Object invoke(String elementPath, Object... parameter) throws ServerException {
		// Invoke operation on server
		try {
			// Invoke server operation
			return provider.invokeOperation(constructPath(elementPath), parameter);
		} catch (ServerException e) {
			throw new ServerException(e);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException(e.getClass().getName(), e.getMessage());
		}
	}

	/**
	 * Remove trailing slashes from address "/"
	 */
	private String trimAddress(String parAddr) {
		if (parAddr == null) {
			return null;
		}

		// Return value
		String result = parAddr;

		// Remove trailing "/" from address
		while (result.endsWith("/")) {
			result = result.substring(0, result.length() - 1);
		}

		// Return trimmed address
		return result;
	}

	/**
	 * Add path to VAB element address. Make sure that resulting path contains the proper number of slashes ("/")
	 * 
	 * @param path
	 *            Input path
	 * @return processed path
	 */
	private String constructPath(String path) {
		if (path == null) {
			return null;
		}

		// Trim input path
		String trimmedPath = trimAddress(path);
		// Remove leading slashes from path
		while (trimmedPath.startsWith("/")) {
			trimmedPath = trimmedPath.substring(1);
		}
		// Add one slash at beginning of path
		trimmedPath = "/" + trimmedPath;

		// Now combine both paths
		if (addr != null && !addr.isEmpty()) {
			return addr + "//" + trimmedPath;
		} else {
			return trimmedPath;
		}
	}
}
