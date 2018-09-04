package org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas;

import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;



/**
 * Implement an Asset Administration Shell
 * 
 * @author kuhn
 *
 */
public class Stub1AAS extends AssetAdministrationShell {	
	
	
	/**
	 * Constructor
	 */
	public Stub1AAS() {
		// Create Asset Administration Shell 
	    setId("Stub1AAS");
	    setAssetId("assetId");
	    setAssetKind(AssetKind.INSTANCE);
	    setAssetTypeDefinition("assetTypeDefinition");
	    setDisplayName("displayName");	
	}
}
