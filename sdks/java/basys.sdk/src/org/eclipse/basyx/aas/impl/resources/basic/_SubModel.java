package org.eclipse.basyx.aas.impl.resources.basic;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ResourceNotFoundException;
import org.eclipse.basyx.aas.api.resources.IElement;
import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;

public class _SubModel extends BaseElement implements ISubModel {

	protected String typeDefinition;

	protected Map<String, IOperation> operations = new HashMap<>();
	protected Map<String, IProperty> properties = new HashMap<>();
	protected Map<String, _Event> events = new HashMap<>();

	/**
	 * Server Clock that gets incremented when a property of this submodel is
	 * changed
	 */
	protected Integer clock = 0;

	/**
	 * Indicates that this submodel and all its children are read only / frozen
	 */
	protected Boolean frozen = false;

	/**
	 * Constructor
	 * 
	 * -FIXME: Document: Properties of Sub models also need to be registered as
	 * properties
	 */
	public _SubModel() {
		super();

	}

	public synchronized void addOperation(IOperation operation) {
		if (operation.getId() == null || operation.getId().isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.operations.put(operation.getId(), operation);
		operation.setParent(this);
	}

	@Override
	public Map<String, IOperation> getOperations() {
		return this.operations;
	}

	public synchronized void addProperty(IProperty property) {
		if (property.getId() == null || property.getId().isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.properties.put(property.getId(), property);
		property.setParent(this);
	}

	@Override
	public Map<String, IProperty> getProperties() {
		return this.properties;
	}

	public synchronized void addEvent(_Event event) {
		if (event.getId() == null || event.getId().isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.events.put(event.getId(), event);
		event.setParent(this);
	}

	public Map<String, _Event> getEvents() {
		return this.events;
	}

	public String getTypeDefinition() {
		return typeDefinition;
	}

	public void setTypeDefinition(String typeDefinition) {
		this.typeDefinition = typeDefinition;
	}

	/**
	 * Return a contained element
	 */
	@Override
	public IElement getElement(String name) {
		// Return value
		IElement result = null;

		// Try to obtain property, operation, event
		if ((result = properties.get(name)) != null)
			return result;
		if ((result = operations.get(name)) != null)
			return result;
		if ((result = events.get(name)) != null)
			return result;

		// Element was not found
		throw new ResourceNotFoundException(name);
	}

	/**
	 * Return all contained elements
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, IElement> getElements() {
		// Create result
		Map result = new HashMap();

		// Add properties, operations, events
		result.putAll(properties);
		result.putAll(operations);
		result.putAll(events);

		// Return map with elements
		return result;
	}
}
