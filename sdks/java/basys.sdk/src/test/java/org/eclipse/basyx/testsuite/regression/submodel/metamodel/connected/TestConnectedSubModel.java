package org.eclipse.basyx.testsuite.regression.submodel.metamodel.connected;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IBlob;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.relationship.IRelationshipElement;
import org.eclipse.basyx.submodel.metamodel.connected.ConnectedSubModel;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Blob;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.relationship.RelationshipElement;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.support.TypeDestroyingProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a SubModel can be created and used correctly
 *
 * @author schnicke
 *
 */
public class TestConnectedSubModel {

	// String constants used in this test case
	private final static String OP = "add";
	private final static String PROP = "prop1";
	private final static String ID = "TestId";
	
	private final String OPERATION_ID = "operation_id";
	private final String PROPERTY_ID = "property_id";
	private final String BLOB_ID = "blob_id";
	private final String RELATIONSHIP_ELEM_ID = "relElem_id";
	private final String SUBMODEL_ELEM_COLLECTION_ID = "elemCollection_id";

	private final static Reference testSemanticIdRef = new Reference(new Key(KeyElements.CONCEPTDESCRIPTION, false, "testVal", IdentifierType.CUSTOM));

	ConnectedSubModel submodel;

	@Before
	public void build() {
		// Create a simple value property
		Property propertyMeta = new Property(100);
		propertyMeta.setIdShort(PROP);

		// Create an operation
		Operation op = new Operation((Function<Object[], Object> & Serializable) obj -> {
			return (int) obj[0] + (int) obj[1];
		});
		op.setIdShort(OP);

		// Create the SubModel using the created property and operation
		SubModel sm = new SubModel();
		sm.addSubModelElement(propertyMeta);
		sm.addSubModelElement(op);
		sm.setIdShort(ID);
		sm.setSemanticId(testSemanticIdRef);

		SubModelProvider provider = new SubModelProvider(new TypeDestroyingProvider(new VABLambdaProvider(sm)));

		// Create the ConnectedSubModel based on the manager
		submodel = new ConnectedSubModel(new VABConnectionManagerStub(provider).connectToVABElement(""));
	}

	/**
	 * Tests if a SubModel's id can be retrieved correctly
	 */
	@Test
	public void getIdTest() {
		assertEquals(ID, submodel.getIdShort());
	}

	/**
	 * Tests if a SubModel's properties can be used correctly
	 */
	@Test
	public void propertiesTest() throws Exception {
		// Retrieve all properties
		Map<String, IProperty> props = submodel.getProperties();

		// Check if number of properties is as expected
		assertEquals(1, props.size());

		// Check the value of the property
		IProperty prop = props.get(PROP);
		assertEquals(100, prop.get());
	}

	/**
	 * Tests if a SubModel's operations can be used correctly
	 * 
	 * @throws Exception
	 */
	@Test
	public void operationsTest() throws Exception {
		// Retrieve all operations
		Map<String, IOperation> ops = submodel.getOperations();

		// Check if number of operations is as expected
		assertEquals(1, ops.size());

		// Check the operation itself
		IOperation op = ops.get(OP);
		assertEquals(5, op.invoke(2, 3));
	}
	
	@Test
	public void saveAndLoadPropertyTest() throws Exception {
		
		// Get sample DataElements and save them into SubModel
		Map<String, IProperty> testData = getTestDataProperty();
		for(ISubmodelElement element: testData.values()) {
			submodel.addSubModelElement(element);
		}
		
		// Load it
		Map<String, IProperty> map = submodel.getProperties();
		
		// Check if it loaded correctly
		checkProperties(map);
	}
	
	@Test
	public void saveAndLoadOperationTest() throws Exception {
		// Get sample Operations and save them into SubModel
		Map<String, IOperation> testOperations = getTestOperations();
		for(ISubmodelElement element: testOperations.values()) {
			submodel.addSubModelElement(element);
		}
		
		// Load it
		Map<String, IOperation> map = submodel.getOperations();
		
		// Check if it loaded correctly
		checkOperations(map);
	}
	
	
	@Test
	public void saveAndLoadSubmodelElementTest() throws Exception {
		
		// Get sample DataElements and save them into SubModel
		Map<String, IProperty> testDataElements = getTestDataProperty();
		for(ISubmodelElement element: testDataElements.values()) {
			submodel.addSubModelElement(element);
		}
		
		// Get sample Operations and save them into SubModel
		Map<String, IOperation> testOperations = getTestOperations();
		for(ISubmodelElement element: testOperations.values()) {
			submodel.addSubModelElement(element);
		}
		
		// Get sample SubmodelElements and save them into SubModel
		Map<String, ISubmodelElement> testSMElements = getTestSubmodelElements();
		for(ISubmodelElement element: testSMElements.values()) {
			submodel.addSubModelElement(element);
		}
		
		// Load it
		Map<String, ISubmodelElement> map = submodel.getSubmodelElements();
		
		// Check if it loaded correctly
		// Including DataElements and Operations as they are also SubmodelElements
		checkProperties(map);
		checkOperations(map);
		checkSubmodelElements(map);
	}

