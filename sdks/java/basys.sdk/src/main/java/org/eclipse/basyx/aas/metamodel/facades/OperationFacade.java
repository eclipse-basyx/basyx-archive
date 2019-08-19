package org.eclipse.basyx.aas.metamodel.facades;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.OperationVariable;

/**
 * Facade providing access to a map containing the Operation structure
 * @author rajashek
 *
 */
public class OperationFacade implements IOperation {
	private Function<Object[], Object> endpoint;
	
	private Map<String, Object> map;
	public OperationFacade(Function<Object[], Object> endpoint, Map<String, Object> map) {
		super();
		this.endpoint = endpoint;
		this.map = map;
	}

	public OperationFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public String getId() {
	return (String) map.get(Referable.IDSHORT);
	}

	@Override
	public void setId(String id) {
		map.put(Referable.IDSHORT, id);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IOperationVariable> getParameterTypes() {
		return (List<IOperationVariable>) map.get(Operation.IN);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IOperationVariable> getReturnTypes() {
		return (List<IOperationVariable>) map.get(Operation.OUT);
	}

	@Override
	public Object invoke(Object... params) throws Exception {
		return this.endpoint.apply(params);
	}

	@Override
	public void SetParameterTypes(List<OperationVariable> in) {
		 map.put(Operation.IN,in);
		
	}

	@Override
	public void setReturnTypes(List<OperationVariable> out) {
		 map.put(Operation.OUT,out);
		
	}

	@Override
	public void setInvocable(Function<Object[], Object[]> endpoint) {
		map.put(Operation.INVOKABLE, endpoint);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Function<Object[], Object> getInvocable() {
		return (Function<Object[], Object>) map.get(Operation.INVOKABLE);
	}

}
