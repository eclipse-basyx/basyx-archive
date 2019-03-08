package org.eclipse.basyx.aas.api.metamodel.aas.parts;

import java.util.HashSet;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IIdentifiable;
/**
 * Interface for ConceptDescription
 * 
 * @author rajashek
 *
*/
public interface IConceptDescription extends IHasDataSpecification,IIdentifiable {

		
	public HashSet<String> getisCaseOf();
	
	public void setIscaseOf(HashSet<String> ref);

}
