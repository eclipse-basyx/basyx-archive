package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.resources.IContainerProperty;
import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.PropertyType;
import org.eclipse.basyx.aas.metamodel.hashmap.VABElementContainer;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;

/**
 * SubmodelElementCollection as defined by DAAS document <br/>
 * A submodel element collection is a set or list of submodel elements
 * 
 * @author schnicke
 *
 */
public class SubmodelElementCollection extends SubmodelElement implements IContainerProperty, VABElementContainer {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public SubmodelElementCollection() {
		// Put attributes
		put("value", new ArrayList<>());
		put("ordered", true);
		put("allowDuplicates", true);

		put("elements", new HashMap<>());

		// Helper for operations and properties
		put("properties", new HashMap<>());
		put("operations", new HashMap<>());
	}

	/**
	 * 
	 * @param value
	 *            Submodel element contained in the collection
	 * @param ordered
	 *            If ordered=false then the elements in the property collection are
	 *            not ordered. If ordered=true then the elements in the collection
	 *            are ordered.
	 * @param allowDuplicates
	 *            If allowDuplicates=true then it is allowed that the collection
	 *            contains the same element several times
	 */
	public SubmodelElementCollection(Collection<SubmodelElement> value, boolean ordered, boolean allowDuplicates) {
		// Put attributes
		put("value", value);
		put("ordered", ordered);
		put("allowDuplicates", allowDuplicates);

		put("elements", new HashMap<>());

		// Helper for operations and properties
		put("properties", new HashMap<>());
		put("operations", new HashMap<>());

		for (SubmodelElement elem : value) {
			if (elem instanceof IProperty) {
				getProperties().put(elem.getId(), (IProperty) elem);
			} else if (elem instanceof IOperation) {
				getOperations().put(elem.getId(), (IOperation) elem);
			}
		}
	}

	public void addSubmodelElement(SubmodelElement elem) {
		if (elem instanceof IProperty) {
			addProperty((IProperty) elem);
		} else if (elem instanceof IOperation) {
			addOperation((IOperation) elem);
		}
		getElements().put(elem.getId(), elem);
	}

	public void addProperty(IProperty property) {
		getElements().put(property.getId(), (SubmodelElement) property);
		getProperties().put(property.getId(), property);
	}

	public void addOperation(IOperation operation) {
		getElements().put(operation.getId(), (SubmodelElement) operation);
		getOperations().put(operation.getId(), operation);
		System.out.println(get("operations") == getOperations());
		System.out.println("A: " + get("operations"));
		System.out.println("B: " + getOperations());
	}

	@Override
	public PropertyType getPropertyType() {
		return PropertyType.Container;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IProperty> getProperties() {
		return (Map<String, IProperty>) get("properties");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IOperation> getOperations() {
		return (Map<String, IOperation>) get("operations");
	}

	@Override
	public void addDataElement(DataElement element) {
		if (element instanceof IProperty) {
			addProperty((IProperty) element);
		} else {
			throw new RuntimeException("Tried to add DataElement with id " + element.getId() + " which is does not implement IProperty");
		}
	}

	@Override
	public void addOperation(Operation operation) {
		if (operation instanceof IOperation) {
			addOperation((IOperation) operation);
		} else {
			throw new RuntimeException("Tried to add Operation with id " + operation.getId() + " which is does not implement IOperation");
		}
	}

	@Override
	public void addEvent(Object event) {
		// TODO Auto-generated method stub
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
