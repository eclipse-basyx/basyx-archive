package org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel;

import java.util.ArrayList;

import org.eclipse.basyx.aas.api.annotation.AASOperation;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.CollectionProperty;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.Operation;
import org.eclipse.basyx.aas.impl.resources.basic.ParameterType;
import org.eclipse.basyx.aas.impl.resources.basic.PropertyContainer;
import org.eclipse.basyx.aas.impl.resources.basic.SingleProperty;
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
			
			// Register contained properties
			SingleProperty property4 = new SingleProperty(4);
			property4.setName("samplePropertyA");
			property4.setId("samplePropertyA");
			property4.setDataType(DataType.INTEGER);
			this.addProperty(property4);
			
			SingleProperty property5 = new SingleProperty(5);
			property5.setName("samplePropertyB");
			property5.setId("samplePropertyB");
			property5.setDataType(DataType.INTEGER);
			this.addProperty(property5);
			
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
	}

	
	
	
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


		
		// Create and add properties
		SingleProperty property1 = new SingleProperty(2);
		property1.setName("sampleProperty1");
		property1.setId("sampleProperty1");
		property1.setDataType(DataType.INTEGER);
		this.addProperty(property1);

		SingleProperty property2 = new SingleProperty(3);
		property2.setName("sampleProperty2");
		property2.setId("sampleProperty2");
		property2.setDataType(DataType.INTEGER);
		this.addProperty(property2);
		
		NestedPropertyType sampleProperty3 = new NestedPropertyType("sampleProperty3", this);
		this.addProperty(sampleProperty3);
		
		ArrayList<Object> collection = new ArrayList<>();
		collection.add(42);
		CollectionProperty sampleProperty4 = new CollectionProperty(collection);
		sampleProperty4.setName("sampleProperty4");
		sampleProperty4.setId("sampleProperty4");
		sampleProperty4.setDataType(DataType.COLLECTION);
		this.addProperty(sampleProperty4);
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
