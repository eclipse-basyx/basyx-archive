package org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel;

import java.util.ArrayList;

import org.eclipse.basyx.aas.api.annotation.AASOperation;
import org.eclipse.basyx.aas.api.annotation.AASProperty;
import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.ParameterType;
import org.eclipse.basyx.aas.impl.resources.basic._AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic._Operation;
import org.eclipse.basyx.aas.impl.resources.basic._Property;
import org.eclipse.basyx.aas.impl.resources.basic._SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.enums.DataObjectType;



/**
 * Implement a submodel stub
 * 
 * @author kuhn
 *
 */
public class Stub2Submodel extends _SubModel {


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
	    _AssetAdministrationShell aas = new _AssetAdministrationShell();
	    aas.setId("Stub1AAS");
	    aas.setName("Stub1AAS");
	    this.setParent(aas);


		
		// Create and add properties
		_Property property1 = new _Property();
		property1.setName("samplePropertyA");
		property1.setId("samplePropertyA");
		property1.setDataType(DataObjectType.Int32);
		this.addProperty(property1);

		_Property property2 = new _Property();
		property2.setName("samplePropertyB");
		property2.setId("samplePropertyB");
		property2.setDataType(DataObjectType.Int32);
		this.addProperty(property2);

		
		// Create and add operation
	    _Operation op = new _Operation();
	    op.setAssetKind(AssetKind.INSTANCE);
	    op.setName("sub");
	    op.setId("sub");
	    ArrayList<ParameterType> pt = new ArrayList<>();
	    pt.add(new ParameterType(0, DataObjectType.Int32, "a"));
	    pt.add(new ParameterType(1, DataObjectType.Int32, "b"));
	    op.setParameterTypes(pt);
	    op.setReturnDataType(DataObjectType.Int32);
	    this.addOperation(op);
	}



	/**
	 * Constructor
	 */
	public Stub2Submodel(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();
		
		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
