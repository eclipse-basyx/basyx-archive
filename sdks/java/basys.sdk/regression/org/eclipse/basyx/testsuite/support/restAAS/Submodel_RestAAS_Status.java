package org.eclipse.basyx.testsuite.support.restAAS;

import java.util.ArrayList;

import org.eclipse.basyx.aas.api.annotation.AASOperation;
import org.eclipse.basyx.aas.api.annotation.AASProperty;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.Operation;
import org.eclipse.basyx.aas.impl.resources.basic.ParameterType;
import org.eclipse.basyx.aas.impl.resources.basic.Property;
import org.eclipse.basyx.aas.impl.resources.basic.SubModel;



/**
 * Implement status sub model for the example AAS RestAAS
 * 
 * @author kuhn,pschorn
 *
 */
public class Submodel_RestAAS_Status extends SubModel {


	/**
	 * Sub model property "isReady"
	 */
	@AASProperty public boolean isReady = true;

	/**
	 * Sub model operation
	 */
	@AASOperation public int sub(int a, int b) {
		return a-b;
	}
	
	/**
	 * Constructor
	 */
	public Submodel_RestAAS_Status() {
		// Initialize this submodel
	    this.setAssetKind(AssetKind.INSTANCE);
	    this.setName("Rest Conformant Submodel 'statusSM'");
	    this.setId("status");
	    this.setTypeDefinition("statusSM");

	    
	    // Initialize dummy AAS (not needed anymore!)
	    AssetAdministrationShell aas = new AssetAdministrationShell();
	    aas.setId("RestAAS");
	    aas.setName("RestAAS");
	    this.setParent(aas);

		
		// Create and add properties
		Property property1 = new Property();
		property1.setName("isReady");
		property1.setId("isReady");
		property1.setDataType(DataType.BOOLEAN);
		this.addProperty(property1);
		
		// Create and add operation
	    Operation op = new Operation();
	    op.setAssetKind(AssetKind.INSTANCE);
	    op.setName("sub");
	    op.setId("sub");
	    ArrayList<ParameterType> pt = new ArrayList<>();
	    pt.add(new ParameterType(0, DataType.INTEGER, "a"));
	    pt.add(new ParameterType(1, DataType.INTEGER, "b"));
	    op.setParameterTypes(pt);
	    op.setReturnDataType(DataType.INTEGER);
	    this.addOperation(op);
	}


	/**
	 * Constructor
	 */
	public Submodel_RestAAS_Status(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();
		
		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
