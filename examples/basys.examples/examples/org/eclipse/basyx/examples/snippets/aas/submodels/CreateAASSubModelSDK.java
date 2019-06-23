package org.eclipse.basyx.examples.snippets.aas.submodels;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
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
		 * Version number of serialized instance
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor - create sub model
		 * 
		 * This sub model contains static properties, i.e. properties that have a static value assigned.
		 */
		@SuppressWarnings("unchecked")
		public SampleSubModel() {
			// Set sub model ID
			setId("sm-001");

			// Create factory that helps with property creation
			// - This factory creates sub model properties and ensures presence of all meta data
			MetaModelElementFactory fac = new MetaModelElementFactory();

			// Add example properties
			// - Add simple property
			getProperties().put(fac.create(new Property(), 234, "prop1"));

			// - Add container property that holds other properties
			List<SubmodelElement> containerProperties = fac.createList(
					fac.create(new Property(), 123, "prop11")
				);
			// - Add container to property map
			getProperties().put(fac.createContainer(new SubmodelElementCollection(), containerProperties, fac.emptyList(), "prop2"));

			// Add another property manually to sub model container "properties"
			{((Map<String, Object>) this.get("properties")).put("prop3", fac.create(new Property(), 17, "prop3"));}
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


