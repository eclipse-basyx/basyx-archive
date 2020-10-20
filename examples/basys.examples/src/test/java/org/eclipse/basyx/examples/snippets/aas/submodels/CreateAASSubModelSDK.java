package org.eclipse.basyx.examples.snippets.aas.submodels;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.junit.Test;



/**
 * Code snippet that illustrates the creation of an AAS sub model
 * 
 * @author kuhn
 *
 */
public class CreateAASSubModelSDK {

	
	/**
	 * Example sub model. This example sub model is created with the BaSyx SDK factory and defines the AAS meta model properties
	 */
	static class SampleSubModel extends SubModel {
		/**
		 * Constructor - create sub model
		 * 
		 * This sub model contains static properties, i.e. properties that have a static value assigned.
		 */
		@SuppressWarnings("unchecked")
		public SampleSubModel() {
			// Set sub model ID
			setIdShort("sm-001");

			// Add example properties
			// - Add simple property
			Property prop1 = new Property(234);
			prop1.setIdShort("prop1");
			addSubModelElement(prop1);

			Property prop11 = new Property(123);
			prop11.setIdShort("prop11");
			// - Add container property that holds other properties
			SubmodelElementCollection container = new SubmodelElementCollection();
			container.setIdShort("prop2");
			container.addSubModelElement(prop11);
			// - Add container to property map
			addSubModelElement(container);

			// Add another property manually to sub model container "properties"
			Property prop3 = new Property(17);
			prop3.setIdShort("prop3");
			{
				((Map<String, Object>) this.get("submodelElements")).put("prop3", prop3);
			}
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
		int propertyVal = (int) sampleSM.getPath("submodelElements/prop1/value");
		
		// Check property value
		assertTrue(propertyVal == 234);
	}
}


