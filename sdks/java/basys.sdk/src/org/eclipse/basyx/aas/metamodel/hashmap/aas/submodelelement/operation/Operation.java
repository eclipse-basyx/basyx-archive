package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.metamodel.facades.OperationFacade;
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
	
	public static final String IN="in";
	public static final String OUT="out";
	public static final String INVOKABLE="invokable";
	public static final String IDSHORT="idShort";
	
	

	/**
	 * Constructor
	 */
	public Operation() {
		// Input variables
		put(IN, new ArrayList<OperationVariable>());

		// Output variables
		put(OUT, new ArrayList<OperationVariable>());

		// Extension of DAAS specification for function storage
		put(INVOKABLE, null);
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
		put(IN, in);

		// Output variables
		put(OUT, out);

		// Extension of DAAS specification for function storage
		put(INVOKABLE, endpoint);
		this.endpoint = endpoint;
	}

	@Override
	public Object invoke(Object... params) throws Exception {
		return new OperationFacade(this.endpoint,this).invoke(params);
	}



	@Override
	public List<OperationVariable> getReturnTypes() {
		return new OperationFacade(this).getReturnTypes();
	}

	@Override
	public List<OperationVariable> getParameterTypes() {
		return new OperationFacade(this).getParameterTypes();
	}

	@Override
	public void SetParameterTypes(List<OperationVariable> in) {
		new OperationFacade(this).SetParameterTypes(in);
		
	}

	@Override
	public void setReturnTypes(List<OperationVariable> out) {
		new OperationFacade(this).setReturnTypes(out);
		
	}

	@Override
	public void setInvocable(Function<Object[], Object[]> endpoint) {
		new OperationFacade(this).setInvocable(endpoint);
		
	}

	@Override
	public Function<Object[], Object[]> getInvocable() {
	return new OperationFacade(this).getInvocable();
	}
}
