package org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas;

import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.ReferencedSubModel;



/**
 * Implement an Asset Administration Shell
 * 
 * @author kuhn
 *
 */
public class Stub2AAS extends AssetAdministrationShell {	
	
	
	/**
	 * Constructor
	 */
	public Stub2AAS() {
		// Create Asset Administration Shell 
	    setId("Stub2AAS");
	    setAssetId("assetId");
	    setAssetKind(AssetKind.INSTANCE);
	    setAssetTypeDefinition("assetTypeDefinition");
	    setDisplayName("displayName");

	    
	    // Register sub models	    
	    // - Sub model mainSM is a referenced sub model. Its contents are provided by a model provider. 
	    ReferencedSubModel mainSM = new ReferencedSubModel();
	    mainSM.setAssetKind(AssetKind.INSTANCE);
	    mainSM.setName("mainSM");
	    mainSM.setId("mainSM");
	    //statusSM.setTypeDefinition("smType");
	    this.addSubModel(mainSM);	
	}
}
