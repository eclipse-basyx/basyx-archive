package org.eclipse.basyx.examples.snippets.registry;

import static org.junit.Assert.fail;

import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.junit.Test;

/**
 * Test for the DeregisterSubmodel Snippet
 * 
 * @author conradi
 *
 */
public class TestDeregisterSubmodel extends AbstractSnippetTest {

	@Test
	public void testDeregisterSubmodel() {
		
		// Get the Identifier of the example AAS
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, AAS_ID);
		IIdentifier smIdentifier = new Identifier(IdentifierType.CUSTOM, SM_ID);
		
		DeregisterSubmodel.registerSubmodel(smIdentifier, aasIdentifier, registryComponent.getRegistryPath());
		
		// Lookup the AAS in the registry
		AASRegistryProxy registry = new AASRegistryProxy(registryComponent.getRegistryPath());
		
		try {
			registry.lookupSubmodel(aasIdentifier, smIdentifier);
			fail();
		} catch (ResourceNotFoundException e) {
		}	
	}
	
}
