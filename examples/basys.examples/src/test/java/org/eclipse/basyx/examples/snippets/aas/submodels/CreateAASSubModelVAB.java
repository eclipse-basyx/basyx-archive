package org.eclipse.basyx.examples.snippets.aas.submodels;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.junit.Test;



/**
 * Code snippet that illustrates the creation of an AAS sub model
 * 
 * @author kuhn
 *
 */
public class CreateAASSubModelVAB {

	
	/**
	 * Example sub model. This example sub model is created with the BaSyx SDK factory and defines the AAS meta model properties
	 */
	static class SampleSubModel extends SubModel {
		
		/**
		 * Version number of serialized instance
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor - create sub model property
		 * 
		 * This sub model contains static properties, i.e. properties that have a static value assigned.
		 */
		@SuppressWarnings("unchecked")
		public SampleSubModel() {
			// Set sub model ID
			setIdShort("sm-001M");

			// Add example properties
			// - Add simple property with value and idShort meta elements
			this.putPath("properties/prop1/value",     234);
			this.putPath("properties/prop1/valueType", PropertyValueTypeDefHelper.fromObject(234));
			this.putPath("properties/prop1/idShort",   "prop1");

			// Add container property that holds other properties
			this.putPath("properties/prop2/idShort", "prop2");
			// - Add contained property
			this.putPath("properties/prop2/properties/prop11/value",     123);
			this.putPath("properties/prop2/properties/prop11/valueType", PropertyValueTypeDefHelper.fromObject(123).toString());
			this.putPath("properties/prop2/properties/prop11/idShort", "prop11");
			
			// Add another property manually to sub model container "properties"
			// - Using the Property class ensures presence of all meta properties
			SingleProperty addedProperty = new SingleProperty(); 
			addedProperty.set(17);
			addedProperty.setIdShort("prop3");
			// - Add property to sub model container "properties"
			{((Map<String, Object>) this.get("properties")).put("prop3", addedProperty);}
		}
	}

	
	/**
	 * Run code snippet. Instantiate AAS sub model.
	 */
	@Test
	public void accessSubModel() throws Exception {
		// Instantiate AAS sub model
		SampleSubModel sampleSM = new SampleSubModel();
		
		// Access sub model property
		int propertyVal = (int) sampleSM.getPath("properties/prop1/value");
		
		// Check property value
		assertTrue(propertyVal == 234);
	}
}


