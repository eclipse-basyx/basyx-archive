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
public class MainSMSubmodel extends SubModel {


	/**
	 * Sub model operation
	 */
	@AASOperation public int sub(int a, int b) {
		return a-b;
	}
	
	
	
	/**
	 * Sub model property "samplePropertyD"
	 */
	@AASProperty public int samplePropertyD = 8;

	
	/**
	 * Sub model property "samplePropertyE"
	 */
	@AASProperty public int samplePropertyE = 9;

	
	

	
	
	
	/**
	 * Constructor
	 */
	public MainSMSubmodel() {
		// Initialize this submodel
	    this.setAssetKind(AssetKind.INSTANCE);
	    this.setName("subModelExample");
	    this.setId("mainSM");
	    this.setTypeDefinition("smType");
	    
	    
	    // Initialize dummy AAS
	    AssetAdministrationShell aas = new AssetAdministrationShell();
	    aas.setId("Stub2AAS"); // ("SubModel2TempAAS");
	    aas.setName("Stub2AAS"); //SubModel2TempAAS");
	    this.setParent(aas);


		
		// Create and add properties
		Property property1 = new Property();
		property1.setName("samplePropertyD");
		property1.setId("samplePropertyD");
		property1.setDataType(DataType.INTEGER);
		this.addProperty(property1);

		Property property2 = new Property();
		property2.setName("samplePropertyE");
		property2.setId("samplePropertyE");
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
	 * Constructor
	 */
	public MainSMSubmodel(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();
		
		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
