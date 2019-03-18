package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.OperationVariable;
/**
 * Facade providing access to a map containing the OperationVariable structure
 * @author rajashek
 *
 */
public class OperationVariableFacade implements IOperationVariable {
	private Map<String, Object> map;
	public OperationVariableFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public void setValue(SubmodelElement value) {
	map.put(OperationVariable.VALUE, value);
		
	}

	@Override
	public SubmodelElement getValue() {
	return (SubmodelElement)map.get(OperationVariable.VALUE);
	}

}
