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
 * Implement status sub model for the example AAS RestAAS
 * 
 * @author kuhn,pschorn
 *
 */
public class StubRestAASFrozenSM extends SubModel {


	/**
	 * Sub model property "sampleProperty1"
	 */
	@AASProperty public int sampleProperty1 = 2;

	
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
	public StubRestAASFrozenSM() {
		// Initialize this submodel
	    this.setAssetKind(AssetKind.INSTANCE);
	    this.setName("Submodel specifically for testing readonly (frozen) submodels");
	    this.setId("frozenSM");
	    this.setTypeDefinition("frozenSMDef");

	    
	    // Initialize dummy AAS (not needed anymore!)
	    AssetAdministrationShell aas = new AssetAdministrationShell();
	    aas.setId("RestAAS");
	    aas.setName("RestAAS");
	    this.setParent(aas);

		
		// Create and add properties
	    
		Property property1 = new Property();
		property1.setName("sampleProperty1");
		property1.setId("sampleProperty1");
		property1.setDataType(DataType.INTEGER);
		this.addProperty(property1);
		
		
		Property property2 = new Property();
		property2.setName("isReady");
		property2.setId("isReady");
		property2.setDataType(DataType.BOOLEAN);
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
	public StubRestAASFrozenSM(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();
		
		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
