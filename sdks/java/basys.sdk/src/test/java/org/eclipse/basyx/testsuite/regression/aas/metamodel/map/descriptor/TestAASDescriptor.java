package org.eclipse.basyx.testsuite.regression.aas.metamodel.map.descriptor;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.junit.Test;

/**
 * Tests {@link AASDescriptor}
 * 
 * @author schnicke
 *
 */
public class TestAASDescriptor {

	/**
	 * Tests retrieval of all registered submodel descriptors
	 */
	@Test
	public void testGetAllSubModels() {
		// Setup descriptor and add one submodel descriptor
		AASDescriptor descriptor = new AASDescriptor(new Identifier(IdentifierType.CUSTOM, "Test"), "http://a.b/c/aas");
		descriptor.addSubmodelDescriptor(new SubmodelDescriptor("SM1", new Identifier(IdentifierType.CUSTOM, "SM1"), "http://a.b/c/aas/submodels/SM1"));

		// Assert correct retrieval
		assertEquals(1, descriptor.getSubModelDescriptors().size());
		assertEquals("SM1", descriptor.getSubModelDescriptors().iterator().next().getIdentifier().getId());

		// Add a second descriptor
		descriptor.addSubmodelDescriptor(new SubmodelDescriptor("SM2", new Identifier(IdentifierType.CUSTOM, "SM2"), "http://a.b/c/aas/submodels/SM2"));

		// Assert correct retrieval
		assertEquals(2, descriptor.getSubModelDescriptors().size());
	}
}
