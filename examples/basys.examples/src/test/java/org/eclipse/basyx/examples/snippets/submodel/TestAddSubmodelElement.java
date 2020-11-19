package org.eclipse.basyx.examples.snippets.submodel;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.examples.snippets.AbstractSnippetTest;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.junit.Test;

/**
 * Test for the AddSubmodelElement snippet
 * 
 * @author conradi
 *
 */
public class TestAddSubmodelElement extends AbstractSnippetTest {

	private static final String NEW_PROPERTY_ID = "new_prop";
	private static final int NEW_PROPERTY_VALUE = 321;
	
	@Test
	public void testAddSubmodelElement() {
		
		// Get the Identifiers of the AAS and Submodel
		IIdentifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, AAS_ID);
		IIdentifier smIdentifier = new Identifier(IdentifierType.CUSTOM, SM_ID);
		
		// Build a new SubmodelElement
		Property newProperty = new Property();
		newProperty.setIdShort(NEW_PROPERTY_ID);
		newProperty.setValue(NEW_PROPERTY_VALUE);
		
		// Add the new Element to the Submodel
		AddSubmodelElement.addSubmodelElement(newProperty, smIdentifier, aasIdentifier, registryComponent.getRegistryPath());
		
		// Get the Element from the server
		ConnectedAssetAdministrationShellManager manager = getManager();
		ISubModel remoteSubmodel = manager.retrieveSubModel(aasIdentifier, smIdentifier);
		ISubmodelElement remoteElement = remoteSubmodel.getSubmodelElement(NEW_PROPERTY_ID);
		
		// Check if its value is as expected
		assertEquals(NEW_PROPERTY_VALUE, remoteElement.getValue());
	}
	
}
