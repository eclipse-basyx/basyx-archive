package org.eclipse.basyx.examples.support;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * This class is used to build AssetAdministrationShells and Submodels
 * for the scenarios and snippets.
 * 
 * Please note that the generated objects are just for showcasing,
 * several mandatory attributes are missing. 
 * 
 * @author conradi
 *
 */
public class ExampleComponentBuilder {

	public static final String PROPERTY_ID = "prop";
	public static final int PROPERTY_VALUE = 123;

	public static final String COLLECTION_ID = "collection";
	public static final String COLLECTION_PROPERTY_ID = "propInCollection";
	public static final String COLLECTION_PROPERTY_VALUE = "TheValue";
	
	/**
	 * Builds a Submodel containing a Property and a Collection with a Property
	 * 
	 * @param idShort the idShort for the new Submodel
	 * @return the new Submodel
	 */
	public static SubModel buildExampleSubmodel(String idShort, String id) {
		SubModel submodel = new SubModel();
		submodel.setIdShort(idShort);
		submodel.setIdentification(IdentifierType.CUSTOM, id);
		
		// Add a Property to the Submodel
		Property property = new Property();
		property.setIdShort(PROPERTY_ID);
		property.setValue(PROPERTY_VALUE);
		submodel.addSubModelElement(property);
				
		// Add a SubmodelElementCollection
		SubmodelElementCollection collection = new SubmodelElementCollection();
		collection.setIdShort(COLLECTION_ID);
		
		// Add a Property to the SubmodelElementCollection
		Property property2 = new Property();
		property2.setIdShort(COLLECTION_PROPERTY_ID);
		property2.setValue(COLLECTION_PROPERTY_VALUE);
		collection.addSubModelElement(property2);
		submodel.addSubModelElement(collection);
		
		return submodel;
	}
	
	/**
	 * Builds an AssetAdministrationShell
	 * 
	 * @param idShort the idShort for the new AAS
	 * @param id the id to be used in Identification
	 * @return the new AAS
	 */
	public static AssetAdministrationShell buildExampleAAS(String idShort, String id) {
		AssetAdministrationShell aas = new AssetAdministrationShell();
		aas.setIdShort(idShort);
		
		aas.setIdentification(IdentifierType.CUSTOM, id);
		
		return aas;
	}
	
}