	/**
	 * Tests if the semantic Id can be retrieved correctly
	 */
	@Test
	public void semanticIdRetrievalTest() {
		IReference ref = submodel.getSemanticId();
		assertEquals(testSemanticIdRef, ref);
	}
	
	/**
	 * Tests if the adding a submodel element is correctly done
	 * Also checks the addition of parent reference to the submodel
	 */
	@Test
	public void addSubModelElementTest() {
		Property property = new Property("testProperty");
		property.setIdShort("testIdShort");
		submodel.addSubModelElement(property);
		
		// creates an expected reference for assertion
		Reference expected = new Reference(new Key(KeyElements.SUBMODELELEMENT, true, "", IdentifierType.IRDI));
		assertEquals(expected, property.getParent());
	} 
	
	/**
	 * Generates test IDataElements
	 */
	private Map<String, IProperty> getTestDataProperty() {
		Map<String, IProperty> ret = new HashMap<>();
		
		Property property = new Property();
		property.setIdShort(PROPERTY_ID);
		property.set("test2");
		
		ret.put(property.getIdShort(), property);
		return ret;
	}
	
	/**
	 * Generates test IOperations
	 */
	private Map<String, IOperation> getTestOperations() {
		Map<String, IOperation> ret = new HashMap<>();
		
		Operation operation = new Operation();
		operation.setIdShort(OPERATION_ID);
		ret.put(operation.getIdShort(), operation);
		
		return ret;
	}
	
	/**
	 * Generates test ISubmodelElements
	 */
	private Map<String, ISubmodelElement> getTestSubmodelElements() {
		Map<String, ISubmodelElement> ret = new HashMap<>();
		
		SubmodelElementCollection smECollection = new SubmodelElementCollection();
		smECollection.setIdShort(SUBMODEL_ELEM_COLLECTION_ID);
		
		// Create a Blob to use as Value for smECollection
		Blob blob = new Blob();
		blob.setIdShort(BLOB_ID);
		
		List<ISubmodelElement> values = new ArrayList<>();
		values.add(blob);
		
		smECollection.setValue(values);
		ret.put(smECollection.getIdShort(), smECollection);
		
		RelationshipElement relElement = new RelationshipElement();
		relElement.setIdShort(RELATIONSHIP_ELEM_ID);
		ret.put(relElement.getIdShort(), relElement);
		
		return ret;
	}
	
	
	/**
	 * Checks if the given Map contains all expected IDataElements
	 */
	private void checkProperties(Map<String, ? extends ISubmodelElement> actual) throws Exception {
		assertNotNull(actual);
		
		Map<String, IProperty> expected = getTestDataProperty();
		
		IProperty expectedProperty = expected.get(PROPERTY_ID);
		IProperty acutalProperty = (IProperty) actual.get(PROPERTY_ID);
		assertNotNull(acutalProperty);
		assertEquals(expectedProperty.get(), acutalProperty.get());
	}
	
	/**
	 * Checks if the given Map contains all expected IOperations
	 */
	private void checkOperations(Map<String, ? extends ISubmodelElement> actual) throws Exception {
		assertNotNull(actual);
		
		Map<String, IOperation> expected = getTestOperations();
		
		IOperation expectedOperation = expected.get(OPERATION_ID);
		IOperation actualOperation = (IOperation) actual.get(OPERATION_ID);
		
		assertNotNull(actualOperation);
		assertEquals(expectedOperation.getIdShort(), actualOperation.getIdShort());
	}
	
	/**
	 * Checks if the given Map contains all expected ISubmodelElements
	 */
	private void checkSubmodelElements(Map<String, ISubmodelElement> actual) throws Exception {
		assertNotNull(actual);
		
		Map<String, ISubmodelElement> expected = getTestSubmodelElements();
		
		ISubmodelElementCollection expectedCollection = 
				(ISubmodelElementCollection) expected.get(SUBMODEL_ELEM_COLLECTION_ID);
		ISubmodelElementCollection actualCollection =
				(ISubmodelElementCollection) actual.get(SUBMODEL_ELEM_COLLECTION_ID);
		
		assertNotNull(actualCollection);
		
		Collection<ISubmodelElement> elements = actualCollection.getValue();
		
		// Check for correct Type
		for (ISubmodelElement iSubmodelElement: elements) {
			assertTrue(iSubmodelElement instanceof IBlob);
		}
		
		assertEquals(expectedCollection.getValue().size(), elements.size());
		
		IRelationshipElement expectedRelElem = (IRelationshipElement) expected.get(RELATIONSHIP_ELEM_ID);
		IRelationshipElement actualRelElem = (IRelationshipElement) actual.get(RELATIONSHIP_ELEM_ID);
		
		assertNotNull(actualRelElem);
		assertEquals(expectedRelElem.getIdShort(), actualRelElem.getIdShort());
	}
	
}
