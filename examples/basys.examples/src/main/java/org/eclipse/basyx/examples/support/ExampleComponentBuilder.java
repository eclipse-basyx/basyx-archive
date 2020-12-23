package org.eclipse.basyx.examples.support;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;

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
		SubModel submodel = new SubModel(idShort, new Identifier(IdentifierType.CUSTOM, id));
		
		// Add a Property to the Submodel
		Property property = new Property(PROPERTY_ID, PropertyValueTypeDef.Int32);
		property.setValue(PROPERTY_VALUE);
		submodel.addSubModelElement(property);
				
		// Add a SubmodelElementCollection
		SubmodelElementCollection collection = new SubmodelElementCollection(COLLECTION_ID);
		
		// Add a Property to the SubmodelElementCollection
		Property property2 = new Property(COLLECTION_PROPERTY_ID, PropertyValueTypeDef.String);
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
		Identifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, id);
		Identifier assetIdentifier = new Identifier(IdentifierType.CUSTOM, id + "asset");
		Asset asset = new Asset(idShort + "asset", assetIdentifier, AssetKind.INSTANCE);
		AssetAdministrationShell aas = new AssetAdministrationShell(idShort, aasIdentifier, asset);
		
		return aas;
	}
	
}
