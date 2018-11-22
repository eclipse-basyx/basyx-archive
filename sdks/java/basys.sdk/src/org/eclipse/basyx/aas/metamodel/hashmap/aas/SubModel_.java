package org.eclipse.basyx.aas.metamodel.hashmap.aas;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.IElement;
import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.impl.resources.basic.BaseElement;
import org.eclipse.basyx.aas.metamodel.hashmap.VABElementContainer;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.Property;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.operation.Operation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasTemplate;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Qualifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Typable;

/**
 * Submodel class
 * 
 * @author kuhn
 *
 */
public class SubModel_ extends HashMap<String, Object> implements VABElementContainer, ISubModel {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Submodel properties
	 */
	protected Map<String, IProperty> properties = new HashMap<>();

	/**
	 * Submodel operations
	 */
	protected Map<String, IOperation> operations = new HashMap<>();

	
	/**
	 * Constructor
	 */
	public SubModel_() {
		// Add qualifiers
		putAll(new HasSemantics());
		putAll(new HasTemplate());
		putAll(new Identifiable());
		putAll(new Qualifiable());
		putAll(new Typable());

		// Default values
		put("id_carrier", null);
		put("id_submodelDefinition", null);

		// Properties
		put("properties", properties);
		put("operations", operations);
	}

	
	/**
	 * Constructor
	 */
	public SubModel_(HasSemantics semantics, Identifiable identifiable, Qualifiable qualifiable, Typable typeable) {
		// Add qualifiers
		putAll(semantics);
		putAll(new HasTemplate());
		putAll(identifiable);
		putAll(qualifiable);
		putAll(typeable);

		// Default values
		put("id_carrier",            null);
		put("id_submodelDefinition", null);
		
		// Properties
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

		// ((Map<String, Object>) value.get("valueType")).put("id_semantics", id);

		properties.put(id, value);

	}

	/**
	 * Add operation
	 */
	@Override
	public void addOperation(String id, Operation operation) {

		System.out.println("adding Operation " + id);

		// Add single operation
		operations.put(id, operation);
	}

	@Override
	public void addEvent(String id, Object event) {
		// TODO Auto-generated method stub
		System.out.println("addEvent Not yet implemented");
	}

	@Override
	public Map<String, Object> getAsMap() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.basyx.aas.api.resources.IElementContainer#getElement(java.lang.
	 * String)
	 */
	@Override
	public IElement getElement(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.basyx.aas.api.resources.IElementContainer#getElements()
	 */
	@Override
	public Map<String, IElement> getElements() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.basyx.aas.api.resources.IElement#getId()
	 */
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.basyx.aas.api.resources.IElement#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.basyx.aas.api.resources.IElement#setParent(org.eclipse.basyx.aas.
	 * impl.resources.basic.BaseElement)
	 */
	@Override
	public void setParent(BaseElement parent) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.basyx.aas.api.resources.IElement#getParent()
	 */
	@Override
	public BaseElement getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	protected void addProperty(Property prop) {
		((Map<String, Object>) get("properties")).put(prop.getId(), prop);
	}

}
