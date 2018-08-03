package org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas;

import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.ReferencedSubModel;



/**
 * Implement an Asset Administration Shell.
 * 
 * @author kuhn, pschorn
 *
 */
public class StubRestAAS extends AssetAdministrationShell {	
	
	
	/**
	 * Constructor
	 */
	public StubRestAAS() {
		// Create Asset Administration Shell 
	    setId("RestAAS");
	    setAssetId("assetId");
	    setAssetKind(AssetKind.INSTANCE);
	    setAssetTypeDefinition("assetTypeDefinition");
	    setDisplayName("Rest API Conforming AAS");

	    
	    // Register sub models
	    
	    // - Status sub model with device status	
	    ReferencedSubModel frozenSM = new ReferencedSubModel();
	    frozenSM.setAssetKind(AssetKind.INSTANCE);
	    frozenSM.setName("frozenSM");
	    frozenSM.setId("frozenSM");
	    //descriptionSM.setTypeDefinition("smType");
	    this.addSubModel(frozenSM);
	}
}
