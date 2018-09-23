package org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.basyx.aas.api.annotation.AASOperation;
import org.eclipse.basyx.aas.api.annotation.AASProperty;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.impl.provider.DescriptorGenerator;
import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.Operation;
import org.eclipse.basyx.aas.impl.resources.basic.ParameterType;
import org.eclipse.basyx.aas.impl.resources.basic.PropertyContainer;
import org.eclipse.basyx.aas.impl.resources.basic.SubModel;



/**
 * Implement a submodel type stub
 * 
 * @author schnicke
 * 
 */
public class Stub1SubmodelType extends SubModel {



	/**
	 * Property type with nested property values
	 */
	public class NestedPropertyType extends PropertyContainer {

		
		/**
		 * Sub model property "samplePropertyA"
		 */
		@AASProperty public int samplePropertyA = 4;

		/**
		 * Sub model property "samplePropertyB"
		 */
		@AASProperty public int samplePropertyB = 5;
		
		
		/**
		 * Nested operation
		 */
		@AASOperation public int sub(int a, int b) {
			return a-b;
		}

		
		
		/**
		 * Constructor
		 */
		public NestedPropertyType(String name, SubModel parent) {
			setName("sampleProperty3");
			setId("sampleProperty3");
			setDataType(DataType.REFERENCE);
			parent.addProperty(this);
			
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
		    
		    DescriptorGenerator.addProperties(this);
		}
	}

	/**
	 * Sub model property "sampleProperty1"
	 */
	@AASProperty public int sampleProperty1 = 2;

	
	/**
	 * Sub model property "sampleProperty2"
	 */
	@AASProperty public int sampleProperty2 = 3;

	
	/**
	 * Sub model property "sampleProperty3" is nested property
	 */
	@AASProperty public NestedPropertyType sampleProperty3 = null;

	
	/**
	 * Sub model property "sampleProperty4"
	 */
	@AASProperty public Collection<Integer> sampleProperty4 = new LinkedList<Integer>();

	
	
	
	/**
	 * Sub model operation
	 */
	@AASOperation public int sum(int a, int b) {
		return a+b;
	}
	
	/**
	 * Constructor
	 */
	public Stub1SubmodelType() {
		// Initialize this submodel
	    this.setAssetKind(AssetKind.TYPE);
	    this.setName("subModelExample");
	    this.setId("statusSM");
	    this.setTypeDefinition("smType");
	    
	    
	    // Initialize dummy AAS
	    AssetAdministrationShell aas = new AssetAdministrationShell();
	    aas.setId("Stub1AAS");
	    aas.setName("Stub1AAS");
	    this.setParent(aas);
	    
	    sampleProperty3 = new NestedPropertyType("sampleProperty3", this);

	    DescriptorGenerator.addProperties(this);
	    DescriptorGenerator.addOperations(this);
	}



	/**
	 * Constructor
	 */
	public Stub1SubmodelType(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();
		
		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
