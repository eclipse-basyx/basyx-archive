package org.eclipse.basyx.examples.snippets.submodel;

import static org.junit.Assert.fail;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.examples.support.ExampleComponentBuilder;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.junit.Test;

/**
 * Test for the DeleteSubmodelElement snippet
 * 
 * @author conradi
 *
 */
public class TestDeleteSubmodelElement extends AbstractSnippetTest {

	@Test
	public void testDeleteSubmodelElement() {
		
		// Get the Identifier of the example AAS and Submodel
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, AAS_ID);
		IIdentifier smIdentifier = new Identifier(IdentifierType.CUSTOM, SM_ID);
		
		// Delete the SubmodelElement
		DeleteSubmodelElement.deleteSubmodelElement(ExampleComponentBuilder.PROPERTY_ID, smIdentifier, aasIdentifier, registryComponent.getRegistryPath());
		
		// Create a proxy pointing to the registry server
		AASRegistryProxy registryProxy = new AASRegistryProxy(registryComponent.getRegistryPath());
		
		// Create a ConnectedAASManager using the registryProxy as its registry
		ConnectedAssetAdministrationShellManager manager =
				new ConnectedAssetAdministrationShellManager(registryProxy);
		
		// Retrieve the Submodel from the server as a ConnectedSubmodel
		ISubModel submodel = manager.retrieveSubModel(aasIdentifier, smIdentifier);
		
		// Try to retrieve deleted SubmodelElement; should throw ResourceNotFoundException
		try {
			submodel.getSubmodelElement(ExampleComponentBuilder.PROPERTY_ID);
			fail();
		} catch (ResourceNotFoundException e) {
		}
		
	}
	
}