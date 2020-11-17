package org.eclipse.basyx.examples.snippets.submodel;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.examples.support.ExampleComponentBuilder;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.junit.Test;

/**
 * Test for the RetrieveSubmodelElement snippet
 * 
 * @author conradi
 *
 */
public class TestRetrieveSubmodelElement extends AbstractSnippetTest {

	
	@Test
	public void testRetrieveSubmodelElement() {
		
		// Get the Identifiers of the AAS and Submodel
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, AAS_ID);
		IIdentifier smIdentifier = new Identifier(IdentifierType.CUSTOM, SM_ID);
		
		// Get the idShort and value of the element to be retrieved
		String elementId = ExampleComponentBuilder.PROPERTY_ID;
		int elementValue = ExampleComponentBuilder.PROPERTY_VALUE;
		
		// Get the SubmodelElement
		ISubmodelElement element = RetrieveSubmodelElement.retrieveSubmodelElement(
				elementId, smIdentifier, aasIdentifier, registryComponent.getRegistryPath());
		
		// Check if the SubmodelElement contains the expected value
		assertEquals(elementValue, element.getValue());
	}
	
}