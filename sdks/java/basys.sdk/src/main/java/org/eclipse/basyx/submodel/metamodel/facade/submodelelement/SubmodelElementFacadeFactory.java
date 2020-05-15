package org.eclipse.basyx.submodel.metamodel.facade.submodelelement;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
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
		String type = ModelType.createAsFacade(submodelElement).getName();
		
		switch (type) {
			case Property.MODELTYPE:
				return Property.createAsFacade(submodelElement);
			case BasicEvent.MODELTYPE:
				return BasicEvent.createAsFacade(submodelElement);
			case MultiLanguageProperty.MODELTYPE:
				return MultiLanguageProperty.createAsFacade(submodelElement);
			case Range.MODELTYPE:
				return Range.createAsFacade(submodelElement);
			case Entity.MODELTYPE:
				return Entity.createAsFacade(submodelElement);
			case File.MODELTYPE:
				return File.createAsFacade(submodelElement);
			case Blob.MODELTYPE:
				return Blob.createAsFacade(submodelElement);
			case ReferenceElement.MODELTYPE:
				return ReferenceElement.createAsFacade(submodelElement);
			case SubmodelElementCollection.MODELTYPE:
				return SubmodelElementCollection.createAsFacade(submodelElement);
			case RelationshipElement.MODELTYPE:
				return RelationshipElement.createAsFacade(submodelElement);
			case Operation.MODELTYPE:
				return Operation.createAsFacade(submodelElement);
			default:
				throw new RuntimeException("Can not create a submodel element from given map");
		}
	}
	
}
