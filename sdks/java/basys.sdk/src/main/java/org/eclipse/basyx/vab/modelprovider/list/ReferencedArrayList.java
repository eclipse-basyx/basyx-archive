package org.eclipse.basyx.vab.modelprovider.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * An ArrayList that additionally holds references to its elements. The references point to the indices of specific
 * objects and are updated when the object's position in the list changes.
 * 
 * @author espen
 *
 * @param <T>
 *            the type of the list elements
 */
public class ReferencedArrayList<T> extends ArrayList<T> {
	private static final long serialVersionUID = 1L;

	private HashMap<Integer, Integer> references = new HashMap<Integer, Integer>();
	private Integer lastIndex = 0;

	public ReferencedArrayList() {
		super();
	}

	public ReferencedArrayList(int initialCapacity) {
		super(initialCapacity);
	}

	public ReferencedArrayList(Collection<? extends T> c) {
		for (T element : c) {
			add(element);
		}
	}

	/**
	 * Returns the object that is referenced by the given reference independent of its index in the list.
	 * 
	 * @param reference
	 *            The reference that points to the requested object
	 * @return The object that is referenced by the reference
	 * @throws InvalidListReferenceException
	 */
	public T getByReference(Integer reference) throws InvalidListReferenceException {
		Integer refIndex = references.get(reference);
		if (refIndex == null) {
			throw new InvalidListReferenceException(reference);
		}
		return get(refIndex);
	}

	/**
	 * Returns the current index of the object the given reference points to.
	 * 
	 * @param reference
	 *            The reference that points to an object in the list
	 * @return The current index of the object referenced by the given reference
	 */
	public Integer getReferencedIndex(Integer reference) {
		return references.get(reference);
	}

	/**
	 * Returns a list of all references used by this list. These references are sorted by their index in the
	 * corresponding list.
	 * 
	 * @return List of references used by this list.
	 */
	public List<Integer> getReferences() {
		List<Integer> refList = new ArrayList<>(references.keySet());
		refList.sort((ref1, ref2) -> {
			return getReferencedIndex(ref1) - getReferencedIndex(ref2);
		});
		return refList;
	}

	@Override
	public void add(int index, T element) {
		super.add(index, element);
		for (Integer reference : references.keySet()) {
			int refIndex = references.get(reference);
			if (refIndex >= index) {
				references.put(reference, refIndex + 1);
			}
		}
		references.put(lastIndex, index);
		lastIndex++;
	}

	@Override
	public boolean add(T e) {
		super.add(e);
		references.put(lastIndex, size() - 1);
		lastIndex++;
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		int i = size();
		boolean hasChanged = super.addAll(c);
		if (hasChanged) {
			for (; i < size(); i++) {
				references.put(lastIndex, i);
				lastIndex++;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		boolean hasChanged = super.addAll(index, c);
		if (hasChanged) {
			for (Integer reference : references.keySet()) {
				int refIndex = references.get(reference);
				if (refIndex >= index) {
					references.put(reference, refIndex + c.size());
				}
			}
			for (int i = index; i < c.size() + index; i++) {
				references.put(lastIndex, i);
				lastIndex++;
			}
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		super.clear();
		references.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() {
		ReferencedArrayList<T> clone = (ReferencedArrayList<T>) super.clone();
		clone.references = (HashMap<Integer, Integer>) references.clone();
		clone.lastIndex = lastIndex;
		return clone;
	}

	@Override
	public T remove(int index) {
		T result = super.remove(index);
		removeReference(index);
		return result;
	}

	@Override
	public boolean remove(Object o) {
		int elementIndex = super.indexOf(o);
		boolean wasRemoved = super.remove(o);
		if (wasRemoved) {
			removeReference(elementIndex);
		}
		return wasRemoved;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean result = false;
		for (Object element : c) {
			result = remove(element) || result;
		}
		return result;
	}

	@Override
	public boolean removeIf(Predicate<? super T> filter) {
		HashMap<Integer, T> relations = storeRelations();
		boolean result = super.removeIf(filter);
		refreshReferences(relations);
		return result;
	}

	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		HashMap<Integer, T> relations = storeRelations();
		super.removeRange(fromIndex, toIndex);
		refreshReferences(relations);
	}

	@Override
	public void replaceAll(UnaryOperator<T> operator) {
		HashMap<Integer, T> relations = storeRelations();
		super.replaceAll(operator);
		refreshReferences(relations);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		HashMap<Integer, T> relations = storeRelations();
		boolean result = super.retainAll(c);
		refreshReferences(relations);
		return result;
	}

	@Override
	public T set(int index, T element) {
		return super.set(index, element);
	}

	@Override
	public void sort(Comparator<? super T> c) {
		HashMap<Integer, T> relations = storeRelations();
		super.sort(c);
		refreshReferences(relations);
	}

	private void removeReference(int index) {
		Collection<Integer> values = references.values();
		Iterator<Integer> i = values.iterator();
		while (i.hasNext()) {
			Integer reference = i.next();
			if (reference == index) {
				i.remove();
				break;
			}
		}
		for (Integer reference : references.keySet()) {
			int refIndex = references.get(reference);
			if (refIndex > index) {
				references.put(reference, refIndex - 1);
			}
		}
	}

	private HashMap<Integer, T> storeRelations() {
		HashMap<Integer, T> result = new HashMap<>();
		for (Integer reference : references.keySet()) {
			T element = get(references.get(reference));
			result.put(reference, element);
		}
		return result;
	}

	private void refreshReferences(HashMap<Integer, T> storedRelations) {
		references.clear();
		for (Integer reference : storedRelations.keySet()) {
			T element = storedRelations.get(reference);
			for (int i = 0; i < size(); i++) {
				if (get(i) == element && !references.containsValue(i)) {
					references.put(reference, i);
					break;
				}
			}
		}
	}
}
