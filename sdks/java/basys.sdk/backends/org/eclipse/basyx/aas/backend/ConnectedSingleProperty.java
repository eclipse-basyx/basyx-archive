package org.eclipse.basyx.aas.backend;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.api.services.IModelProvider;

/**
 * Implement a connected AAS property that communicates via HTTP/REST and that
 * holds one value
 * 
 * @author kuhn
 *
 */
public class ConnectedSingleProperty extends ConnectedProperty implements ISingleProperty {

	/**
	 * Constructor - expect the URL to the sub model
	 * 
	 * @param connector
	 */
	public ConnectedSingleProperty(String id, String submodelId, String path, IModelProvider provider, ConnectedAssetAdministrationShellManager aasMngr) {
		// Invoke base constructor
		super(id, submodelId, path, provider, aasMngr);
	}

	/**
	 * Get property value
	 */
	@Override
	public Object get() {

		Object value = this.getElement();

		// cache property value
		this.setElement(value);

		// Return property value
		return value;
	}

	/**
	 * Get property value
	 * 
	 * @throws ServerException
	 */
	@Override
	public void set(Object newValue) throws ServerException {
		// set property value
		try {
			provider.setModelPropertyValue(propertyPath, newValue);
			// update Cache
			this.setElement(newValue);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerException("Unknown exception", "");
		}

	}

	/**
	 * Move property value
	 * 
	 * @throws ServerException
	 */
	@Override
	public void moveTo(ISingleProperty property) throws ServerException {
		Object thisvalue = get();
		property.set(thisvalue);
	}

}
