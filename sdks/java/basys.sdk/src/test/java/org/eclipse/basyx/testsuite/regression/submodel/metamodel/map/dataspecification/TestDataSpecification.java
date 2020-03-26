package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.dataspecification;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IDataSpecificationContent;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.dataspecification.DataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.dataspecification.DataSpecificationIEC61360;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.junit.Test;

/**
 * Tests constructor and getter of {@link DataSpecification} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestDataSpecification {
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
	
	@Test
	public void testConstructor() {
		IDataSpecificationContent content = new DataSpecificationIEC61360(PREFERRED_NAME, SHORT_NAME, UNIT, UNIT_ID, SOURCE_OF_DEFINITION, SYMBOL, DATA_TYPE, DEFINITION, VALUE_FORMAT);
		DataSpecification specification = new DataSpecification(content);
		assertEquals(content, specification.getContent());
	}
}
