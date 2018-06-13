package org.eclipse.basyx.aas.backend;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.basic.IMapProperty;
import org.eclipse.basyx.aas.backend.connector.IBasysConnector;


/**
 * 
 * @author pschorn
 *
 */
public class ConnectedMapProperty extends ConnectedProperty implements IMapProperty  {

	public ConnectedMapProperty(String id, String submodelId, String path, String url, IBasysConnector connector, ConnectedAssetAdministrationShellManager aasMngr) {
		
		// Invoke base constructor
		super(id, submodelId, path, url, connector, aasMngr);
	}

	@Override
	public Object getValue(Object key) {
		
		Object map = this.getElement();
		
		// Fetch value at index @objRef
		Object value = null;
		if (map instanceof Map<?,?>) {
			
			// Type safe get value for key
			value = ((Map<?,?>) map).get(key);
		}
		
		// Return property value
		return value;
	}

	@Override
	public void put(Object key, Object value) {
		
		// Post data to server
		basysConnector.basysPost(this.modelProviderURL, propertyPath, "create" , key, value);
		
		// Update cache
		Object map = this.getElement();
		if (map instanceof Map<?,?>) {
			
			// Type safe cast 
			((Map) map).put(key, value);
		}
	}

	@Override
	public Collection<Object> getKeys() {
		
		Object map = this.getElement();
		
		if (map instanceof Map<?,?>) {
			
			// Type safe get keys for map
			return (Collection<Object>) ((Map<?,?>) map).keySet();
		}
		
		return null;
		
	}

	@Override
	public Integer getEntryCount() {
		
		Object map = this.getElement();
		
		if (map instanceof Map<?,?>) {
			
			// Type safe get size for map
			return ((Map<?,?>) map).size();
		}
		
		return null;
	}

	@Override
	public void remove(Object key) {
		
		// Post data to server
		basysConnector.basysPost(this.modelProviderURL, propertyPath, "delete" , key);
		
		// Update cache
		Object map = this.getElement();
		if (map instanceof Map<?,?>) {
			
			// Type safe remove element from map
			((Map) map).remove(key);
		}
	}

}