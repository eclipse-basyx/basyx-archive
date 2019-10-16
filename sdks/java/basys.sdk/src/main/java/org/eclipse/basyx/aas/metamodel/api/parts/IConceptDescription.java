package org.eclipse.basyx.aas.metamodel.api.parts;

import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IIdentifiable;
/**
 * Interface for ConceptDescription
 * 
 * @author rajashek
 *
*/
public interface IConceptDescription extends IHasDataSpecification,IIdentifiable {
	public Set<String> getisCaseOf();
}
