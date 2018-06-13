package org.eclipse.basyx.aas.backend;
import java.util.Collection;
import java.util.List;

import org.eclipse.basyx.aas.api.resources.basic.ICollectionProperty;
import org.eclipse.basyx.aas.backend.connector.IBasysConnector;

/**
 * 
 * @author pschorn
 *
 */
public class ConnectedCollectionProperty extends ConnectedProperty implements ICollectionProperty {

	public ConnectedCollectionProperty(String id, String submodelId, String path, String url, IBasysConnector connector, ConnectedAssetAdministrationShellManager aasMngr) {
		
		// Invoke base constructor
		super(id, submodelId, path, url, connector, aasMngr);
	}

	
	/**
	 * Get item from collection at index @objRef, starting at index 0
	 * @param objRef is expected to be an Integer
	 */
	@Override
	public Object get(Object objRef) {
		
		// Check objRef type valid FIXME this should happen on the server side with a proper exception!
		if (!(objRef instanceof Integer)) {return null;}
		
		// Get collection
		Object collection = this.getElement();
		
		// Fetch value at index @objRef
		Object value = null;
		if (collection instanceof List<?>) {
			try  {
				// Type safe cast to List<?>. If integer cast fails, value is null.
				value = ((List<?>) collection).get((Integer) objRef);
				
				// FIXME exception handling
			} catch (IndexOutOfBoundsException e) { 
				return null;
			}
		}
		
		// Return property value
		return value;
	}

	/**
	 * Add item to collection
	 */
	@Override
	public void set(Object newValue) {
		
		// Post data to server
		basysConnector.basysPost(this.modelProviderURL, propertyPath, "create" , newValue);
		
		// Update cache
		Object collection = this.getElement();
		if (collection instanceof List<?>) {
			
			// Check if element is already inside, delete old value in this case (because it is done in the JOP too)
			((List<?>) collection).remove(newValue);
			
			// Type safe add element to collection
			((List<Object>) collection).add(newValue);
		}
	}

	/**
	 * Delete item from collection
	 */
	@Override
	public void remove(Object oldValue) {
		
		// Post data to server
		basysConnector.basysPost(this.modelProviderURL, propertyPath, "delete" , oldValue);
		
		// Update cache
		Object collection = this.getElement();
		if (collection instanceof List<?>) {
			
			// Check if element is already inside, delete old value in this case (because it is done in the JOP too)
			((List<?>) collection).remove(oldValue);
		}
	}

	/**
	 * return all elements from collection
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> getElements() {
		// Get collection
		return (Collection<Object>) this.getElement(); // type safe cast?
	
	}

	/**
	 * return number of elements in collection
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getElementCount() {
		// Get collection size
		return ((Collection<Object>) this.getElement()).size(); // type safe cast?
	
	}

}
