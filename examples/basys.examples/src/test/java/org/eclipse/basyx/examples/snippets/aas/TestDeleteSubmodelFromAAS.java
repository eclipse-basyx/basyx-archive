package org.eclipse.basyx.examples.snippets.aas;

import static org.junit.Assert.fail;

import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.junit.Test;

/**
 * Test for the DeleteSubmodelFromAAS snippet
 * 
 * @author conradi
 *
 */
public class TestDeleteSubmodelFromAAS extends AbstractSnippetTest {

	@Test
	public void testDeleteSubmodel() {
		
		// Get the Identifier of the example AAS and Submodel
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, AAS_ID);
		IIdentifier smIdentifier = new Identifier(IdentifierType.CUSTOM, SM_ID);
		
		// Delete the Submodel
		DeleteSubmodelFromAAS.deleteSubmodelFromAAS(smIdentifier, aasIdentifier, registryComponent.getRegistryPath());
		
		// Try to retrieve deleted Submodel; should throw ResourceNotFoundException
		try {
			RetrieveSubmodelFromAAS.retrieveSubmodelFromAAS(
					smIdentifier, aasIdentifier, registryComponent.getRegistryPath());
			fail();
		} catch (ResourceNotFoundException e) {
		}
		
	}
	
}