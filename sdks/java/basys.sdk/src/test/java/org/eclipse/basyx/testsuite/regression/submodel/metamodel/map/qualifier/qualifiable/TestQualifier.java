package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.qualifier.qualifiable;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifier;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests constructor, setter and getter of {@link Qualifier} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestQualifier {
	private static final KeyElements KEY_ELEMENTS = KeyElements.ASSET;
	private static final boolean IS_LOCAL = false;
	private static final String VALUE = "testValue";
	private static final String TYPE = "testType";
	private static final String VALUE_TYPE = "anyType";
	private static final IdentifierType ID_TYPE = IdentifierType.CUSTOM;
	private static final Identifier IDENTIFIER = new Identifier(ID_TYPE, VALUE);
	private static final Reference VALUE_ID = new Reference(IDENTIFIER, KEY_ELEMENTS, IS_LOCAL);
	
	private Qualifier qualifier;
	
	@Before
	public void buildQualifier() {
		qualifier = new Qualifier(TYPE, VALUE, VALUE_TYPE, VALUE_ID);	
	}
	
	@Test
	public void testConstructor() {
		assertEquals(TYPE, qualifier.getType());
		assertEquals(VALUE, qualifier.getValue());
		assertEquals(PropertyValueTypeDefHelper.fromName(VALUE_TYPE), qualifier.getValueType());
		assertEquals(VALUE_ID, qualifier.getValueId());
	}
	
	@Test
	public void testSetType() {
		String newTypeString = "newType";
		qualifier.setType(newTypeString);
		assertEquals(newTypeString, qualifier.getType());
	}
	
	@Test
	public void testSetValue() {
		String newValueString = "newValue";
		qualifier.setValue(newValueString);
		assertEquals(newValueString, qualifier.getValue());
	}
	
	@Test
	public void testSetValueId() {
		Reference reference = new Reference(new Identifier(IdentifierType.IRI, "newId"), KeyElements.BLOB, true);
		qualifier.setValueId(reference);
		assertEquals(reference, qualifier.getValueId());
	}
	
	@Test
	public void testSetValueType() {
		PropertyValueTypeDef newValueTypeString = PropertyValueTypeDef.AnyType;
		qualifier.setValueType(newValueTypeString);
		assertEquals(newValueTypeString, qualifier.getValueType());
	}
	
	@Test
	public void testSetSemanticID() {
		Reference reference = new Reference(new Identifier(IdentifierType.IRI, "newId"), KeyElements.BLOB, true);
		qualifier.setSemanticID(reference);
		assertEquals(reference, qualifier.getSemanticId());
	}
}
