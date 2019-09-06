package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;

/**
 * Interface for DataSpecification
 * @author rajashek
 *
*/


public interface IRelationshipElement extends ISubmodelElement {

	public void setFirst(IReference first);
	
	public IReference getFirst();
	
	public void setSecond(IReference second);
	
	public IReference getSecond();
	
	
}
