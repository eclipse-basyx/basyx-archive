package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.dataspecification;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.dataspecification.DataSpecificationIEC61360;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests constructor, setter and getter of {@link DataSpecificationIEC61360} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestDataSpecificationIEC61360 {
	private static final String SHORT_NAME = "testShortName";
	private static final String UNIT = "testUnit";
	private static final String SYMBOL = "testSymbol";
	private static final String DATA_TYPE = "testDataType";
	private static final String VALUE_FORMAT = "testValueFormat";
	private static final LangStrings PREFERRED_NAME  = new LangStrings("DE", "testName");
	private static final LangStrings SOURCE_OF_DEFINITION = new LangStrings("DE", "testSource");
	private static final LangStrings DEFINITION = new LangStrings("DE", "testDefinition");
	private static final KeyElements KEY_ELEMENTS = KeyElements.ASSET;
	private static final boolean IS_LOCAL = false;
	private static final String VALUE = "testValue";
	private static final IdentifierType ID_TYPE = IdentifierType.CUSTOM;
	private static final Identifier IDENTIFIER = new Identifier(ID_TYPE, VALUE);
	private static final Reference UNIT_ID = new Reference(IDENTIFIER, KEY_ELEMENTS, IS_LOCAL);
	
	private DataSpecificationIEC61360 specification;
	
	@Before
	public void buildDataSpecification() {
		specification = new DataSpecificationIEC61360(PREFERRED_NAME, SHORT_NAME, UNIT, UNIT_ID, SOURCE_OF_DEFINITION, SYMBOL, DATA_TYPE, DEFINITION, VALUE_FORMAT);
	}
	
	@Test
	public void testConstructor() {
		assertEquals(SHORT_NAME, specification.getShortName());
		assertEquals(UNIT, specification.getUnit());
		assertEquals(SYMBOL, specification.getSymbol());
		assertEquals(DATA_TYPE, specification.getDataType());
		assertEquals(VALUE_FORMAT, specification.getValueFormat());
		assertEquals(PREFERRED_NAME, specification.getPreferredName());
		assertEquals(SOURCE_OF_DEFINITION, specification.getSourceOfDefinition());
		assertEquals(DEFINITION, specification.getDefinition());
		assertEquals(UNIT_ID, specification.getUnitId());
	}
	
	@Test
	public void testSetPreferredName() {
		LangStrings newPreferredNameString = new LangStrings("Eng", "newPreferredName");
		specification.setPreferredName(newPreferredNameString);
		assertEquals(newPreferredNameString, specification.getPreferredName());
	}
	
	@Test
	public void testSetShortName() {
		String newShortNameString = "newShortName";
		specification.setShortName(newShortNameString);
		assertEquals(newShortNameString, specification.getShortName());
	}
	
	@Test
	public void testSetUnit() {
		String newUnitString = "newUnit"; 
		specification.setUnit(newUnitString);
		assertEquals(newUnitString, specification.getUnit());
	}
	
	@Test
	public void testSetUnitId() {
		boolean isLocal = true;
		KeyElements keyElements = KeyElements.BLOB;
		String valueString = "newValue";
		IdentifierType identifierType = IdentifierType.IRI;
		Identifier identifier = new Identifier(identifierType, valueString);
		Reference unitIdReference = new Reference(identifier, keyElements, isLocal);
		specification.setUnitId(unitIdReference);
		assertEquals(unitIdReference, specification.getUnitId());
	}
	
	@Test
	public void testSetSourceOfDefinition() {
		LangStrings newSourceOfDefinition = new LangStrings("Eng", "newSourceOfDefinition");
		specification.setSourceOfDefinition(newSourceOfDefinition);
		assertEquals(newSourceOfDefinition, specification.getSourceOfDefinition());
	}
	
	@Test
	public void testSetSymbol() {
		String newSymbolString = "newSymbol";
		specification.setSymbol(newSymbolString);
		assertEquals(newSymbolString, specification.getSymbol());
	}
	
	@Test
	public void testSetDataType() {
		String newDataTypeString = "newDataType";
		specification.setDataType(newDataTypeString);
		assertEquals(newDataTypeString, specification.getDataType());
	}
	
	@Test
	public void testSetDefinition() {
		LangStrings newDefinition = new LangStrings("Eng", "newDefinition");
		specification.setDefinition(newDefinition);
		assertEquals(newDefinition, specification.getDefinition());
	}
	
	@Test
	public void testSetValueFormat() {
		String newValueFormatString = "newValueFormat";
		specification.setValueFormat(newValueFormatString);
		assertEquals(newValueFormatString, specification.getValueFormat());
	}
}
