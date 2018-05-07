package org.eclipse.basyx.testsuite.support.gateways.stubs.servlets.topo.iese;

import org.eclipse.basyx.aas.api.annotation.AASProperty;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.Property;
import org.eclipse.basyx.aas.impl.resources.basic.SubModel;



/**
 * Implement product sub model for the example product database
 * 
 * @author kuhn
 *
 */
public class Submodel_Office_ProductDatabase_Products extends SubModel {


	/**
	 * Sub model property "product1Size"
	 */
	@AASProperty public int product1Size = 22;

	
	
	/**
	 * Constructor
	 */
	public Submodel_Office_ProductDatabase_Products() {
		// Initialize this submodel
	    this.setAssetKind(AssetKind.INSTANCE);
	    this.setName("products");
	    this.setId("products");
	    this.setTypeDefinition("productType");

	    // Initialize dummy AAS
	    AssetAdministrationShell aas = new AssetAdministrationShell();
	    aas.setId("product_database");
	    aas.setName("product_database");
	    this.setParent(aas);

		
		// Create and add properties
		Property property1 = new Property();
		property1.setName("product1Size");
		property1.setId("product1Size");
		property1.setDataType(DataType.INTEGER);
		this.addProperty(property1);
	}


	/**
	 * Constructor
	 */
	public Submodel_Office_ProductDatabase_Products(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();
		
		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
