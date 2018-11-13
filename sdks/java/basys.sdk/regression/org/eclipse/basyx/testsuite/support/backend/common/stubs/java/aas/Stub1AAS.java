package org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas;

import org.eclipse.basyx.aas.impl.resources.basic._AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic._ReferencedSubModel;



/**
 * Implement an Asset Administration Shell
 * 
 * @author kuhn
 *
 */
public class Stub1AAS extends _AssetAdministrationShell {	
	
	
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
	    
	    // Register sub models
	    
	    // - Sub model statusSM is a referenced sub model. Its contents are provided by a model provider. 
	    //   We do not know its exact internal structure here, since some properties (with varying cardinalities) are unknown. 
	    _ReferencedSubModel statusSM = new _ReferencedSubModel();
	    statusSM.setAssetKind(AssetKind.INSTANCE);
	    statusSM.setName("statusSM");
	    statusSM.setId("statusSM");
	    //statusSM.setTypeDefinition("smType");
	    this.addSubModel(statusSM);

	    // - Sub model Stub2SM is a referenced sub model. Its contents are provided by a model provider. 
	    //   We do not know its exact internal structure here, since some properties (with varying cardinalities) are unknown. 
	    _ReferencedSubModel stub2SM = new _ReferencedSubModel();
	    stub2SM.setAssetKind(AssetKind.INSTANCE);
	    stub2SM.setName("Stub2SM");
	    stub2SM.setId("Stub2SM");
	    //statusSM.setTypeDefinition("smType");
	    this.addSubModel(stub2SM);
	}
}
