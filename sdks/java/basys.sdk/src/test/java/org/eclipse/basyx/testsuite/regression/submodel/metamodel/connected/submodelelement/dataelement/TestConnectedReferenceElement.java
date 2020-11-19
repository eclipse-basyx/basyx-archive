package org.eclipse.basyx.testsuite.regression.submodel.metamodel.connected.submodelelement.dataelement;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.submodel.restapi.SubmodelElementProvider;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.support.TypeDestroyingProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a ConnectedReferenceElement can be created and used correctly
 * 
 * @author conradi
 *
 */
public class TestConnectedReferenceElement {
	
	ConnectedReferenceElement connectedRefElem;
	ReferenceElement refElem;
	
	@Before
	public void build() {
		
		Reference ref = new Reference(new Key(KeyElements.BLOB, true, "", IdentifierType.CUSTOM));
		
		refElem = new ReferenceElement(ref);
		
		VABConnectionManagerStub manager = new VABConnectionManagerStub(
				new SubmodelElementProvider(new TypeDestroyingProvider(new VABLambdaProvider(refElem))));

		connectedRefElem = new ConnectedReferenceElement(manager.connectToVABElement(""));
	}
	
	/**
	 * Tests if getValue() returns the correct value
	 */
	@Test
	public void testGetValue() {
		assertEquals(refElem.getValue(), connectedRefElem.getValue());
	}
}