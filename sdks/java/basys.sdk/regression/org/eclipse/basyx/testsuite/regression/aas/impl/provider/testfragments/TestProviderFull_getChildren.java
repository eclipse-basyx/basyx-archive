package org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.basyx.aas.api.resources.IElement;
import org.eclipse.basyx.vab.core.IModelProvider;

/**
 * Provides a test method to check the get ability of a generic IModelProvider
 * implementation focused on getting children.
 * 
 * @author schnicke
 *
 */
public class TestProviderFull_getChildren {

	@SuppressWarnings("unchecked")
	public static void testGetChildren(IModelProvider provider, Collection<String> modelNames) throws Exception {
		// Get AAS sub model property values via AAS
		Collection<IElement> stub1ChildrenA = (Collection<IElement>) provider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/children");
		Collection<IElement> stub1ChildrenB = (Collection<IElement>) provider.getModelPropertyValue("statusSM/submodel/children");

		// - Check results
		assertTrue(modelNames.size() == 2);
		assertTrue(modelNames.containsAll(Arrays.asList("statusSM", "Stub1AAS")));
		// assertTrue(stub1ChildrenA.size() == 2);
		System.out.println("StatusSM contains " + stub1ChildrenA.size() + " children.");

		assertEquals(4, stub1ChildrenA.size());
		assertEquals(4, stub1ChildrenB.size());
	}
}
