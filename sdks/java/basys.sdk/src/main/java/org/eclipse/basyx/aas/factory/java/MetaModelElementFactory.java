package org.eclipse.basyx.aas.factory.java;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.ContainerProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;

/**
 * Creates meta model entities <br />
 * Allows passing preconfigured entities to be copied
 * 
 * @author schnicke
 *
 */
public class MetaModelElementFactory {

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
	public ContainerProperty createContainer(ContainerProperty property,
			List<SubmodelElement> properties, List<SubmodelElement> operations) {
		ContainerProperty ret = new ContainerProperty();
		ret.putAll(property);
		properties.stream().forEach(ret::addSubModelElement);
		operations.stream().forEach(ret::addSubModelElement);
		return ret;
	}

	/**
	 * Create SubmodelElementCollection
	 *
	 * @param container
	 * @param object
	 */
	public ContainerProperty createContainer(ContainerProperty property,
			List<SubmodelElement> properties, List<SubmodelElement> operations, String id) {
		ContainerProperty ret = createContainer(property, properties, operations);
		ret.setIdShort(id);
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
	public SubModel create(SubModel subModel, List<SingleProperty> properties, List<Operation> operations) {
		SubModel ret = new SubModel();
		ret.putAll(subModel);
		properties.stream().forEach(e -> ret.addSubModelElement(e));
		operations.stream().forEach(e -> ret.addSubModelElement(e));
		return ret;
	}

	/**
	 * Create AssetAdministrationShell
	 * 
	 * @param shell
	 * @param submodels
	 * @return
	 */
	public AssetAdministrationShell create(AssetAdministrationShell shell, Set<SubmodelDescriptor> submodels) {
		AssetAdministrationShell ret = new AssetAdministrationShell();
		ret.putAll(shell);
		submodels.stream().forEach(s -> ret.addSubModel(s));
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
		for (SubmodelElement el : elements)
			result.add(el);

		// Return list
		return result;
	}

}
