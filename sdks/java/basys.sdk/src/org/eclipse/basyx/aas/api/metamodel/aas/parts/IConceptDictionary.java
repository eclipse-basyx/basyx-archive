package org.eclipse.basyx.aas.api.metamodel.aas.parts;

import java.util.HashSet;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IReferable;

/**
 * Interface for ConceptDictionary
 * 
 * @author rajashek
 *
*/

public interface IConceptDictionary extends IReferable {
	
	public HashSet<String> getConceptDescription();
	
	public void setConceptDescription(HashSet<String> ref);
	
	

}
