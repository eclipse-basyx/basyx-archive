/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.dataspecification;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IValueReferencePair;
import org.eclipse.basyx.submodel.metamodel.api.dataspecification.enums.DataTypeIEC61360;
import org.eclipse.basyx.submodel.metamodel.api.dataspecification.enums.LevelType;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.dataspecification.DataSpecificationIEC61360;
import org.eclipse.basyx.submodel.metamodel.map.dataspecification.DataSpecificationIEC61360Content;
import org.eclipse.basyx.submodel.metamodel.map.dataspecification.ValueReferencePair;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.vab.support.TypeDestroyer;
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
	private static final LangStrings SHORT_NAME = new LangStrings("DE", "testShortName");
	private static final String UNIT = "testUnit";
	private static final String SYMBOL = "testSymbol";
	private static final DataTypeIEC61360 DATA_TYPE = DataTypeIEC61360.REAL_MEASURE;
	private static final String VALUE_FORMAT = "testValueFormat";
	private static final LangStrings PREFERRED_NAME  = new LangStrings("DE", "testName");
	private static final String SOURCE_OF_DEFINITION = "testSource";
	private static final LangStrings DEFINITION = new LangStrings("DE", "testDefinition");
	private static final KeyElements KEY_ELEMENTS = KeyElements.ASSET;
	private static final String VALUE = "testValue";
	private static final IdentifierType ID_TYPE = IdentifierType.CUSTOM;
	private static final Identifier IDENTIFIER = new Identifier(ID_TYPE, VALUE);
	private static final Reference UNIT_ID = new Reference(IDENTIFIER, KEY_ELEMENTS);
	private static final Reference VALUE_ID = new Reference(IDENTIFIER, KEY_ELEMENTS);
	private static final IValueReferencePair VALUEREFPAIR = new ValueReferencePair(VALUE, VALUE_ID);
	private static final LevelType LEVELTYPE = LevelType.TYP;
	
	private DataSpecificationIEC61360Content specification;
	
	@Before
	public void buildDataSpecification() {
		specification = new DataSpecificationIEC61360Content(PREFERRED_NAME, SHORT_NAME,
				UNIT, UNIT_ID, SOURCE_OF_DEFINITION, SYMBOL, DATA_TYPE, DEFINITION, VALUE_FORMAT,
				Arrays.asList(VALUEREFPAIR), VALUE, VALUE_ID, LEVELTYPE);
	}
	
	@Test
	public void testConstructor() {
		assertEquals(PREFERRED_NAME, specification.getPreferredName());
		assertEquals(SHORT_NAME, specification.getShortName());
		assertEquals(UNIT, specification.getUnit());
		assertEquals(UNIT_ID, specification.getUnitId());
		assertEquals(SOURCE_OF_DEFINITION, specification.getSourceOfDefinition());
		assertEquals(SYMBOL, specification.getSymbol());
		assertEquals(DATA_TYPE, specification.getDataType());
		assertEquals(DEFINITION, specification.getDefinition());
		assertEquals(VALUE_FORMAT, specification.getValueFormat());
		assertEquals(VALUEREFPAIR, specification.getValueList().iterator().next());
		assertEquals(VALUE, specification.getValue());
		assertEquals(VALUE_ID, specification.getValueId());
		assertEquals(LEVELTYPE, specification.getLevelType());

	}
	
	@Test
	public void testSetPreferredName() {
		LangStrings newPreferredNameString = new LangStrings("EN", "newPreferredName");
		specification.setPreferredName(newPreferredNameString);
		TypeDestroyer.destroyType(specification);
		assertEquals(newPreferredNameString, specification.getPreferredName());
	}
	
	@Test
	public void testSetShortName() {
		LangStrings newShortNameString = new LangStrings("DE", "newShortName");
		specification.setShortName(newShortNameString);
		TypeDestroyer.destroyType(specification);
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
		KeyElements keyElements = KeyElements.BLOB;
		String valueString = "newValue";
		IdentifierType identifierType = IdentifierType.IRI;
		Identifier identifier = new Identifier(identifierType, valueString);
		Reference unitIdReference = new Reference(identifier, keyElements);
		specification.setUnitId(unitIdReference);
		TypeDestroyer.destroyType(specification);
		assertEquals(unitIdReference, specification.getUnitId());
	}
	
	@Test
	public void testSetSourceOfDefinition() {
		String newSourceOfDefinition = "newSourceOfDefinition";
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
		DataTypeIEC61360 newDataTypeString = DataTypeIEC61360.BOOLEAN;
		specification.setDataType(newDataTypeString);
		assertEquals(newDataTypeString, specification.getDataType());
	}
	
	@Test
	public void testSetDefinition() {
		LangStrings newDefinition = new LangStrings("EN", "newDefinition");
		specification.setDefinition(newDefinition);
		TypeDestroyer.destroyType(specification);
		assertEquals(newDefinition, specification.getDefinition());
	}
	
	@Test
	public void testSetValueFormat() {
		String newValueFormatString = "newValueFormat";
		specification.setValueFormat(newValueFormatString);
		assertEquals(newValueFormatString, specification.getValueFormat());
	}

	@Test
	public void testSetValue() {
		String newValueString = "newValue";
		specification.setValue(newValueString);
		assertEquals(newValueString, specification.getValue());
	}

	@Test
	public void testSetValueId() {
		KeyElements keyElements = KeyElements.BLOB;
		String valueString = "newValueId";
		IdentifierType identifierType = IdentifierType.IRI;
		Identifier identifier = new Identifier(identifierType, valueString);
		Reference valueIdReference = new Reference(identifier, keyElements);
		specification.setValueId(valueIdReference);
		TypeDestroyer.destroyType(specification);
		assertEquals(valueIdReference, specification.getValueId());
	}

	@Test
	public void testSetValuePair() {
		KeyElements keyElements = KeyElements.PROPERTY;
		String valueString = "newValueIdPair";
		IdentifierType identifierType = IdentifierType.CUSTOM;
		Identifier identifier = new Identifier(identifierType, valueString);
		Reference valueIdReference = new Reference(identifier, keyElements);
		String newValueString = "newValuePair";
		ValueReferencePair newPair = new ValueReferencePair(newValueString, valueIdReference);
		specification.setValueList(Arrays.asList(newPair));
		TypeDestroyer.destroyType(specification);
		assertEquals(newPair, specification.getValueList().iterator().next());
	}

	@Test
	public void testLevelType() {
		LevelType newLevelType = LevelType.NOM;
		specification.setLevelType(newLevelType);
		assertEquals(newLevelType, specification.getLevelType());
	}
}
