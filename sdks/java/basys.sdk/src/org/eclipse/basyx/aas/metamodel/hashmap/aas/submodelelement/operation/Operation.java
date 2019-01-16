package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElement;

/**
 * Operation as defined in DAAS document <br/>
 * An operation is a submodel element with input and output variables.
 * 
 * @author schnicke
 *
 */
public class Operation extends SubmodelElement implements IOperation {
	private static final long serialVersionUID = -1381491542617026911L;

	private Function<Object[], Object[]> endpoint;

	/**
	 * Constructor
	 */
	public Operation() {
		// Input variables
		put("in", new ArrayList<OperationVariable>());

		// Output variables
		put("out", new ArrayList<OperationVariable>());

		// Extension of DAAS specification for function storage
		put("invokable", null);
	}

	/**
	 * 
	 * @param in
	 *            Input parameter of the operation.
	 * @param out
	 *            Output parameter of the operation.
	 * @param endpoint
	 *            the concrete function
	 * 
	 */
	public Operation(List<OperationVariable> in, List<OperationVariable> out, Function<Object[], Object[]> endpoint) {
		// Input variables
		put("in", in);

		// Output variables
		put("out", out);

		// Extension of DAAS specification for function storage
		put("invokable", endpoint);
		this.endpoint = endpoint;
	}

	@Override
	public Object invoke(Object... params) throws Exception {
		return endpoint.apply(params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperationVariable> getReturnTypes() {
		return (List<OperationVariable>) get("out");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OperationVariable> getParameterTypes() {
		return (List<OperationVariable>) get("in");
	}
}
