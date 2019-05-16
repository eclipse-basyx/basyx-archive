package org.eclipse.basyx.examples.snippets.modelurn;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import basys.examples.aasdescriptor.ModelUrn;



/**
 * Illustrate both constructors of ModelURN class to create URNs
 * 
 * @author kuhn
 *
 */
public class ModelURNStringConstructor {

		
	/**
	 * Test AAS data structure
	 */
	@Test
	public void snippet() throws Exception {
		
		// Create model URN
		ModelUrn modelURN1 = new ModelUrn("de.FHG", "devices.es.iese", "controllerSM", "1.0", "3", "x-509", "001");
		
		// Create model URN using string constructor
		ModelUrn modelURN2 = new ModelUrn("urn:de.FHG:devices.es.iese:controllerSM:1.0:3:x-509#001");

		// Check equality of both URNs
		assertTrue(modelURN1.getURN().equals(modelURN2.getURN()));
	}
}

