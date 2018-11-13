package org.eclipse.basyx.testsuite.support.gateways.stubs.servlets.topo.manufacturing;

import org.eclipse.basyx.aas.api.annotation.AASProperty;
import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic._AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic._Property;
import org.eclipse.basyx.aas.impl.resources.basic._SubModel;

/**
 * Implement description sub model for the example device device2
 * 
 * @author kuhn
 *
 */
public class Submodel_Line2Int_Device10_Description extends _SubModel {

	/**
	 * Sub model property "productDescription"
	 */
	@AASProperty
	public String productDescriptionInt = "internal product description";

	/**
	 * Constructor
	 */
	public Submodel_Line2Int_Device10_Description() {
		// Initialize this submodel
		this.setId("description");
		this.setTypeDefinition("descriptionSM");

		// Initialize dummy AAS
		_AssetAdministrationShell aas = new _AssetAdministrationShell();
		aas.setId("device10");
		this.setParent(aas);

		// Create and add properties
		_Property property1 = new _Property();
		property1.setId("productDescriptionInt");
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
