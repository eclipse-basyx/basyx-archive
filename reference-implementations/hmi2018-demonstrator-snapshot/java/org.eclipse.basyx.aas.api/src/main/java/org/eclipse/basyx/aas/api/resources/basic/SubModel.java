package org.eclipse.basyx.aas.api.resources.basic;

import java.util.HashMap;
import java.util.Map;

public class SubModel extends BaseElement {

	protected String typeDefinition;
	
	protected Map<String, Operation> operations = new HashMap<>();
	protected Map<String, Property> properties = new HashMap<>();
	protected Map<String, Event> events = new HashMap<>();
	protected AssetKind assetKind;

	
	public SubModel() {
		super();
	}
	

	public AssetKind getAssetKind() {
		return assetKind;
	}
	public void setAssetKind(AssetKind kind) {
		this.assetKind = kind;
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
	
	public synchronized void addProperty(Property property) {
		if (property.name == null || property.name.isEmpty()) {
			throw new IllegalArgumentException();
		}		
		this.properties.put(property.name, property);
		property.setParent(this);		
	}
	
	public Map<String, Property> getProperties() {
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
	
}
