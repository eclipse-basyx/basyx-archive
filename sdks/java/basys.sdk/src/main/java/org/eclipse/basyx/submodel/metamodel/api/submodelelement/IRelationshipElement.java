package org.eclipse.basyx.submodel.metamodel.api.submodelelement;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

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
