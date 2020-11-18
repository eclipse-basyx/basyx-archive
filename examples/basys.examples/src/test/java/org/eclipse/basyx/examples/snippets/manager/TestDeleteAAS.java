package org.eclipse.basyx.examples.snippets.manager;

import static org.junit.Assert.fail;

import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.junit.Test;

/**
 * Test for the DeleteAAS snippet
 * 
 * @author conradi
 *
 */
public class TestDeleteAAS extends AbstractSnippetTest {

	@Test
	public void testDeleteAAS() {
		
		// Get the Identifier of the example AAS
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, AAS_ID);
		
		// Delete the AAS
		DeleteAAS.deleteAAS(aasIdentifier, registryComponent.getRegistryPath());
		
		// Try to retrieve deleted AAS; should throw ResourceNotFoundException
		try {
			RetrieveAAS.retrieveRemoteAAS(aasIdentifier, registryComponent.getRegistryPath());
			fail();
		} catch (ResourceNotFoundException e) {
		}
		
	}
	
}