package org.eclipse.basyx.examples.snippets.aas;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.junit.Test;

/**
 * Code snippet that illustrates the creation of an Asset Administration Shell (AAS) by extending an SDK class
 * 
 * @author kuhn
 *
 */
public class CreateAAS {

	
	/**
	 * Example Asset Administration Shell
	 */
	static class ExampleAssetAdministrationShell extends AssetAdministrationShell {
		
		/**
		 * Version number of serialized instance
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor
		 */
		public ExampleAssetAdministrationShell() {
			// Set Asset Administration Shell ID
			setIdShort("aas-001");
		}
	}
	
	/**
	 * Run code snippet. Connect to AAS on server, access AAS properties. 
	 */
	@Test
	public void createAAS() throws Exception {
		// Create Asset Administration Shell
		ExampleAssetAdministrationShell aas = new ExampleAssetAdministrationShell();
		
		// Retrieve AAS ID value
		Object propertyId = aas.getIdShort();
		
		
		// Check result
		assertEquals("aas-001", propertyId);
	}
}


