package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.identifier;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.junit.Test;

/**
 * Tests constructor, setter and getter of {@link Identifier} for their
 * correctness
 * 
 * @author schnicke
 *
 */
public class TestIdentifier {

	@Test
	public void testConstructor() {
		IdentifierType type = IdentifierType.CUSTOM;
		String id = "testId";
		IIdentifier identifier = new Identifier(type, id);
		assertEquals(identifier.getIdType(), type);
		assertEquals(identifier.getId(), id);
	}

	@Test
	public void testSetType() {
		IdentifierType type = IdentifierType.IRI;
		Identifier identifier = new Identifier();
		identifier.setIdType(type);
		assertEquals(type, identifier.getIdType());
	}

	@Test
	public void testSetId() {
		String id = "testId";
		Identifier identifier = new Identifier();
		identifier.setId(id);
		assertEquals(id, identifier.getId());
	}
}
