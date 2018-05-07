package org.eclipse.basyx.testsuite.support.gateways.stubs.servlets.topo.manufacturing;

import org.eclipse.basyx.aas.api.annotation.AASProperty;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.Property;
import org.eclipse.basyx.aas.impl.resources.basic.SubModel;



/**
 * Implement description sub model for the example device device2
 * 
 * @author kuhn
 *
 */
public class Submodel_Line2Int_Device10_Description extends SubModel {


	/**
	 * Sub model property "productDescription"
	 */
	@AASProperty public String productDescriptionInt = "internal product description";

	
	
	/**
	 * Constructor
	 */
	public Submodel_Line2Int_Device10_Description() {
		// Initialize this submodel
	    this.setAssetKind(AssetKind.INSTANCE);
	    this.setName("description");
	    this.setId("description");
	    this.setTypeDefinition("descriptionSM");
	    
	    
	    // Initialize dummy AAS
	    AssetAdministrationShell aas = new AssetAdministrationShell();
	    aas.setId("device10");
	    aas.setName("device10");
	    this.setParent(aas);

		
		// Create and add properties
		Property property1 = new Property();
		property1.setName("productDescriptionInt");
		property1.setId("productDescriptionInt");
		property1.setDataType(DataType.STRING);
		this.addProperty(property1);
	}


	/**
	 * Constructor
	 */
	public Submodel_Line2Int_Device10_Description(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();
		
		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
