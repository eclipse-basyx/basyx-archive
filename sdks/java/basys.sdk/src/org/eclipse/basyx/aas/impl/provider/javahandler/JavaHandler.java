package org.eclipse.basyx.aas.impl.provider.javahandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.impl.reference.ElementRef;

/**
 * Java handler class
 * 
 * @author kuhn
 *
 */
public class JavaHandler<T extends IElement> {

	/**
	 * The handled object
	 */
	protected T obj;

	/**
	 * Map property names to numeric keys
	 */
	protected Map<String, Integer> propertyIDs = new HashMap<String, Integer>();

	/**
	 * Map operation names to numeric keys
	 */
	protected Map<String, Integer> operationIDs = new HashMap<String, Integer>();

	/**
	 * Property ID counter
	 */
	protected int propertyIDCounter = 0;

	/**
	 * Operation ID counter
	 */
	protected int operationIDCounter = 0;

	/**
	 * Store get operations
	 */
	protected Vector<Function<T, Object>> getOps = new Vector<>();

	/**
	 * Store set operations
	 */
	protected Vector<BiConsumer<T, Object>> setOps = new Vector<>();

	/**
	 * Store add operations
	 */
	protected Vector<BiConsumer<T, Object>> addOps = new Vector<>();

	/**
	 * Store remove operations
	 */
	protected Vector<BiConsumer<T, Object>> removeOps = new Vector<>();

	/**
	 * Store invoke operations
	 */
	protected Vector<BiFunction<T, Object[], Object>> invokeOps = new Vector<>();

	protected Map<String, IElementReference> submodels = new HashMap<>();

	protected Map<String, IElementReference> properties = new HashMap<>();

	protected Map<String, IElementReference> operations = new HashMap<>();

	/**
	 * Constructor
	 */
	public JavaHandler(T handledObject) {
		// Store object reference
		obj = handledObject;
	}

	/**
	 * Get handled IElement
	 */
	public IElement getIElement() {
		return obj;
	}

	/**
	 * Add a handled property
	 */
	public void addProperty(String propertyName, Function<T, Object> getOp, BiConsumer<T, Object> setOp, BiConsumer<T, Object> addOp, BiConsumer<T, Object> removeOp) {
		// Get property ID
		int propertyID = propertyIDCounter++;

		// Store ID to name mapping
		propertyIDs.put(propertyName, propertyID);

		// Extend vector size
		getOps.setSize(propertyID + 1);
		setOps.setSize(propertyID + 1);
		addOps.setSize(propertyID + 1);
		removeOps.setSize(propertyID + 1);

		// Store lambdas
		getOps.set(propertyID, getOp);
		setOps.set(propertyID, setOp);
		addOps.set(propertyID, addOp);
		removeOps.set(propertyID, removeOp);

		if (propertyName.contains(".")) {
			propertyName = propertyName.split("\\.")[0];
		}

		properties.put(propertyName, new ElementRef(obj.getParent().getId(), obj.getId(), propertyName));
	}

	public void addSubModel(String submodelName) {
		submodels.put(submodelName, new ElementRef(obj.getId(), submodelName));
	}

	/**
	 * Add a handled operation
	 */
	public void addOperation(String operationName, BiFunction<T, Object[], Object> invocation) {
		// Get operation ID
		int operationID = operationIDCounter++;

		// Store ID to name mapping
		operationIDs.put(operationName, operationID);

		// Extend vector size
		invokeOps.setSize(operationID + 1);

		// Store lambdas
		invokeOps.set(operationID, invocation);
		
		operations.put(operationName, new ElementRef(obj.getParent().getId(), obj.getId(), operationName));
	}

