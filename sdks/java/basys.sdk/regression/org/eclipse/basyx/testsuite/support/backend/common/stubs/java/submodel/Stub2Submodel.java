package org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel;

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
 * Implement a submodel stub
 * 
 * @author kuhn
 *
 */
public class Stub2Submodel extends SubModel {


	/**
	 * Sub model operation
	 */
	@AASOperation public int sub(int a, int b) {
		return a-b;
	}
	
	
	
	/**
	 * Sub model property "sampleProperty1"
	 */
	@AASProperty public int samplePropertyA = 5;

	
	/**
	 * Sub model property "sampleProperty2"
	 */
	@AASProperty public int samplePropertyB = 6;

	
	

	
	
	
	/**
	 * Constructor
	 */
	public Stub2Submodel() {
		// Initialize this submodel
	    this.setAssetKind(AssetKind.INSTANCE);
	    this.setName("subModelExample");
	    this.setId("Stub2SM");
	    this.setTypeDefinition("smType");
	    
	    
	    // Initialize dummy AAS
	    AssetAdministrationShell aas = new AssetAdministrationShell();
	    aas.setId("Stub1AAS");
	    aas.setName("Stub1AAS");
	    this.setParent(aas);


		
		// Create and add properties
		Property property1 = new Property();
		property1.setName("samplePropertyA");
		property1.setId("samplePropertyA");
		property1.setDataType(DataType.INTEGER);
		this.addProperty(property1);

		Property property2 = new Property();
		property2.setName("samplePropertyB");
		property2.setId("samplePropertyB");
		property2.setDataType(DataType.INTEGER);
		this.addProperty(property2);

		
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
	 * Constructor (never called)
	 */
	public Stub2Submodel(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();
		
		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
