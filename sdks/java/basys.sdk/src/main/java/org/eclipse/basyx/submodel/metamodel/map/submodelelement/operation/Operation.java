package org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasDataSpecificationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.ReferableFacade;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Description;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;

/**
 * Operation as defined in DAAS document <br/>
 * An operation is a submodel element with input and output variables.
 * 
 * @author schnicke
 *
 */
public class Operation extends SubmodelElement implements IOperation {
	private static final long serialVersionUID = -1381491542617026911L;

	public static final String IN = "in";
	public static final String OUT = "out";
	public static final String INVOKABLE = "invokable";
	public static final String MODELTYPE = "Operation";

	/**
	 * Constructor
	 */
	public Operation() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

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
	 * @param function
	 *            the concrete function
	 * 
	 */
	public Operation(List<OperationVariable> in, List<OperationVariable> out, Function<Object[], Object> function) {
		// Input variables
		put(IN, in);

		// Output variables
		put(OUT, out);

		// Extension of DAAS specification for function storage
		put(INVOKABLE, function);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IOperationVariable> getParameterTypes() {
		return (List<IOperationVariable>) get(Operation.IN);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IOperationVariable> getReturnTypes() {
		return (List<IOperationVariable>) get(Operation.OUT);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object invoke(Object... params) throws Exception {
		return ((Function<Object[], Object>) get(INVOKABLE)).apply(params);
	}

	public void SetParameterTypes(List<OperationVariable> in) {
		put(Operation.IN, in);
	}

	public void setReturnTypes(List<OperationVariable> out) {
		put(Operation.OUT, out);
	}

	public void setInvocable(Function<Object[], Object> endpoint) {
		put(Operation.INVOKABLE, endpoint);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Function<Object[], Object> getInvocable() {
		return (Function<Object[], Object>) get(Operation.INVOKABLE);
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(this).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(Set<IReference> ref) {
		new HasDataSpecificationFacade(this).setDataSpecificationReferences(ref);
	}

	@Override
	public String getIdShort() {
		return new ReferableFacade(this).getIdShort();
	}

	@Override
	public String getCategory() {
		return new ReferableFacade(this).getCategory();
	}

	@Override
	public Description getDescription() {
		return new ReferableFacade(this).getDescription();
	}
}
