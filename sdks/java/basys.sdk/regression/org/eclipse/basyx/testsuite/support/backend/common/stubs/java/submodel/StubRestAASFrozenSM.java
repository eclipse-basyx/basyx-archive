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
 * Implement status sub model for the example AAS RestAAS
 * 
 * @author kuhn,pschorn
 *
 */
public class StubRestAASFrozenSM extends _SubModel {


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
	    _AssetAdministrationShell aas = new _AssetAdministrationShell();
	    aas.setId("RestAAS");
	    aas.setName("RestAAS");
	    this.setParent(aas);

		
		// Create and add properties
	    
		_Property property1 = new _Property();
		property1.setName("sampleProperty1");
		property1.setId("sampleProperty1");
		property1.setDataType(DataObjectType.Int32);
		this.addProperty(property1);
		
		
		_Property property2 = new _Property();
		property2.setName("isReady");
		property2.setId("isReady");
		property2.setDataType(DataObjectType.Bool);
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
	public StubRestAASFrozenSM(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();
		
		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
