package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.submodelelement.dataelement.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
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
	private static final PropertyValueTypeDef STRING_TYPE = PropertyValueTypeDef.String;
	private Property property;

	@Before
	public void buildFile() {
		property = new Property(VALUE);
	}
	
	@Test
	public void testConstructor1(){
		assertEquals(VALUE, property.get());
		assertNull(property.getValueId());
		assertEquals(STRING_TYPE, property.getValueType());
	} 
	
	@Test
	public void testConstructor2(){
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
	public void testSet(){
		Property booleanProp = new Property();
		Boolean isSomething = true;
		booleanProp.set(isSomething);
		assertEquals(isSomething, booleanProp.get());
		assertEquals(isSomething, booleanProp.getValue());
		assertEquals(PropertyValueTypeDef.Boolean, booleanProp.getValueType());

		Byte byteNumber = new Byte("2");
		Property byteProp = new Property();
		byteProp.set(byteNumber);
		assertEquals(byteNumber, byteProp.get());
		assertEquals(PropertyValueTypeDef.Int8, byteProp.getValueType());
		
		Duration duration = Duration.ofSeconds(10);
		Property durationProp = new Property();
		durationProp.set(duration);
		assertEquals(duration, durationProp.get());
		assertEquals(PropertyValueTypeDef.Duration, durationProp.getValueType());

		Property periodProp = new Property();
		LocalDate today = LocalDate.now();
		LocalDate birthday = LocalDate.of(1960, Month.JANUARY, 1);
		Period p = Period.between(birthday, today);
		periodProp.set(p);
		assertEquals(p, periodProp.get());
		assertEquals(PropertyValueTypeDef.YearMonthDuration, periodProp.getValueType());

		Property bigNumberProp = new Property();
		BigInteger bignumber = new BigInteger("9223372036854775817");
		bigNumberProp.set(bignumber);
		assertEquals(bignumber, bigNumberProp.get());
		assertEquals(PropertyValueTypeDef.PositiveInteger, bigNumberProp.getValueType());
	}

	@Test
	public void testSetCustom(){
		property.set(null, PropertyValueTypeDef.String);
		assertEquals(null, property.get());
		assertEquals(PropertyValueTypeDef.String, property.getValueType());
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
	
	@Test
	public void testInitializeWithNullValue() {
		try {
			// Should not work as valueType is a mandatory attribute
			new Property("id", null);
			fail();
		} catch (RuntimeException e) {
		}
		
		try {
			// Should not work as valueType can not be set with null as value
			Property prop = new Property();
			prop.setValue(null);
			fail();
		} catch (RuntimeException e) {
		}
		
		Property prop = new Property("id", "value");
		// This should work as the valueType is already set
		prop.setValue(null);
	}
}
