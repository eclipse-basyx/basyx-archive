package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.submodelelement.dataelement.property;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Formula;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.DataElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.ContainerProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests constructor, getter and setter of {@link ContainerProperty} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestContainerProperty {
	private static final String OPERATION_ID = "testOpID";
	private static final String PROPERTY_ID = "testPropID";
	private static final String ID_SHORT = "testIdShort";
	
	private List<SubmodelElement> properties;
	private List<SubmodelElement> operations;
	
	@Before
	public void initialize() {
		properties = Collections.singletonList(getDataElement());
		operations = Collections.singletonList(getOperation());
	}
	
	@Test
	public void testConstructor1() {
		ContainerProperty property = new ContainerProperty(new ContainerProperty(), properties, operations);
		testCommonConstructorPart(property);
	} 
	
	@Test
	public void testConstructor2() {
		ContainerProperty property = new ContainerProperty(new ContainerProperty(), properties, operations, ID_SHORT);
		testCommonConstructorPart(property);
		assertEquals(ID_SHORT, property.getIdShort());
	}
	
	@Test
	public void testAddSubModelElement() {
		ContainerProperty containerProperty = new ContainerProperty(new ContainerProperty(), properties, operations);
		Property property = new Property("testValue");
		String newIdShort = "newIdShort"; 
		property.put(Referable.IDSHORT, newIdShort);
		containerProperty.addSubModelElement(property);
		Map<String, ISubmodelElement> submodelElements = new HashMap<String, ISubmodelElement>();
		submodelElements.put(PROPERTY_ID, getDataElement());
		submodelElements.put(OPERATION_ID, getOperation());
		submodelElements.put(newIdShort, property);
		assertEquals(submodelElements, containerProperty.getSubmodelElements());
	}
	
	/**
	 * Tests common part of both constructor test cases
	 */
	private void testCommonConstructorPart(ContainerProperty property) {
		Map<String, IDataElement> dataElements = new HashMap<String, IDataElement>();
		Map<String, IOperation> operations = new HashMap<String, IOperation>();
		Map<String, ISubmodelElement> submodels = new HashMap<String, ISubmodelElement>();
		dataElements.put(PROPERTY_ID, getDataElement());
		operations.put(OPERATION_ID, getOperation());
		submodels.putAll(operations);
		submodels.putAll(dataElements);
		assertEquals(dataElements, property.getDataElements());
		assertEquals(operations, property.getOperations());
		assertEquals(submodels, property.getSubmodelElements());
	} 
	
	/**
	 * Get a dummy data element 
	 * @return data element 
	 */
	private DataElement getDataElement() {
		Referable referable = new Referable(PROPERTY_ID, "testCategory", new LangStrings("DE", "test"));
		Reference semanticId = new Reference(new Key(KeyElements.ASSET, true, "testValue", IdentifierType.IRI));
		Qualifiable qualifiable = new Qualifiable(new Formula(Collections.singleton(new Reference(new Key(KeyElements.BLOB, true, "TestValue", IdentifierType.IRI)))));
		Property property = new Property("testValue", referable, semanticId, qualifiable);
		return property;
	}
	
	/**
	 * Get a dummy operation
	 * @return operation
	 */
	private Operation getOperation() {
		List<OperationVariable> variable = Collections.singletonList(new OperationVariable(new Property("testOpVariableId")));
		Operation operation = new Operation(variable, variable, variable, null);
		operation.put(Referable.IDSHORT, OPERATION_ID);
		return operation;
	}
}
