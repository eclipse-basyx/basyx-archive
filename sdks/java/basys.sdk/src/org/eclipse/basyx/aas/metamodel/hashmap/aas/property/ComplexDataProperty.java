package org.eclipse.basyx.aas.metamodel.hashmap.aas.property;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.IContainerProperty;
import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.metamodel.hashmap.VABElementContainer;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.operation.Operation;

/**
 * ComplexDataProperty (nested) property class
 * 
 * @author pschorn, schnicke
 *
 */
public class ComplexDataProperty extends Property implements VABElementContainer, IContainerProperty {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Nested properties
	 */
	protected Map<String, IProperty> properties = new HashMap<>();

	/**
	 * Nested operations
	 */
	protected Map<String, IOperation> operations = new HashMap<>();

	/**
	 * Constructor
	 */
	public ComplexDataProperty() {
		// Add qualifiers
		// putAll(new Property());

		super();// Properties

		put("properties", properties);
		put("operations", operations);

	}

	/**
	 * Get submodel properties
	 */
	@Override
	public Map<String, IProperty> getProperties() {
		return properties;
	}

	/**
	 * Get submodel operations
	 */
	@Override
	public Map<String, IOperation> getOperations() {
		return operations;
	}

	/**
	 * Add property
	 */
	@Override
	public void addProperty(String id, Property value) {

		System.out.println("adding Property " + id);

		properties.put(id, value);
	}

	/**
	 * Add operation
	 */
	@Override
	public void addOperation(String id, Operation operation) {

		System.out.println("adding Operation " + id);

		operations.put(id, operation);
	}

	@Override
	public void addEvent(String id, Object event) {
		// TODO Auto-generated method stub
		System.out.println("addEvent Not yet implemented");

	}

	@SuppressWarnings("unchecked")
	protected void addProperty(Property prop) {
		((Map<String, Object>) get("properties")).put(prop.getId(), prop);
	}

	@Override
	public Map<String, Object> getAsMap() {
		return this;
	}
}
