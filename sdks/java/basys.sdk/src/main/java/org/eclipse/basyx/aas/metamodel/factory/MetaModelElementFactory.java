package org.eclipse.basyx.aas.metamodel.factory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.basyx.aas.api.resources.IElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.OperationVariable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.vab.provider.lambda.VABLambdaProviderHelper;

/**
 * Creates meta model entities <br />
 * Allows passing preconfigured entities to be copied
 * 
 * @author schnicke
 *
 */
public class MetaModelElementFactory {

	/**
	 * Create Property
	 * 
	 * @param prop
	 * @param get
	 * @param set
	 * @return
	 */
	public Property create(Property prop, Object value) {
		Property ret = new Property();
		ret.putAll(prop);
		ret.setValue(value);
		return ret;
	}

	
	/**
	 * Create Property
	 * 
	 * @param prop
	 * @param get
	 * @param set
	 * @return
	 */
	public Property create(Property prop, Object value, String id) {
		Property ret = new Property();
		ret.putAll(prop);
		ret.setValue(value);
		ret.setId(id);
		return ret;
	}

	
	/**
	 * Create Property
	 * 
	 * @param prop
	 * @param get
	 * @param set
	 * @return
	 */
	public Property create(Property prop, Supplier<Object> get, Consumer<Object> set) {
		Property ret = new Property();
		ret.putAll(prop);
		Map<String, Object> value = VABLambdaProviderHelper.createSimple(get, set);
		ret.put(Property.VALUE, value);
		ret.put(Property.VALUETYPE, PropertyValueTypeDefHelper.fromObject(get.get()));
		return ret;
	}

	/**
	 * Create Operations w/o endpoint
	 * 
	 * @param operation
	 * @param function
	 * @return
	 */
	public Operation createOperation(Operation operation, Function<Object[], Object> function) {
		Operation ret = new Operation();		
		ret.putAll(operation);
		ret.put(Operation.INVOKABLE, function);
		return ret;
	}
	
	/**
	 * Create Operations
	 * 
	 * @param operation
	 * @param function
	 * @return
	 */
	public Operation createOperation(Operation operation, Function<Object[], Object> function, List<OperationVariable> in, List<OperationVariable> out) {
		Operation ret = new Operation(in, out, function);		
		ret.putAll(operation);
		ret.put(Operation.INVOKABLE, function);
		return ret;
	}

	
	/**
	 * Create SubmodelElementCollection
	 *
	 * @param container
	 * @param object
	 */
	public SubmodelElementCollection createContainer(SubmodelElementCollection property, List<SubmodelElement> properties, List<SubmodelElement> operations) {
		SubmodelElementCollection ret = new SubmodelElementCollection();
		ret.putAll(property);

		properties.stream().forEach(x -> ret.addSubmodelElement(x));
		operations.stream().forEach(x -> ret.addSubmodelElement(x));

		return ret;
	}

	
	/**
	 * Create SubmodelElementCollection
	 *
	 * @param container
	 * @param object
	 */
	public SubmodelElementCollection createContainer(SubmodelElementCollection property, List<SubmodelElement> properties, List<SubmodelElement> operations, String id) {
		SubmodelElementCollection ret = new SubmodelElementCollection();
		ret.putAll(property);
		ret.setId(id);

		properties.stream().forEach(x -> ret.addSubmodelElement(x));
		operations.stream().forEach(x -> ret.addSubmodelElement(x));

		return ret;
	}

	
	/**
	 * Create SubModel
	 * 
	 * @param subModel
	 * @param properties
	 * @param operations
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SubModel create(SubModel subModel, List<Property> properties, List<Operation> operations) {
		SubModel ret = new SubModel();
		ret.putAll(subModel);
		((Map<String, Object>) ret.get(SubModel.PROPERTIES)).putAll(createElemMap(properties));
		((Map<String, Object>) ret.get(SubModel.OPERATIONS)).putAll(createElemMap(operations));
		return ret;
	}

	/**
	 * Create AssetAdministrationShell
	 * 
	 * @param shell
	 * @param submodels
	 * @return
	 */
	public AssetAdministrationShell create(AssetAdministrationShell shell, Set<String> submodels) {
		AssetAdministrationShell ret = new AssetAdministrationShell();
		ret.putAll(shell);
		submodels.stream().forEach(s -> ret.addSubModel(s));
		return ret;
	}

	/**
	 * Creates a HashMap for all data elements in the list
	 * 
	 * @param elem
	 * @return
	 */
	private <T extends IElement> Map<String, T> createElemMap(List<T> elem) {
		Map<String, T> ret = new HashMap<>();
		for (T o : elem) {
			ret.put(o.getId(), o);
		}
		return ret;
	}
	
	
	/**
	 * Return an empty list
	 */
	public List<SubmodelElement> emptyList() {
		return new LinkedList<SubmodelElement>();
	}
	
	
	/**
	 * Return a list of properties
	 */
	public List<SubmodelElement> createList(SubmodelElement... elements) {
		// Create linked list
		List<SubmodelElement> result = new LinkedList<SubmodelElement>();
		
		// Add elements to list
		for (SubmodelElement el: elements) result.add(el);
		
		// Return list
		return result;
	}
	
	
}
