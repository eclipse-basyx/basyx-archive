package org.eclipse.basyx.models.manufacturing.process.model.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;



/**
 * A bounded queue for BaSys process models.
 * 
 * This class is a facade to the Java LinkedList.
 * 
 * @author kuhn
 *
 */
public class BoundedQueue<T> implements Queue<T> {

	
	/**
	 * Maximum capacity
	 */
	protected int maxCapacity = -1;
	
	
	/**
	 * Queue implementation
	 */
	protected Queue<T> queue = new LinkedList<T>();
	
	
	
	
	/**
	 * Constructor
	 */
	public BoundedQueue(int maxElements) {
		// Invoke base constructor
		super();
		
		// Store maximum capacity
		maxCapacity = maxElements;
	}
	

	
	/**
	 * Get number of elements in queue
	 */
	@Override
	public int size() {
		// Delegate to queue
		return queue.size();
	}

	
	/**
	 * Return true if queue is empty
	 */
	@Override
	public boolean isEmpty() {
		// Delegate to queue
		return queue.isEmpty();
	}

	
	/**
	 * Check if queue contains the given element
	 */
	@Override
	public boolean contains(Object o) {
		// Delegate to queue
		return queue.contains(o);
	}

	
	/**
	 * Get queue iterator
	 */
	@Override
	public Iterator<T> iterator() {
		// Delegate to queue
		return queue.iterator();
	}

	
	/**
	 * Convert queue to array
	 */
	@Override
	public Object[] toArray() {
		// Delegate to queue
		return queue.toArray();
	}

	
	/**
	 * Convert queue to array
	 */
	@Override @SuppressWarnings("unchecked")
	public Object[] toArray(Object[] a) {
		// Delegate to queue
		return queue.toArray(a);
	}

	
	/**
	 * Remove element from queue
	 */
	@Override
	public boolean remove(Object o) {
		// Delegate to queue
		return queue.remove(o);
	}

	
	/**
	 * Check if queue contains all elements from given collection
	 */
	@Override @SuppressWarnings("rawtypes")
	public boolean containsAll(Collection c) {
		// Delegate to queue
		return containsAll(c);
	}

	
	/**
	 * Add all elements to queue
	 * 
	 * @throws QueueSizeExceededException if added elements would exceed defined maximum queue size
	 */
	@Override @SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean addAll(Collection c) {
		// Size check
		if ((queue.size() + c.size()) > maxCapacity) throw new QueueSizeExceededException();
		
		// Add elements to queue
		return queue.addAll(c);
	}

	
	/**
	 * Remove all given elements from queue
	 */
	@Override @SuppressWarnings("rawtypes")
	public boolean removeAll(Collection c) {
		// Delegate to queue
		return queue.removeAll(c);
	}

	
	/**
	 * Remove all elements from queue but the given ones
	 */
	@Override @SuppressWarnings("rawtypes")
	public boolean retainAll(Collection c) {
		// Delegate to queue
		return queue.retainAll(c);
	}

	
	/**
	 * Delete elements in queue
	 */
	@Override
	public void clear() {
		// Delegate to queue
		queue.clear();
	}

	
	/**
	 * Add element to queue
	 * 
	 * @throws QueueSizeExceededException if added elements would exceed defined maximum queue size
	 */
	@Override
	public boolean add(T e) {
		// Size check
		if ((queue.size() + 1) > maxCapacity) throw new QueueSizeExceededException();

		// Add element to queue
		return queue.add(e);
	}

	
	/**
	 * Add element to queue
	 * 
	 * @throws QueueSizeExceededException if added elements would exceed defined maximum queue size
	 */	
	@Override
	public boolean offer(T e) {
		// Size check
		if ((queue.size() + 1) > maxCapacity) throw new QueueSizeExceededException();

		// Add element to queue
		return queue.offer(e);
	}


	/**
	 * Remove element from head of queue
	 */
	@Override
	public T remove() {
		// Remove element from queue
		return queue.remove();
	}

	
	/**
	 * Remove element from head of queue
	 */
	@Override
	public T poll() {
		// Remove element from queue
		return queue.poll();
	}

	
	/**
	 * Retrieves but does not remove the element from head of queue
	 */
	@Override
	public T element() {
		// Remove element from queue
		return queue.element();
	}


	/**
	 * Retrieves but does not remove the element from head of queue
	 */
	@Override
	public T peek() {
		// Remove element from queue
		return queue.peek();
	}
}