	/**
	 * Get property value
	 */
	public Object getValue(String id) {
		if (id.equals("submodels")) {
			return submodels;
		} else if (id.endsWith("properties")) {
			return properties;
		} else if (id.contains("properties.")) {
			id = id.substring(id.lastIndexOf(".properties.") + ".properties.".length());
		} else if (id.endsWith("operations")) {
			return operations;
		}

		// If id is null pointer, return object reference
		if ((id == null) || (id.length() == 0))
			return obj;

		// Try numeric ID
		try {
			// Convert id to integer
			Integer val = Integer.parseInt(id);
			// - Lookup operation for ID, invoke operation
			return getOps.get(val).apply(obj);
		} catch (NumberFormatException e) {

			// Get operation handler by id, invoke handler with object instance assigned to
			// this handler
			try {
				return getOps.get(propertyIDs.get(id)).apply(obj);
			} catch (NullPointerException ex) {
				return null;
			}
		}
	}

	/**
	 * Set property value
	 */
	public void setValue(String id, Object newValue) {
		
		if (id.contains("properties.")) {
			id = id.substring(id.lastIndexOf(".properties.") + ".properties.".length());
		}
		
		// If id is null pointer
		if ((id == null) || (id.length() == 0))
			;

		// Try numeric ID
		try {
			// Convert id to integer
			Integer val = Integer.parseInt(id);
			// - Lookup operation for ID, invoke operation
			setOps.get(val).accept(obj, newValue);
		} catch (NumberFormatException e) {

			// Get operation handler by id, invoke handler with object instance assigned to
			// this handler
			try {
				setOps.get(propertyIDs.get(id)).accept(obj, newValue);
			} catch (NullPointerException ex) {
				throw new NullPointerException("ID did not match operation handler.");
			}
		}
	}

	/**
	 * add collection Value
	 * 
	 * @param id
	 * @param addedValue
	 */
	public void setContainedValue(String id, Object... addedValue) {
		// If id is null pointer
		if ((id == null) || (id.length() == 0))
			;

		// Try numeric ID
		try {
			// Convert id to integer
			Integer val = Integer.parseInt(id);
			// - Lookup operation for ID, invoke operation
			addOps.get(val).accept(obj, addedValue);
		} catch (NumberFormatException e) {

			// Get operation handler by id, invoke handler with object instance assigned to
			// this handler
			try {
				addOps.get(propertyIDs.get(id)).accept(obj, addedValue);
			} catch (NullPointerException ex) {
				throw new NullPointerException("ID did not match operation handler.");
			}
		}
	}

	/**
	 * delete collection entry
	 * 
	 * @param id
	 * @param addedValue
	 */
	public void deleteContainedValue(String id, Object deletedValue) {
		// If id is null pointer
		if ((id == null) || (id.length() == 0))
			;

		// Try numeric ID
		try {
			// Convert id to integer
			Integer val = Integer.parseInt(id);
			// - Lookup operation for ID, invoke operation
			removeOps.get(val).accept(obj, deletedValue);
		} catch (NumberFormatException e) {

			// Get operation handler by id, invoke handler with object instance assigned to
			// this handler
			try {
				removeOps.get(propertyIDs.get(id)).accept(obj, deletedValue);
			} catch (NullPointerException ex) {
				throw new NullPointerException("ID did not match operation handler.");
			}
		}
	}

	public Object invokeOperation(String id, Object[] parameter) {
		// If id is null pointer
		if ((id == null) || (id.length() == 0))
			;

		if (id.contains("operations.")) {
			id = id.substring(id.lastIndexOf(".operations.") + ".operations.".length());
		}
		
		// Try numeric ID
		try {
			// Convert id to integer
			Integer val = Integer.parseInt(id);
			// - Lookup operation for ID, invoke operation
			return invokeOps.get(val).apply(obj, parameter);
		} catch (NumberFormatException e) {

			// Get operation handler by id, invoke handler with object instance assigned to
			// this handler
			try {
				return invokeOps.get(operationIDs.get(id)).apply(obj, parameter);
			} catch (NullPointerException ex) {
				throw new NullPointerException("ID did not match operation handler.");
			}
		}
	}
}
