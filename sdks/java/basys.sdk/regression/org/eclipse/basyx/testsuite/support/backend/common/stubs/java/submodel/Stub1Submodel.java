package org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.basyx.aas.api.annotation.AASOperation;
import org.eclipse.basyx.aas.api.annotation.AASProperty;
import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.impl.reference.ElementRef;
import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.Operation;
import org.eclipse.basyx.aas.impl.resources.basic.ParameterType;
import org.eclipse.basyx.aas.impl.resources.basic.Property;
import org.eclipse.basyx.aas.impl.resources.basic.PropertyContainer;
import org.eclipse.basyx.aas.impl.resources.basic.SubModel;



/**
 * Implement a submodel stub
 * 
 * @author kuhn
 *
 */
public class Stub1Submodel extends SubModel {


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
		 * Optional nested property
		 */
		@AASProperty public NestedPropertyType samplePropertyC = null;

		
		
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
			Property property4 = new Property();
			property4.setName("samplePropertyA");
			property4.setId("samplePropertyA");
			property4.setDataType(DataType.INTEGER);
			this.addProperty(property4);
			
			Property property5 = new Property();
			property5.setName("samplePropertyB");
			property5.setId("samplePropertyB");
			property5.setDataType(DataType.INTEGER);
			this.addProperty(property5);
			
			Property property6 = new Property();
			property6.setName("samplePropertyC");
			property6.setId("samplePropertyC");
			property6.setDataType(DataType.REFERENCE);
			this.addProperty(property6);
			
			
			
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
	 * Sub model property "sampleProperty5"
	 */
	@AASProperty public Collection<IElement> sampleProperty5 = new LinkedList<IElement>();
	

	/**
	 * Sub model property "sampleProperty6"
	 */
	@AASProperty public Map<String, Integer> sampleProperty6 = new HashMap<>();

	
	
	
	/**
	 * Referenced AAS "Stub2AAS"
	 */
	@AASProperty public IElementReference referencedAAS = new ElementRef("Stub2AAS");


	/**
	 * Referenced sub model "subModelRef" of same AAS
	 */
	@AASProperty public IElementReference subModelRefLocal = new ElementRef("Stub1AAS", "Stub2SM");


	/**
	 * Referenced sub model "mainSM" of different AAS
	 */
	@AASProperty public IElementReference subModelRefGlobal = new ElementRef("Stub2AAS", "mainSM");


	/**
	 * Referenced property of sub model "subModelRef"
	 */
	@AASProperty public IElementReference subModelPropertyRefLoc = new ElementRef("Stub1AAS", "Stub2SM", "samplePropertyA");

	
	/**
	 * Referenced property of sub model "subModelRef" in different AAS
	 */
	@AASProperty public IElementReference subModelPropertyRefGlob = new ElementRef("Stub2AAS", "mainSM", "samplePropertyE");

	

	
	
	
	/**
	 * Constructor
	 */
	public Stub1Submodel() {
		// Initialize this submodel
	    this.setAssetKind(AssetKind.INSTANCE);
	    this.setName("subModelExample");
	    this.setId("statusSM");
	    this.setTypeDefinition("smType");
	    
	    
	    // Initialize dummy AAS
	    AssetAdministrationShell aas = new AssetAdministrationShell();
	    aas.setId("Stub1AAS");
	    aas.setName("Stub1AAS");
	    this.setParent(aas);


		
		// Create and add properties
		Property property1 = new Property();
		property1.setName("sampleProperty1");
		property1.setId("sampleProperty1");
		property1.setDataType(DataType.INTEGER);
		this.addProperty(property1);

		Property property2 = new Property();
		property2.setName("sampleProperty2");
		property2.setId("sampleProperty2");
		property2.setDataType(DataType.INTEGER);
		this.addProperty(property2);

		sampleProperty3 = new NestedPropertyType("sampleProperty3", this);
		
		sampleProperty4.add(42); // add 42 to linkedList
		Property property4 = new Property();
		property4.setName("sampleProperty4");
		property4.setId("sampleProperty4");
		property4.setDataType(DataType.COLLECTION);
		this.addProperty(property4);

		sampleProperty5.add(property2); // add property to IElement linkedList
		Property property5 = new Property();
		property5.setName("sampleProperty5");
		property5.setId("sampleProperty5");
		property5.setDataType(DataType.COLLECTION);
		property5.setCollection(true);
		this.addProperty(property5);

		sampleProperty6.put("magic", 42);	// add entry "magic" -> 42 to map
		Property property6 = new Property();
		property6.setName("sampleProperty6");
		property6.setId("sampleProperty6");
		property6.setDataType(DataType.MAP);
		this.addProperty(property6);



		Property property7 = new Property();
		property7.setName("referencedAAS");
		property7.setId("referencedAAS");
		property7.setDataType(DataType.REFERENCE);
		this.addProperty(property7);

		Property property8 = new Property();
		property8.setName("subModelRefLocal");
		property8.setId("subModelRefLocal");
		property8.setDataType(DataType.REFERENCE);
		this.addProperty(property8);

		Property property9 = new Property();
		property9.setName("subModelRefGlobal");
		property9.setId("subModelRefGlobal");
		property9.setDataType(DataType.REFERENCE);
		this.addProperty(property9);

		Property property10 = new Property();
		property10.setName("subModelPropertyRefLoc");
		property10.setId("subModelPropertyRefLoc");
		property10.setDataType(DataType.REFERENCE);
		this.addProperty(property10);

		Property property11 = new Property();
		property11.setName("subModelPropertyRefGlob");
		property11.setId("subModelPropertyRefGlob");
		property11.setDataType(DataType.REFERENCE);
		this.addProperty(property11);

		
		
		
		// Create and add operation
	    Operation op = new Operation();
	    op.setAssetKind(AssetKind.INSTANCE);
	    op.setName("sum");
	    op.setId("sum");
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
	public Stub1Submodel(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();
		
		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
