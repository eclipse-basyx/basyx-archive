package org.eclipse.basyx.vab.backend.gateway;

import org.eclipse.basyx.vab.core.IConnectorProvider;
import org.eclipse.basyx.vab.core.IModelProvider;

/**
 * IModelProvider that delegates all calls to a Connector to enable gateway
 * functionality
 * 
 * @author schnicke
 *
 */
public class DelegatingModelProvider implements IModelProvider {

	// Provider that provides the connectors
	private IConnectorProvider connectorProvider;

	public DelegatingModelProvider(IConnectorProvider connectorProvider) {
		super();
		this.connectorProvider = connectorProvider;
	}

	@Override
	public Object getModelPropertyValue(String path) throws Exception {
		return getProvider(path).getModelPropertyValue(removeAddressEntry(path));
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		getProvider(path).setModelPropertyValue(removeAddressEntry(path), newValue);
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		getProvider(path).createValue(removeAddressEntry(path), newEntity);
	}

	@Override
	public void deleteValue(String path) throws Exception {
		getProvider(path).deleteValue(removeAddressEntry(path));
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		getProvider(path).deleteValue(removeAddressEntry(path), obj);
	}

	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {
		return getProvider(path).invokeOperation(removeAddressEntry(path), parameter);
	}

	/**
	 * Returns the appropriate connector based on address
	 * 
	 * @param path
	 * @return
	 */
	private IModelProvider getProvider(String path) {
		return connectorProvider.getConnector(getAddressEntry(path));
	}

	/**
	 * Gets the address entry of a path <br />
	 * E.g. basyx://127.0.0.1:6998//https://localhost/test/ will return
	 * basyx://127.0.0.1:6998
	 * 
	 * @param path
	 * @return
	 */
	public static String getAddressEntry(String path) {
		if (path == null || !path.contains("//")) {
			return "";
		} else {
			String splitted[] = path.split("//");
			System.out.println("RT3:"+splitted[0] + "//" + splitted[1]);
			return splitted[0] + "//" + splitted[1];
		}
	}

	/**
	 * Removes from a path the address part <br/>
	 * E.g. basyx://127.0.0.1:6998//https://localhost/test/ will return
	 * https://localhost/test/
	 * 
	 * @param path
	 * @return
	 */
	public static String removeAddressEntry(String path) {
		if (!path.contains("//")) {
			System.out.println("RT1:"+path);
			return path;
		} else {
			path = path.replaceFirst(getAddressEntry(path), "");
			if (path.startsWith("//")) {
				path = path.replaceFirst("//", "");
			}
			System.out.println("RT2:"+path);
			return path;
		}
	}
}
