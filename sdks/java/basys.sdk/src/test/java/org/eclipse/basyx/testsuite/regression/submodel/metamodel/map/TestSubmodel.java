package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Formula;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;

/**
 * Ensures correct behavior of {@link SubModel}
 * 
 * @author haque
 *
 */
public class TestSubmodel {
	public static String SUBMODEL_ID = "submodelID";
	public static String IDENTIFIER_ID = "testId";
	public static String PROP_ID = "propertyID";
	public static Property PROPERTY = new Property("testValue");
	
	private SubModel subModel;
	
	@Before
	public void init() {
		HasSemantics semantics = new HasSemantics(new Reference(new Key(KeyElements.ASSET, true, "testValue", IdentifierType.IRDI)));
		Identifiable identifiable = new Identifiable("1", "5", SUBMODEL_ID, "testCategory", new LangStrings("DE", "test"), IdentifierType.IRDI, IDENTIFIER_ID);
		Qualifiable qualifiable = new Qualifiable(new Formula(Collections.singleton(new Reference(new Key(KeyElements.BLOB, true, "TestValue", IdentifierType.IRI)))));
		HasDataSpecification specification = new HasDataSpecification(new ArrayList<>(), Collections.singleton(new Reference(new Key(KeyElements.BLOB, true, "testRef", IdentifierType.IRI))));
		HasKind hasKind = new HasKind(ModelingKind.INSTANCE);
		
		// Create a submodel 
		subModel = new SubModel(semantics, identifiable, qualifiable, specification, hasKind);
		
		//set an id to the submodel element
		PROPERTY.setIdShort(PROP_ID);
		
		// Add the element to the submodel
		subModel.addSubModelElement(PROPERTY);
	}
	
	@Test
	public void testAddSubmodelElement() {
		// Create expected map of added submodel element for assertion
		Map<String, ISubmodelElement> submodelElemMap = new HashMap<String, ISubmodelElement>();
		submodelElemMap.put(PROP_ID, PROPERTY);
		assertEquals(submodelElemMap, subModel.getSubmodelElements());
		
		// Create expected parent of the element for assertion
		Reference expectedParent = new Reference(new Key(KeyElements.SUBMODEL, true, IDENTIFIER_ID, IdentifierType.IRDI));
		assertEquals(expectedParent, PROPERTY.getParent());
	} 

	/**
	 * Tests if a SubModel containing a list for SUBMODELELEMENT is correctly
	 * handled by the facading submodel. This is necessary because the submodel
	 * serialization does specify SUBMODELELEMENT as list
	 */
	@Test
	public void testCreateAsFacadePropertyList() {
		// Create test property
		String propId = "testProp";
		
		Property expected = new Property(5);
		expected.setIdShort(propId);

		// Create test submodel and force key SUBMODELELEMENT to contain a list
		SubModel sm = new SubModel();
		sm.put(SubModel.SUBMODELELEMENT, Collections.singleton(expected));

		// Check if the facade converts the SUBMODELELEMENT value correctly
		SubModel facade = SubModel.createAsFacade(sm);
		assertTrue(facade.get(SubModel.SUBMODELELEMENT) instanceof Map<?, ?>);
		assertEquals(expected, facade.getSubmodelElements().get(propId));
	}
	
	/**
	 * This method tests getSubmodelElement method of SubModel class
	 * with existing element id
	 */
	@Test
	public void testGetSubmodelElement() {
		ISubmodelElement element = subModel.getSubmodelElement(PROP_ID);
		assertEquals(PROPERTY, element);
	}
	
	/**
	 * This method tests getSubmodelElement method of SubModel class
	 * with non existing element id
	 */
	@Test(expected = ResourceNotFoundException.class)
	public void testGetSubModelElementNotExist() {
		subModel.getSubmodelElement("Id_Which_Does_Not_Exist");
	}
	
	/**
	 * This method tests deleteSubmodelElement method of SubModel class
	 * with existing element id
	 */
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteSubmodelElement() {
		subModel.deleteSubmodelElement(PROP_ID);
		subModel.getSubmodelElement(PROP_ID);
	}
	
	/**
	 * This method tests deleteSubmodelElement method of SubModel class
	 * with non existing element id
	 */
	@Test(expected = ResourceNotFoundException.class)
	public void testDeleteSubModelElementNotExist() {
		subModel.deleteSubmodelElement("Id_Which_Does_Not_Exist");
	}
}
