package org.eclipse.basyx.testsuite.support.gateways.stubs.servlets.topo.manufacturing;

import org.eclipse.basyx.aas.api.annotation.AASProperty;
import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic._AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic._Property;
import org.eclipse.basyx.aas.impl.resources.basic._SubModel;

/**
 * Implement status sub model for the example device device2
 * 
 * @author kuhn
 *
 */
public class Submodel_Line2Int_Device10_Status extends _SubModel {

	/**
	 * Sub model property "isReady"
	 */
	@AASProperty
	public boolean isReady = true;

	/**
	 * Constructor
	 */
	public Submodel_Line2Int_Device10_Status() {
		// Initialize this submodel
		this.setId("status");
		this.setTypeDefinition("statusSM");

		// Initialize dummy AAS
		_AssetAdministrationShell aas = new _AssetAdministrationShell();
		aas.setId("device10");
		this.setParent(aas);

		// Create and add properties
		_Property property1 = new _Property();
		property1.setId("isReady");
		this.addProperty(property1);
	}

	/**
	 * Constructor
	 */
	public Submodel_Line2Int_Device10_Status(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();

		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
