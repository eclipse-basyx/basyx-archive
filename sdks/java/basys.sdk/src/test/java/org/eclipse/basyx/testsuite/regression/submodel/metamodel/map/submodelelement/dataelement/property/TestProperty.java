package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.submodelelement.dataelement.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collections;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
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
	
	@Test
	public void testConstructor1() {
		Property property = new Property(VALUE);
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
		Property property = new Property(VALUE);
		property.setValueType(PropertyValueTypeDef.String);
		assertEquals(STRING_TYPE, property.getValueType());
	} 
	
	@Test
	public void testGetValueTypeNull() {
		Property property = new Property(VALUE);
		property.put(Property.VALUETYPE, "NonMapType");
		assertEquals("", property.getValueType());
	}
	
	@Test
	public void testSet() {
		Boolean isSomething = true;
		Property property = new Property(VALUE);
		property.set(isSomething);
		assertEquals(isSomething, property.get());
		assertEquals("boolean", property.getValueType());
	} 
	
	@Test
	public void testAddConceptDescription() {
		ConceptDescription description = new ConceptDescription();
		Property property = new Property(VALUE);
		property.addConceptDescription(description);
		assertEquals(new Reference(description, KeyElements.CONCEPTDESCRIPTION, true), property.getSemanticId());
	} 
}
