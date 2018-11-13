package org.eclipse.basyx.testsuite.support.gateways.stubs.servlets.topo.manufacturing;

import java.util.Collection;
import java.util.LinkedList;

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
public class Submodel_Line2_Device2_Description extends _SubModel {

	/**
	 * Sub model property "productDescription"
	 */
	@AASProperty
	public String productDescription = "device description";

	/**
	 * Sub model property "testCollection"
	 */
	@AASProperty
	public Collection<Integer> testCollection = new LinkedList<Integer>();

	/**
	 * Constructor
	 */
	public Submodel_Line2_Device2_Description() {
		// Initialize this submodel
		this.setId("description");
		this.setTypeDefinition("descriptionSM");

		// Initialize dummy AAS
		_AssetAdministrationShell aas = new _AssetAdministrationShell();
		aas.setId("device2");
		this.setParent(aas);

		// Create and add properties
		_Property property1 = new _Property();
		property1.setId("productDescription");
		this.addProperty(property1);

		testCollection.add(5);
		testCollection.add(42);

		_Property property2 = new _Property();
		property2.setId("testCollection");
		this.addProperty(property2);
	}

	/**
	 * Constructor
	 */
	public Submodel_Line2_Device2_Description(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();

		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
