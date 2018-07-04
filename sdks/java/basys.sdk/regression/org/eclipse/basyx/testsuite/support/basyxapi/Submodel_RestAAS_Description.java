package org.eclipse.basyx.testsuite.support.basyxapi;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.basyx.aas.api.annotation.AASProperty;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.Property;
import org.eclipse.basyx.aas.impl.resources.basic.SubModel;



/**
 * Implement description sub model for the example AAS RestAAS
 * 
 * @author kuhn,pschorn
 *
 */
public class Submodel_RestAAS_Description extends SubModel {


	/**
	 * Sub model property "productDescription"
	 */
	@AASProperty public String productDescription = "device description";
	
	/**
	 * Sub model property "testCollection"
	 */
	@AASProperty public Collection<Integer> testCollection = new LinkedList<Integer>();


	
	
	/**
	 * Constructor
	 */
	public Submodel_RestAAS_Description() {
		// Initialize this submodel
	    this.setAssetKind(AssetKind.INSTANCE);
	    this.setName("Rest Conformant Submodel 'description'");
	    this.setId("description");
	    this.setTypeDefinition("descriptionSM");
	    
	    
	    // Initialize dummy AAS (not needed anymore!)
	    AssetAdministrationShell aas = new AssetAdministrationShell();
	    aas.setId("RestAAS");
	    aas.setName("RestAAS");
	    this.setParent(aas);

		
		// Create and add properties
		Property property1 = new Property();
		property1.setName("productDescription");
		property1.setId("productDescription");
		property1.setDataType(DataType.STRING);
		this.addProperty(property1);
		
		testCollection.add(5);
		testCollection.add(42);
		
		Property property2 = new Property();
		property2.setName("testCollection");
		property2.setId("testCollection");
		property2.setDataType(DataType.COLLECTION);
		this.addProperty(property2);
	}


	/**
	 * Constructor
	 */
	public Submodel_RestAAS_Description(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();
		
		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
