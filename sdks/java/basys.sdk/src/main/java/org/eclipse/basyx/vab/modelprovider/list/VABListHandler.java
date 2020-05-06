package org.eclipse.basyx.vab.modelprovider.list;

import java.util.Collection;

import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.generic.IVABElementHandler;

/**
 * A VAB list handler. Handles "List" elements. Single elements in lists can not be
 * accessed directly. It is possible to add/remove single values to/from lists.
 * 
 * @author espen
 *
 */
public class VABListHandler implements IVABElementHandler {
	public static final String VALUE_BYREF_SUFFIX = "byRef_";
	public static final String VALUE_REFERENCES_SUFFIX = "references";

	/**
	 * Can not access single list entries
	 */
	@Override
	public Object getElementProperty(Object element, String propertyName) throws ProviderException {
		if (element instanceof Collection<?> || element instanceof Object[]) {
			throw new ResourceNotFoundException("It is not possible to access single elements in lists.");
		}
		return null;
	}

	@Override
	public boolean setModelPropertyValue(Object element, String propertyName, Object newValue)
			throws ProviderException {
		if (element instanceof Collection<?> || element instanceof Object[]) {
			throw new ResourceNotFoundException("It is not possible to access single elements in lists.");
		}
		return false;
	}

	/**
	 * Adds a new elements to the list. The references are updated accordingly, so a
	 * new reference is created for each new element added to the list.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean createValue(Object element, Object newValue) throws ProviderException {
		if (element instanceof Collection<?>) {
			Collection<Object> collection = (Collection<Object>) element;
			collection.add(newValue);
			return true;
		}
		return false;
	}

	/**
	 * Not possible to delete by index
	 */
	@Override
	public boolean deleteValue(Object element, String propertyName) throws ProviderException {
		if (element instanceof Collection<?> || element instanceof Object[]) {
			throw new ResourceNotFoundException("It is not possible to remove elements from a list using an index.");
		}
		return false;
	}

	/**
	 * Deletes an object from the list. The list references are updated accordingly.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteValue(Object element, Object property) throws ProviderException {
		if (element instanceof Collection) {
			return ((Collection<Object>) element).remove(property);
		}
		return false;
	}
}
