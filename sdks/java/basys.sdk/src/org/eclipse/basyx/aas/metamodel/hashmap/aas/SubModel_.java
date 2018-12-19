package org.eclipse.basyx.aas.metamodel.hashmap.aas;

import java.util.Map;

import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.VABElementContainer;
import org.eclipse.basyx.aas.metamodel.hashmap.VABModelMap;
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
public class SubModel_ extends VABModelMap<Object> implements VABElementContainer, ISubModel {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Submodel properties
	 */
	protected Map<String, IProperty> properties = new VABModelMap<>();

	
	/**
	 * Submodel operations
	 */
	protected Map<String, IOperation> operations = new VABModelMap<>();

	
	
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
		put("id_carrier", null);
		put("id_submodelDefinition", null);

		// Properties
		put("properties", properties);
		put("operations", operations);
	}

	
	/**
	 * Get sub model properties
	 */
	@Override
	public Map<String, IProperty> getProperties() {
		return properties;
	}

	
	/**
	 * Get sub model operations
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

	/**
	 * Get the Id of the submodel
	 */
	@Override
	public String getId() {
		return (String) get("idShort");
	}


	/**
	 * Set the Id of the submodel
	 */
	@Override
	public void setId(String id) {
		put("idShort", id);
	}

	@SuppressWarnings("unchecked")
	protected void addProperty(Property prop) {
		((Map<String, Object>) get("properties")).put(prop.getId(), prop);
	}
}
