package org.eclipse.basyx.submodel.metamodel.facade;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.facade.submodelelement.SubmodelElementFacadeFactory;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;


/**
 * This class provides the functionality to convert the
 * smElements of a SubModel/SubmodelElementCollection from a Collection to a Map and vice versa.<br>
 * The given SubModel/Map is not changed.<br>
 * This is necessary, because internally smElements are represented as Map and externally as Collection.
 * 
 * @author conradi
 *
 */
public class SubmodelElementMapCollectionConverter {
	
	
	/**
	 * Builds a SubModel from a given Map.<br>
	 * Converts the SubModel.SUBMODELELEMENT entry of a Map to a Map<IdShort, SMElement>.<br>
	 * Creates Facades for all smElements.
	 * 
	 * @param submodel a Map representing the SubModel to be converted.
	 * @return a new SubModel made from the given Map with the smElements as Map
	 */
	public static SubModel mapToSM(Map<String, Object> submodel) {
		
		// Put the content of the Map into a SM and replace its smElements with the new Map of smElements
		SubModel ret = new SubModel();
		ret.setMap(submodel);
		
		Object smElements = submodel.get(SubModel.SUBMODELELEMENT);
		
		ret.put(SubModel.SUBMODELELEMENT, convertCollectionToIDMap(smElements));
		
		return ret;
	}
	
	/**
	 * Converts a given SubModel to a Map<br>
	 * Converts the SubModel.SUBMODELELEMENT entry of a SubModel to a Collection.<br>
	 * 
	 * @param submodel the SubModel to be converted.
	 * @return a Map made from the given SubModel containing the smElements as Collection.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> smToMap(SubModel submodel) {		
		
		// Get the smElements Map from the given SubModel
		Map<String, ISubmodelElement> smElements = submodel.getSubmodelElements();
		
		// Put the Entries of the SM in a new Map
		Map<String, Object> ret = new HashMap<>();
		ret.putAll(submodel);
		
		// Feed all contained smElements through smElementToMap to deal with smElemCollections
		List<Map<String, Object>> newElements = smElements.values().stream()
				.map(e -> smElementToMap((Map<String, Object>) e)).collect(Collectors.toList());
		
		// Replace the smElements Map with the Collection of Elements
		ret.put(SubModel.SUBMODELELEMENT, newElements);
		
		return ret;
	}

	
	/**
	 * Builds a SubmodelElementCollection from a given Map.<br>
	 * Converts the Property.VALUE entry of a Map to a Map<IdShort, SMElement>.<br>
	 * Creates Facades for all smElements.
	 * 
	 * @param smECollection a Map representing the SubmodelElementCollection to be converted.
	 * @return a new SubmodelElementCollection made from the given Map with the smElements as Map
	 */
	public static SubmodelElementCollection mapToSmECollection(Map<String, Object> smECollection) {
		
		// Put the content of the Map into a SM and replace its smElements with the new Map of smElements
		SubmodelElementCollection ret = new SubmodelElementCollection();
		ret.setMap(smECollection);
		
		Object smElements = smECollection.get(Property.VALUE);
		
		ret.put(Property.VALUE, convertCollectionToIDMap(smElements));
		
		return ret;
	}
	
	/**
	 * Converts a given SubmodelElementCollection to a Map<br>
	 * Converts the Property.VALUE entry of a SubmodelElementCollection to a Collection.<br>
	 * If given Element is not a SubmodelElementCollection it will be returned unchanged.
	 * 
	 * @param smElement the SubmodelElement to be converted.
	 * @return a Map made from the given SubmodelElement.
	 */
	public static Map<String, Object> smElementToMap(Map<String, Object> smElement) {		
		
		if(!SubmodelElementCollection.isSubmodelElementCollection((Map<String, Object>) smElement)) {
			return (Map<String, Object>) smElement;
		}
		
		// Put the Entries of the SM in a new Map
		Map<String, Object> ret = new HashMap<>();
		ret.putAll(smElement);
		
		ret.put(Property.VALUE, convertIDMapToCollection(smElement.get(Property.VALUE)));
		
		return ret;
	}
	
	
	/**
	 * Converts a given smElement Collection/Map to a Map<idShort, smElement>. 
	 * 
	 * @param smElements the smElements to be converted
	 * @return a Map<idSHort, smElement>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> convertCollectionToIDMap(Object smElements) {
		Map<String, Object> smElementsMap = new HashMap<>();
		
		if(smElements == null) {
			// if null was given, return an empty Map
			return smElementsMap;
		}
		
		// SubmodelElemets can be given as Map, Set or List
		// If it is a Set or List, convert it to a Map first
		if(smElements instanceof Collection<?>) {
			Collection<Object> smElementsSet = (Collection<Object>) smElements;
			for (Object o: smElementsSet) {
				Map<String, Object> smElement = (Map<String, Object>) o;
				String id = (String) smElement.get(Referable.IDSHORT);
				smElementsMap.put(id, smElement);
			}
		} else if(smElements instanceof Map<?, ?>){
			smElementsMap = (Map<String, Object>) smElements;
		} else {
			throw new RuntimeException("Elements must be given as Map or Collection");
		}
		
		// Iterate through all SubmodelElements and create Facades for them
		smElementsMap.replaceAll((id, smElement) ->
			SubmodelElementFacadeFactory.createSubmodelElement((Map<String, Object>) smElement));
		
		return smElementsMap;
	}
	
	
	/**
	 * Converts a given Map<idShort, smElement> to a smElement Collection. 
	 * 
	 * @param smElements the smElements to be converted
	 * @return Collection<smElement>
	 */
	@SuppressWarnings("unchecked")
	public static Collection<Map<String, Object>> convertIDMapToCollection(Object map) {
		Collection<Object> smElements = null;
		
		// Check if the contained value is a Map or a Collection
		if(map instanceof Collection<?>) {
			// It it is a Collection proceed, as there could be nested Collections that need conversion
			smElements = (Collection<Object>) map;
		} else if(map instanceof Map<?, ?>) {
			smElements = ((Map<String, Object>) map).values();
		} else {
			throw new RuntimeException("The SubmodelElementCollection contains neither a Collection nor a Map as value.");
		}
		
		// Feed all contained smElements recursively through smElementToMap again to deal with nested smElemCollections
		List<Map<String, Object>> newElements = smElements.stream()
				.map(e -> smElementToMap((Map<String, Object>) e)).collect(Collectors.toList());
		
		return newElements;
	}
	
}