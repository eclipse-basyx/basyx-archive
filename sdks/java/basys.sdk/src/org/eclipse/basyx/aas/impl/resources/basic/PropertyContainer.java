package org.eclipse.basyx.aas.impl.resources.basic;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ResourceNotFoundException;
import org.eclipse.basyx.aas.api.resources.basic.IContainerProperty;
import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.api.resources.basic.IElementContainer;
import org.eclipse.basyx.aas.api.resources.basic.IProperty;



/**
 * Nested property class definition
 * 
 * @author kuhn
 *
 */
public class PropertyContainer extends Property implements IElementContainer, IContainerProperty {

	
	/**
	 * Store property type
	 */
	protected String typeDefinition;
	
	
	/** 
	 * Contained operations
	 */
	protected Map<String, Operation> operations = new HashMap<>();
	
	/**
	 * Contained properties
	 */
	protected Map<String, IProperty> properties = new HashMap<>();
	
	/**
	 * Contained event definitions
	 */
	protected Map<String, Event> events = new HashMap<>();
	
	
	
	
	/**
	 * Default constructor
	 */
	public PropertyContainer() {
		super();
		setContainer(true);
	}
	
	public synchronized void addOperation(Operation operation) {
		if (operation.name == null || operation.name.isEmpty()) {
			throw new IllegalArgumentException();
		}		
		this.operations.put(operation.name, operation);
		operation.setParent(this);
	}
	
	public Map<String, Operation> getOperations() {
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

	public synchronized void addEvent(Event event) {
		if (event.name == null || event.name.isEmpty()) {
			throw new IllegalArgumentException();
		}		
		this.events.put(event.name, event);
		event.setParent(this);		
	}
	
	public Map<String, Event> getEvents() {
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
		if ((result = properties.get(name)) != null) return result;
		if ((result = operations.get(name)) != null) return result;
		if ((result = events.get(name)) != null)     return result;
		
		// Element was not found
		throw new ResourceNotFoundException(name);
	}


	/**
	 * Return all contained elements
	 */	
	@Override @SuppressWarnings({ "unchecked", "rawtypes" }) 
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
