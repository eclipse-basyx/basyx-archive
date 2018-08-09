package org.eclipse.basyx.aas.impl.resources.basic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.basyx.aas.api.exception.ResourceNotFoundException;
import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.api.resources.basic.IOperation;
import org.eclipse.basyx.aas.api.resources.basic.IProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;

public class SubModel extends BaseElement implements ISubModel {

	protected String typeDefinition;
	
	protected Map<String, IOperation> operations = new HashMap<>();
	protected Map<String, IProperty> properties = new HashMap<>();
	protected Map<String, Event> events = new HashMap<>();
	
	/**
	 * Server Clock that gets incremented when a property of this submodel is changed
	 */
	protected Integer clock = 0;
	
	/**
	 * Indicates that this submodel and all its children are read only / frozen
	 */
	protected Boolean frozen = false; 
	
	/**
	 * Constructor
	 * 
	 * -FIXME: Document: Properties of Sub models also need to be registered as properties
	 */
	public SubModel() {
		super();

	}
	
	public synchronized void addOperation(IOperation operation) {
		if (operation.getName() == null || operation.getName().isEmpty()) {
			throw new IllegalArgumentException();
		}		
		this.operations.put(operation.getName(), operation);
		operation.setParent(this);
	}
	
	@Override
	public Map<String, IOperation> getOperations() {
		return this.operations;
	}
	
	public synchronized void addProperty(IProperty property) {
		if (property.getName() == null || property.getName().isEmpty()) {
			throw new IllegalArgumentException();
		}		
		this.properties.put(property.getName(), property);
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

	/**
	 * Returns if this submodel is frozen
	 */
	@Override
	public boolean isFrozen() {
		return this.frozen;
	}

	/** 
	 * Sets all properties of this submodel to readonly TODO Test for Referenced submodels?
	 */
	@Override
	public void freeze() {
		Iterator<Entry<String, IProperty>> it = this.properties.entrySet().iterator();
		while (it.hasNext()) {
			Property   p = (Property) it.next().getValue();
			p.setWriteable(false);	// TODO writable is never checked when accessing static properties
		}
		
		this.frozen = true;
		
	}


	/**
	 * Makes all properties of this submodel writable TODO Test for Referenced submodels?
	 */
	@Override
	public void unfreeze() {
		Iterator<Entry<String, IProperty>> it = this.properties.entrySet().iterator();
		while (it.hasNext()) {
			Property   p = (Property) it.next().getValue();
			p.setWriteable(true);
		}
		
		this.frozen = false;
	}
}

