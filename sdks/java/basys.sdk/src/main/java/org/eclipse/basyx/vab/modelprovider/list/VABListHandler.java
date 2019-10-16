package org.eclipse.basyx.vab.modelprovider.list;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.vab.modelprovider.generic.IVABElementHandler;

/**
 * A VAB list handler. Handles "List" elements. Elements in lists can not be
 * accessed by its list index, but only by their reference. The available
 * references of a list can be queried with "/list/references", which returns an
 * Integer array of all references available in the list. Given a reference, the
 * element can be queried with "/list/byRef_x", wheras x is the corresponding
 * reference integer. Given references always point to the same objects, even if
 * other elements are added or removed from the list. Makes use of
 * {@link ReferencedArrayList} and also replaces given lists and arrays by this
 * class for reference handling.
 * 
 * @author espen
 *
 */
public class VABListHandler implements IVABElementHandler {
	public static final String VALUE_BYREF_SUFFIX = "byRef_";
	public static final String VALUE_REFERENCES_SUFFIX = "references";

	/**
	 * Replaces all instances of Lists and Arrays by {@link ReferencedArrayList} to
	 * enable reference handling.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object preprocessObject(Object object) {
		if (object instanceof Map<?, ?>) {
			return prepareMap((Map<String, Object>) object);
		} else if (object instanceof List<?>) {
			return prepareList((List<Object>) object);
		} else if (object instanceof Object[]) {
			return prepareArray((Object[]) object);
		} else {
			return object;
		}
	}

	/**
	 * Handles {@link ReferencedArrayList} objects. "/byRef_x" returns the object
	 * that is references by the integer x. "/references" return all references used
	 * by the list object.
	 * 
	 */
	@Override
	public Object getElementProperty(Object element, String propertyName) throws Exception {
		if (element instanceof ReferencedArrayList) {
			ReferencedArrayList<?> listElement = (ReferencedArrayList<?>) element;
			if (propertyName.startsWith(VALUE_BYREF_SUFFIX)) {
				return getReferencedListElement(listElement, propertyName);
			} else if (propertyName.equals(VALUE_REFERENCES_SUFFIX)) {
				return listElement.getReferences();
			} else {
				// not possible to query list index directly
				return null;
			}
		}
		return null;
	}

	/**
	 * "/byRef_x" replaces the object that is referenced by the integer x. GET
	 * "/byRef_x" will point to the new value instead. Does not work, if the
	 * reference is not available yet.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setModelPropertyValue(Object element, String propertyName, Object newValue) throws Exception {
		if (element instanceof ReferencedArrayList) {
			ReferencedArrayList<Object> list = (ReferencedArrayList<Object>) element;
			Integer index = getReferencedIndex(list, propertyName);
			if (index != null) {
				list.set(index, newValue);
			}
		}
	}

	/**
	 * Adds a new elements to the list. The references are updated accordingly, so a
	 * new reference is created for each new element added to the list.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void createValue(Object element, Object newValue) throws Exception {
		if (element instanceof Collection) {
			Collection<Object> collection = (Collection<Object>) element;
			collection.add(newValue);
		}
	}

	/**
	 * "/byRef_x" deletes the object that is referenced by the integer x. As a
	 * result, the reference x is no longer used by the list.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteValue(Object element, String propertyName) throws Exception {
		if (element instanceof ReferencedArrayList) {
			ReferencedArrayList<Object> list = (ReferencedArrayList<Object>) element;
			Integer index = getReferencedIndex(list, propertyName);
			if (index != null) {
				list.remove((int) index);
			}
		}
	}

	/**
	 * Deletes an object from the list. The list references are updated accordingly.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteValue(Object element, Object property) throws Exception {
		if (element instanceof Collection) {
			((Collection<Object>) element).remove(property);
		}
	}

	private Map<String, Object> prepareMap(Map<String, Object> map) {
		for (String s : map.keySet()) {
			map.put(s, preprocessObject(map.get(s)));
		}
		return map;
	}

	private ReferencedArrayList<Object> prepareList(List<Object> list) {
		ReferencedArrayList<Object> result = new ReferencedArrayList<>();
		for (Object o : list) {
			result.add(preprocessObject(o));
		}
		return result;
	}

	private ReferencedArrayList<Object> prepareArray(Object[] array) {
		ReferencedArrayList<Object> result = new ReferencedArrayList<>();
		for (Object o : array) {
			result.add(preprocessObject(o));
		}
		return result;
	}

	private Integer getReferencedIndex(ReferencedArrayList<?> list, String propertyName) throws Exception {
		if (propertyName.startsWith(VALUE_BYREF_SUFFIX)) {
			String indexName = propertyName.substring(VALUE_BYREF_SUFFIX.length());
			Integer index = Integer.valueOf(indexName);
			return list.getReferencedIndex(index);
		} else {
			return null;
		}
	}

	private Object getReferencedListElement(ReferencedArrayList<?> list, String propertyName) throws Exception {
		String indexName = propertyName.substring(VALUE_BYREF_SUFFIX.length());
		Integer index = Integer.valueOf(indexName);
		return list.getByReference(index);
	}
}
