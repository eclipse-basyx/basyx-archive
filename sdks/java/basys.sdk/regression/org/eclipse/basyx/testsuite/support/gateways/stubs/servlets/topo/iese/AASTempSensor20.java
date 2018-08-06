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
public class AASTempSensor20 extends AssetAdministrationShell {	
	
	
	/**
	 * Constructor
	 */
	public AASTempSensor20() {
		// Create Asset Administration Shell 
	    setId("tempsensor20");
	    setAssetId("assetId");
	    setAssetKind(AssetKind.INSTANCE);
	    setAssetTypeDefinition("assetTypeDefinition");
	    setDisplayName("displayName");


	    // Register sub models
	    
	    // - Sub model statusSM is a referenced sub model. Its contents are provided by a model provider. 
	    //   We do not know its exact internal structure here, since some properties (with varying cardinalities) are unknown. 
	    ReferencedSubModel statusSM = new ReferencedSubModel();
	    statusSM.setAssetKind(AssetKind.INSTANCE);
	    statusSM.setName("temperatureStatusSM");
	    statusSM.setId("temperatureStatusSM");
	    //statusSM.setTypeDefinition("smType");
	    this.addSubModel(statusSM);
	}
}
