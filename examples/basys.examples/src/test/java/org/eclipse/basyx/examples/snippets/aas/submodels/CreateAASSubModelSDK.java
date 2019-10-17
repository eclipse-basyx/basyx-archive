package org.eclipse.basyx.examples.snippets.aas.submodels;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.factory.java.MetaModelElementFactory;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.ContainerProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
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
			setIdShort("sm-001");

			// Create factory that helps with property creation
			// - This factory creates sub model properties and ensures presence of all meta
			// data
			MetaModelElementFactory fac = new MetaModelElementFactory();

			// Add example properties
			// - Add simple property
			SingleProperty prop1 = new SingleProperty(234);
			prop1.setIdShort("prop1");
			addSubModelElement(prop1);

			SingleProperty prop11 = new SingleProperty(123);
			prop11.setIdShort("prop11");
			// - Add container property that holds other properties
			List<SubmodelElement> containerProperties = fac.createList(prop11);
			// - Add container to property map
			addSubModelElement(fac.createContainer(new ContainerProperty(), containerProperties, fac.emptyList(), "prop2"));

			// Add another property manually to sub model container "properties"
			SingleProperty prop3 = new SingleProperty(17);
			prop3.setIdShort("prop3");
			{
				((Map<String, Object>) this.get("dataElements")).put("prop3", prop3);
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
		int propertyVal = (int) sampleSM.getPath("dataElements/prop1/value");
		
		// Check property value
		assertTrue(propertyVal == 234);
	}
}


