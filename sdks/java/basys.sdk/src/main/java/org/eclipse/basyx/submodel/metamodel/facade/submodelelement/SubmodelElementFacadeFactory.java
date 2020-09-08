package org.eclipse.basyx.submodel.metamodel.facade.submodelelement;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Blob;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.File;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.MultiLanguageProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Range;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.ReferenceElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.entity.Entity;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.event.BasicEvent;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.relationship.RelationshipElement;

/**
 * Facade factory for SubmodelElement
 * 
 * @author conradi
 * 
 */
public class SubmodelElementFacadeFactory {

	/**
	 * Takes a Map and creates the corresponding SubmodelElement as facade
	 * 
	 * @param smElement a Map containing the information of a SubmodelElement
	 * @return the actual of the given SubmodelElement map created as facade
	 */
	public static ISubmodelElement createSubmodelElement(Map<String, Object> submodelElement) {
		if (Property.isProperty(submodelElement)) {
			return Property.createAsFacade(submodelElement);
		} else if (Blob.isBlob(submodelElement)) {
			return Blob.createAsFacade(submodelElement);
		} else if (File.isFile(submodelElement)) {
			return File.createAsFacade(submodelElement);
		} else if (SubmodelElementCollection.isSubmodelElementCollection(submodelElement)) {
			return SubmodelElementCollection.createAsFacade(submodelElement);
		} else if (MultiLanguageProperty.isMultiLanguageProperty(submodelElement)) {
			return MultiLanguageProperty.createAsFacade(submodelElement);
		} else if (Entity.isEntity(submodelElement)) {
			return Entity.createAsFacade(submodelElement);
		} else if (Range.isRange(submodelElement)) {
			return Range.createAsFacade(submodelElement);
		} else if (ReferenceElement.isReferenceElement(submodelElement)) {
			return ReferenceElement.createAsFacade(submodelElement);
		} else if (RelationshipElement.isRelationshipElement(submodelElement)) {
			return RelationshipElement.createAsFacade(submodelElement);
		} else if (Operation.isOperation(submodelElement)) {
			return Operation.createAsFacade(submodelElement);
		} else if (BasicEvent.isBasicEvent(submodelElement)) {
			return BasicEvent.createAsFacade(submodelElement);
		} else {
			throw new RuntimeException("Can not create a submodel element from given map");
		}
	}

	
}
