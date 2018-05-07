package org.eclipse.basyx.testsuite.support.gateways.stubs.servlets.topo.manufacturing;

import org.eclipse.basyx.aas.api.annotation.AASProperty;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.Property;
import org.eclipse.basyx.aas.impl.resources.basic.SubModel;



/**
 * Implement status sub model for the example device device2
 * 
 * @author kuhn
 *
 */
public class Submodel_Line2_Device2_Status extends SubModel {


	/**
	 * Sub model property "isReady"
	 */
	@AASProperty public boolean isReady = true;

	
	
	/**
	 * Constructor
	 */
	public Submodel_Line2_Device2_Status() {
		// Initialize this submodel
	    this.setAssetKind(AssetKind.INSTANCE);
	    this.setName("status");
	    this.setId("status");
	    this.setTypeDefinition("statusSM");

	    
	    // Initialize dummy AAS
	    AssetAdministrationShell aas = new AssetAdministrationShell();
	    aas.setId("device2");
	    aas.setName("device2");
	    this.setParent(aas);

		
		// Create and add properties
		Property property1 = new Property();
		property1.setName("isReady");
		property1.setId("isReady");
		property1.setDataType(DataType.BOOLEAN);
		this.addProperty(property1);
	}


	/**
	 * Constructor
	 */
	public Submodel_Line2_Device2_Status(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();
		
		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
