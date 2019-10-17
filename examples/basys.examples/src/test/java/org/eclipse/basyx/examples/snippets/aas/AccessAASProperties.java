package org.eclipse.basyx.examples.snippets.aas;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.junit.Test;

/**
 * This code snippet illustrates the creation of an Asset Administration Shell (AAS) data structure 
 * and how to access its properties via different kinds of access operations
 * 
 * @author kuhn
 *
 */
public class AccessAASProperties {

		
	/**
	 * Code snippet that illustrates the instantiation and use of AAS
	 */
	@Test
	public void snippet() throws Exception {
		
		// Create Asset Administration Shell
		AssetAdministrationShell aas = new AssetAdministrationShell();
		
		// Access predefined AAS properties
		// - Set AAS property via generic access method. For the generic access method,
		//   the user must know the name of the accessed property. This kind of access
		//   is discouraged, as it is not portable to new meta model versions. However,
		//   it is illustrated for completeness.
		aas.put("idShort", "DeviceIDShort");
		// - Access AAS property via the specific access operation. This is the preferred
		//   approach for accessing Asset Administration Shell and sub model properties,
		//   as well as meta data.
		Object deviceIDValue = aas.getIdShort();
		
		
		// Compared received value to expected value
		assertTrue(deviceIDValue.equals("DeviceIDShort"));
	}
}

