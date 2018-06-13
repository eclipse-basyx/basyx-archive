package org.eclipse.basyx.aas.backend;

import org.eclipse.basyx.aas.api.resources.basic.ISingleProperty;
import org.eclipse.basyx.aas.backend.connector.IBasysConnector;




/**
 * Implement a connected AAS property that communicates via HTTP/REST and that holds one value
 * 
 * @author kuhn
 *
 */
public class ConnectedSingleProperty extends ConnectedProperty implements ISingleProperty {
	
	
	/**
	 * Constructor - expect the URL to the sub model
	 * @param connector 
	 */
	public ConnectedSingleProperty(String id, String submodelId, String path, String url, IBasysConnector connector, ConnectedAssetAdministrationShellManager aasMngr) {
		// Invoke base constructor
		super(id, submodelId, path, url, connector, aasMngr);
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
	 */
	@Override
	public void set(Object newValue) {
		// set property value
		basysConnector.basysSet(this.modelProviderURL, propertyPath, newValue);
		
		// update Cache
		this.setElement(newValue);
	}


	/**
	 * Move property value
	 */
	@Override
	public void moveTo(ISingleProperty property) {
		Object thisvalue = get();
		property.set(thisvalue);
	}


}
