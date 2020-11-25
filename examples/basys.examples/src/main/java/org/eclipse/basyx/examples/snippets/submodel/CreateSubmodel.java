package org.eclipse.basyx.examples.snippets.submodel;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * Snippet that showcases how to create a Submodel
 * with the mandatory attributes and a Property.
 * 
 * @author conradi
 *
 */
public class CreateSubmodel {

	/**
	 * Creates a new Submodel with a Property
	 * 
	 * @param idShort the idShort of the Submodel to be created
	 * @param smIdentifier the Identifier of the Submodel to be created
	 * @param propertyIdShort the idShort of the Property to be created
	 * @param propertyValue the value of the Property (see PropertyValueTypeDef for allowed value types)
	 * @return the newly created Submodel
	 */
	public static SubModel createSubmodel(String idShort, IIdentifier smIdentifier, String propertyIdShort, Object propertyValue) {
		
		// Create a new Property with the given idShort and value
		Property property = new Property(propertyIdShort, propertyValue);
		
		// Create a new Submodel object
		SubModel sm = new SubModel(idShort, smIdentifier);
		
		// Add the Property to the Submodel
		sm.addSubModelElement(property);
		
		return sm;
	}
	
}