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
import org.junit.Test;

/**
 * Ensures correct behavior of {@link SubModel}
 * 
 * @author haque
 *
 */
public class TestSubmodel {
	
	@Test
	public void testAddSubmodelElement() {
		String submodelId = "submodelID";
		String identifierId = "testId";
		String propId = "propertyID";
		
		HasSemantics semantics = new HasSemantics(new Reference(new Key(KeyElements.ASSET, true, "testValue", IdentifierType.IRDI)));
		Identifiable identifiable = new Identifiable("1", "5", submodelId, "testCategory", new LangStrings("DE", "test"), IdentifierType.IRDI, identifierId);
		Qualifiable qualifiable = new Qualifiable(new Formula(Collections.singleton(new Reference(new Key(KeyElements.BLOB, true, "TestValue", IdentifierType.IRI)))));
		HasDataSpecification specification = new HasDataSpecification(new ArrayList<>(), Collections.singleton(new Reference(new Key(KeyElements.BLOB, true, "testRef", IdentifierType.IRI))));
		HasKind hasKind = new HasKind(ModelingKind.INSTANCE);
		
		// Create a submodel 
		SubModel subModel = new SubModel(semantics, identifiable, qualifiable, specification, hasKind);
		
		//Create a submodel element and set an id to it
		Property property = new Property("testValue");
		property.setIdShort(propId);
		
		// Add the element to the submodel
		subModel.addSubModelElement(property);
		
		// Create expected map of added submodel element for assertion
		Map<String, ISubmodelElement> submodelElemMap = new HashMap<String, ISubmodelElement>();
		submodelElemMap.put(propId, property);
		assertEquals(submodelElemMap, subModel.getSubmodelElements());
		
		// Create expected parent of the element for assertion
		Reference expectedParent = new Reference(new Key(KeyElements.SUBMODEL, true, identifierId, IdentifierType.IRDI));
		assertEquals(expectedParent, property.getParent());
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
}
