package org.eclipse.basyx.examples.snippets.aas;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.junit.Test;



/**
 * Illustrate AAS data structure access via accessor methods
 * 
 * @author kuhn
 *
 */
public class AASPropertyAccessors {

		
	/**
	 * Test AAS data structure
	 */
	@Test
	public void snippet() throws Exception {
		
		// Create Asset Administration Shell
		AssetAdministrationShell aas = new AssetAdministrationShell();
		
		// Access predefined AAS properties
		// - Set AAS property via accessor method
		aas.put("idShort", "DeviceIDShort");
		// - Access AAS property via its name
		assertTrue(aas.getId().equals("DeviceIDShort"));
	}
}

