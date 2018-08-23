package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets;

import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.ReferencedSubModel;



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

	    
	    // Register sub models
	    
	    // - Sub model statusSM is a referenced sub model. Its contents are provided by a model provider. 
	    //   We do not know its exact internal structure here, since some properties (with varying cardinalities) are unknown. 
	    ReferencedSubModel statusSM = new ReferencedSubModel();
	    statusSM.setAssetKind(AssetKind.INSTANCE);
	    statusSM.setName("statusSM");
	    statusSM.setId("statusSM");
	    //statusSM.setTypeDefinition("smType");
	    this.addSubModel(statusSM);

	    // - Sub model Stub2SM is a referenced sub model. Its contents are provided by a model provider. 
	    //   We do not know its exact internal structure here, since some properties (with varying cardinalities) are unknown. 
	    ReferencedSubModel stub2SM = new ReferencedSubModel();
	    stub2SM.setAssetKind(AssetKind.INSTANCE);
	    stub2SM.setName("Stub2SM");
	    stub2SM.setId("Stub2SM");
	    //statusSM.setTypeDefinition("smType");
	    this.addSubModel(stub2SM);

	    
	    // - Sub model technicalDataSM is a regular (contained) sub model. It contains static data from the data sheet
	    /*SubModel technicalDataSM = new SubModel();
	    technicalDataSM.setAssetKind(AssetKind.INSTANCE);
	    technicalDataSM.setName("technicalDataSM");
	    technicalDataSM.setId("technicalDataSM");
	    technicalDataSM.setTypeDefinition("smType");
	    this.addSubModel(technicalDataSM);*/
	    
	
	}
}
