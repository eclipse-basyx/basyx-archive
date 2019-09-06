package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.ISubmodelElement;
/**
 * Interface for OperationVariable
 * @author rajashek
 *
*/
public interface IOperationVariable extends ISubmodelElement {
	
	public void setValue(ISubmodelElement value);
	
	public ISubmodelElement getValue();

}
