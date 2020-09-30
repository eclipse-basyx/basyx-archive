package org.eclipse.basyx.submodel.metamodel.facade;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.facade.submodelelement.SubmodelElementFacadeFactory;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;


/**
 * This class provides the functionality to convert the
 * smElements of a SubModel from a Collection to a Map and vice versa.<br>
 * The given SubModel/Map is not changed.<br>
 * This is necessary, because internally smElements are represented as Map and externally as Collection.
 * 
 * @author conradi
 *
 */
public class SubmodelMapConverter {

	
	/**
	 * Builds a SubModel from a given Map.<br>
	 * Converts the SubModel.SUBMODELELEMENT entry of a Map to a Map<IdShort, SMElement>.<br>
	 * Creates Facades for all smElements.
	 * 
	 * @param submodel a Map representing the SubModel to be converted.
	 * @return a new SubModel made from the given Map with the smElements as Map
	 */
	@SuppressWarnings("unchecked")
	public static SubModel mapToSM(Map<String, Object> submodel) {
		
		// Put the content of the Map into a SM and replace its smElements with the new Map of smElements
		SubModel ret = new SubModel();
		ret.setMap(submodel);
		
		Object smElements = submodel.get(SubModel.SUBMODELELEMENT);
		
		Map<String, Object> smElementsMap = new HashMap<>();
		
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
		}
		
		// Iterate through all SubmodelElements and create Facades for them
		smElementsMap.replaceAll((id, smElement) ->
			SubmodelElementFacadeFactory.createSubmodelElement((Map<String, Object>) smElement));
		
		ret.put(SubModel.SUBMODELELEMENT, smElementsMap);
		
		
		return ret;
	}
	
	/**
	 * Converts a given SubModel to a Map<br>
	 * Converts the SubModel.SUBMODELELEMENT entry of a SubModel to a Collection.<br>
	 * 
	 * @param submodel the SubModel to be converted.
	 * @return a Map made from the given SubModel containing the smElements as Collection.
	 */
	public static Map<String, Object> smToMap(SubModel submodel) {		
		
		// Get the smElements Map from the given SubModel
		Map<String, ISubmodelElement> smElements = submodel.getSubmodelElements();
		
		// Put the Entries of the SM in a new Map
		Map<String, Object> ret = new HashMap<>();
		ret.putAll(submodel);
		
		// Replace the smElements Map with the Collection of Elements
		ret.put(SubModel.SUBMODELELEMENT, smElements.values());
		
		return ret;
	}
	
}