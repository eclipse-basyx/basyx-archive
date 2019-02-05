package org.eclipse.basyx.aas.metamodel.hashmap.aas;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.VABElementContainer;
import org.eclipse.basyx.aas.metamodel.hashmap.VABModelMap;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind.HasKind;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.DataElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;

/**
 * Submodel class
 * 
 * @author kuhn, schnicke
 *
 *
 */
public class SubModel extends VABModelMap<Object> implements VABElementContainer, ISubModel {

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
	 * Submodel elements in general. Does also contain operations and properties
	 */
	protected Map<String, SubmodelElement> elements = new HashMap<String, SubmodelElement>();

	/**
	 * Constructor
	 */
	public SubModel() {
		// Add qualifiers
		putAll(new HasSemantics());
		putAll(new Identifiable());
		putAll(new Qualifiable());
		putAll(new HasDataSpecification());
		putAll(new HasKind());

		// Attributes
		put("submodelElement", elements);

		// Helper attributes
		put("properties", properties);
		put("operations", operations);
	}

	/**
	 * Constructor
	 */
	public SubModel(HasSemantics semantics, Identifiable identifiable, Qualifiable qualifiable, HasDataSpecification specification, HasKind hasKind) {
		// Add qualifiers
		putAll(semantics);
		putAll(identifiable);
		putAll(qualifiable);
		putAll(specification);
		putAll(hasKind);

		// Attributes
		put("submodelElement", elements);

		// Helper attributes
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
	public void addDataElement(DataElement value) {

		String id = value.getId();
		if (value instanceof IProperty) {
			System.out.println("adding Property " + id);
			properties.put(id, (IProperty) value);
			elements.put(id, value);
		} else {
			throw new RuntimeException("Tried to add DataElement with id " + id + " which is does not implement IProperty");
		}
	}

	/**
	 * Add operation
	 */
	@Override
	public void addOperation(Operation operation) {

		String id = operation.getId();
		if (operation instanceof IOperation) {

			System.out.println("adding Operation " + id);

			// Add single operation
			operations.put(id, operation);
			elements.put(id, operation);
		} else {
			throw new RuntimeException("Tried to add Operation with id " + id + " which is does not implement IOperation");
		}
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

	@Override
	public void addEvent(Object event) {
		throw new FeatureNotImplementedException();
	}

	@Override
	public void addElementCollection(SubmodelElementCollection collection) {
		getElements().put(collection.getId(), collection);
		if (collection instanceof IProperty) {
			getProperties().put(collection.getId(), collection);
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, SubmodelElement> getElements() {
		return (Map<String, SubmodelElement>) get("elements");
	}
}
