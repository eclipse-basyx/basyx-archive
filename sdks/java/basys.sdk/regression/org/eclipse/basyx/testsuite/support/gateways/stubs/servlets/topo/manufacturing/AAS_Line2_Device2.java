package org.eclipse.basyx.testsuite.support.gateways.stubs.servlets.topo.manufacturing;

import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.ReferencedSubModel;



/**
 * Implement an Asset Administration Shell
 * 
 * @author kuhn
 *
 */
public class AAS_Line2_Device2 extends AssetAdministrationShell {	
	
	
	/**
	 * Constructor
	 */
	public AAS_Line2_Device2() {
		// Create Asset Administration Shell 
	    setId("device2");
	    setAssetId("assetId");
	    setAssetKind(AssetKind.INSTANCE);
	    setAssetTypeDefinition("assetTypeDefinition");
	    setDisplayName("displayName");

	    
	    // Register sub models
	    // - Description sub model with device descriptions
	    ReferencedSubModel descriptionSM = new ReferencedSubModel();
	    descriptionSM.setAssetKind(AssetKind.INSTANCE);
	    descriptionSM.setName("description");
	    descriptionSM.setId("description");
	    //descriptionSM.setTypeDefinition("smType");
	    this.addSubModel(descriptionSM);

	    // - Status sub model with device status	
	    ReferencedSubModel statusSM = new ReferencedSubModel();
	    statusSM.setAssetKind(AssetKind.INSTANCE);
	    statusSM.setName("status");
	    statusSM.setId("status");
	    //descriptionSM.setTypeDefinition("smType");
	    this.addSubModel(statusSM);
	}
}
