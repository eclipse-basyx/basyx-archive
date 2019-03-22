package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElement;
/**
 * Interface for OperationVariable
 * @author rajashek
 *
*/
public interface IOperationVariable {
	
	public void setValue(SubmodelElement value);
	
	public SubmodelElement getValue();

}
