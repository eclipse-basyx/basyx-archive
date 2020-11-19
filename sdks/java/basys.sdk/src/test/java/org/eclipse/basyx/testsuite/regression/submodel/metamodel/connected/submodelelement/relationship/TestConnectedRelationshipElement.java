package org.eclipse.basyx.testsuite.regression.submodel.metamodel.connected.submodelelement.relationship;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.relationship.ConnectedRelationshipElement;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.relationship.RelationshipElement;
import org.eclipse.basyx.submodel.restapi.SubmodelElementProvider;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.support.TypeDestroyingProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a ConnectedRelationshipElement can be created and used correctly
 * 
 * @author conradi
 *
 */
public class TestConnectedRelationshipElement {
	
	ConnectedRelationshipElement connectedRelElem;
	RelationshipElement relElem;
	
	@Before
	public void build() {
		
		Reference ref1 = new Reference(new Key(KeyElements.BLOB, true, "1", IdentifierType.CUSTOM));
		Reference ref2 = new Reference(new Key(KeyElements.FILE, false, "2", IdentifierType.IRDI));
		
		relElem = new RelationshipElement(ref1, ref2);
		
		
		VABConnectionManagerStub manager = new VABConnectionManagerStub(
				new SubmodelElementProvider(new TypeDestroyingProvider(new VABLambdaProvider(relElem))));

		connectedRelElem = new ConnectedRelationshipElement(manager.connectToVABElement(""));
	}

	/**
	 * Tests if getFirst() returns the correct value
	 */
	@Test
	public void testGetFirst() {
		assertEquals(relElem.getFirst(), connectedRelElem.getFirst());
	}

	/**
	 * Tests if getSecond() returns the correct value
	 */
	@Test
	public void testGetSecond() {
		assertEquals(relElem.getSecond(), connectedRelElem.getSecond());
	}
}
