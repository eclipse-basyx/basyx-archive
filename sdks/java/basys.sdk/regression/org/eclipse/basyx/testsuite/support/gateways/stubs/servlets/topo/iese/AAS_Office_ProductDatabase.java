package org.eclipse.basyx.testsuite.support.gateways.stubs.servlets.topo.iese;

import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.ReferencedSubModel;



/**
 * Implement an Asset Administration Shell
 * 
 * @author kuhn
 *
 */
public class AAS_Office_ProductDatabase extends AssetAdministrationShell {	
	
	
	/**
	 * Constructor
	 */
	public AAS_Office_ProductDatabase() {
		// Create Asset Administration Shell 
	    setId("product_database");
	    setAssetId("assetId");
	    setAssetKind(AssetKind.INSTANCE);
	    setAssetTypeDefinition("assetTypeDefinition");
	    setDisplayName("displayName");

	    
	    // Register sub models
	    
	    // - Sub model statusSM is a referenced sub model. Its contents are provided by a model provider. 
	    //   We do not know its exact internal structure here, since some properties (with varying cardinalities) are unknown. 
	    ReferencedSubModel statusSM = new ReferencedSubModel();
	    statusSM.setAssetKind(AssetKind.INSTANCE);
	    statusSM.setName("products");
	    statusSM.setId("products");
	    //statusSM.setTypeDefinition("smType");
	    this.addSubModel(statusSM);
	}
}
