package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.submodelelement.dataelement.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Collections;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.parts.ConceptDescription;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Formula;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests constructor, getter and setter of {@link Property} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestProperty {
	private static final String VALUE = "testValue";
	private static final String STRING_TYPE = "string";
	private Property property;

	@Before
	public void buildFile() {
		property = new Property(VALUE);
	}
	
	@Test
	public void testConstructor1() {
		assertEquals(VALUE, property.get());
		assertNull(property.getValueId());
		assertEquals(STRING_TYPE, property.getValueType());
	} 
	
	@Test
	public void testConstructor2() {
		Referable referable = new Referable("testIdShort", "testCategory", new LangStrings("DE", "test"));
		Reference semanticId = new Reference(new Key(KeyElements.ASSET, true, "testValue", IdentifierType.IRI));
		Qualifiable qualifiable = new Qualifiable(new Formula(Collections.singleton(new Reference(new Key(KeyElements.BLOB, true, "TestValue", IdentifierType.IRI)))));
		Property property = new Property(VALUE, referable, semanticId, qualifiable);
		assertEquals(VALUE, property.get());
		assertNull(property.getValueId());
		assertEquals(STRING_TYPE, property.getValueType());
	} 
	
	@Test
	public void testSetValueType() {
		property.setValueType(PropertyValueTypeDef.String);
		assertEquals(STRING_TYPE, property.getValueType());
	} 
	
	@Test
	public void testGetNonMapValueType() {
		property.put(Property.VALUETYPE, "string");
		assertEquals("string", property.getValueType());
	}
	
	@Test
	public void testGetNotExistingValueType() {
		// I would vote for fail fast - directly when setting the value
		property.put(Property.VALUETYPE, "IDoNotExistInPropertyValueTypeDef");
		try {
			property.getValueType();
			fail("Expecting exception when providing invalid type");
		} catch (RuntimeException e) {
		}
	}
	
	@Test
	public void testSet() {
		Boolean isSomething = true;
		property.set(isSomething);
		assertEquals(isSomething, property.get());
		assertEquals("boolean", property.getValueType());
	}

	@Test
	public void testSetCustom() {
		property.set(null, PropertyValueTypeDef.String);
		assertEquals(null, property.get());
		assertEquals(PropertyValueTypeDef.String.getStandardizedLiteral(), property.getValueType());
	}

	@Test
	public void testSetId() {
		IReference ref = new Reference(new Key(KeyElements.PROPERTY, true, "custom", IdentifierType.CUSTOM));
		IReference ref2 = new Reference(new Key(KeyElements.PROPERTY, true, "custom", IdentifierType.CUSTOM));
		property.setValueId(ref);
		assertEquals(ref2, property.getValueId());
	}

	@Test
	public void testAddConceptDescription() {
		ConceptDescription description = new ConceptDescription();
		Property property = new Property(VALUE);
		property.addConceptDescription(description);
		assertEquals(new Reference(description, KeyElements.CONCEPTDESCRIPTION, true), property.getSemanticId());
	} 
}
