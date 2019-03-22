package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

/**
 * Interface for DataSpecification
 * @author rajashek
 *
*/


public interface IRelationshipElement {

	public void setFirst(Reference first);
	
	public Reference getFirst();
	
	public void setSecond(Reference second);
	
	public Reference getSecond();
	
	
}
