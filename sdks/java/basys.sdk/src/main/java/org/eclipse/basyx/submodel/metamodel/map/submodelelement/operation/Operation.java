package org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;

/**
 * Operation as defined in DAAS document <br/>
 * An operation is a submodel element with input and output variables.
 * 
 * @author schnicke
 *
 */
public class Operation extends SubmodelElement implements IOperation {
	public static final String IN = "inputVariables";
	public static final String OUT = "outputVariables";
	public static final String INOUT = "inoutputVariables";

	public static final String INVOKABLE = "invokable";
	public static final String MODELTYPE = "Operation";
	
	public static final String INVOKE = "invoke";

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

		// Variables, that are input and output
		put(INOUT, new ArrayList<OperationVariable>());

		// Extension of DAAS specification for function storage
		put(INVOKABLE, null);
	}
	
	/**
	 * Constructor accepting only mandatory attribute
	 * @param idShort
	 */
	public Operation(String idShort) {
		super(idShort);
		// Add model type
		putAll(new ModelType(MODELTYPE));

		// Input variables
		setInputVariables(new ArrayList<OperationVariable>());

		// Output variables
		setOutputVariables(new ArrayList<OperationVariable>());

		// Variables, that are input and output
		setInOutputVariables(new ArrayList<OperationVariable>());

		// Extension of DAAS specification for function storage
		setInvocable(null);
	}

	/**
	 * 
	 * @param in
	 *            Input parameter of the operation.
	 * @param out
	 *            Output parameter of the operation.
	 * @param inout
	 *            Inoutput parameter of the operation.
	 * @param function
	 *            the concrete function
	 * 
	 */
	public Operation(Collection<OperationVariable> in, Collection<OperationVariable> out,
			Collection<OperationVariable> inout, Function<Object[], Object> function) {
		// Add model type
		putAll(new ModelType(MODELTYPE));

		// Input variables
		put(IN, in);

		// Output variables
		put(OUT, out);

		// Output variables
		put(INOUT, inout);

		// Extension of DAAS specification for function storage
		put(INVOKABLE, function);
	}

	/**
	 * Create Operations w/o endpoint
	 * 
	 * @param operation
	 * @param function
	 * @return
	 */
	public Operation(Function<Object[], Object> function) {
		this();
		setInvocable(function);
	}

	/**
	 * Creates an Operation object from a map
	 * 
	 * @param obj
	 *            an Operation object as raw map
	 * @return an Operation object, that behaves like a facade for the given map
	 */
	public static Operation createAsFacade(Map<String, Object> obj) {
		Operation ret = new Operation();
		ret.setMap(obj);
		return ret;
	}

	/**
	 * Returns true if the given submodel element map is recognized as an operation
	 */
	@SuppressWarnings("unchecked")
	public static boolean isOperation(Object value) {
		if(!(value instanceof Map<?, ?>)) {
			return false;
		}
		
		Map<String, Object> map = (Map<String, Object>) value;
		
		String modelType = ModelType.createAsFacade(map).getName();
		// Either model type is set or the element type specific attributes are contained
		return MODELTYPE.equals(modelType)
				|| (modelType == null && (map.containsKey(IN) && map.containsKey(OUT) && map.containsKey(INOUT)));
	}

	@Override
	public Collection<IOperationVariable> getInputVariables() {
		return transformToOperationVariables(get(Operation.IN));
	}

	@Override
	public Collection<IOperationVariable> getOutputVariables() {
		return transformToOperationVariables(get(Operation.OUT));
	}

	@Override
	public Collection<IOperationVariable> getInOutputVariables() {
		return transformToOperationVariables(get(Operation.INOUT));
	}

	@SuppressWarnings("unchecked")
	private Collection<IOperationVariable> transformToOperationVariables(Object obj) {
		if (obj instanceof Collection<?>) {
			Collection<Map<String, Object>> map = (Collection<Map<String, Object>>) obj;
			Collection<IOperationVariable> ret = new ArrayList<>();
			for (Map<String, Object> m : map) {
				ret.add(OperationVariable.createAsFacade(m));
			}
			return ret;
		} else {
			return new ArrayList<>();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object invoke(Object... params) throws Exception {
		return ((Function<Object[], Object>) get(INVOKABLE)).apply(params);
	}

	@Override
	public AsyncInvocation invokeAsync(Object... params) {
		AsyncInvocation invocation = new AsyncInvocation(this, params);
		return invocation;
	}

	public void setInputVariables(Collection<OperationVariable> in) {
		put(Operation.IN, in);
	}

	public void setOutputVariables(Collection<OperationVariable> out) {
		put(Operation.OUT, out);
	}

	public void setInOutputVariables(Collection<OperationVariable> inOut) {
		put(Operation.INOUT, inOut);
	}

	public void setInvocable(Function<Object[], Object> endpoint) {
		put(Operation.INVOKABLE, endpoint);
	}

	@Override
	public Collection<IReference> getDataSpecificationReferences() {
		return HasDataSpecification.createAsFacade(this).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(Collection<IReference> ref) {
		HasDataSpecification.createAsFacade(this).setDataSpecificationReferences(ref);
	}

	@Override
	public String getIdShort() {
		return Referable.createAsFacade(this, getKeyElement()).getIdShort();
	}

	@Override
	public String getCategory() {
		return Referable.createAsFacade(this, getKeyElement()).getCategory();
	}

	@Override
	public LangStrings getDescription() {
		return Referable.createAsFacade(this, getKeyElement()).getDescription();
	}
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.OPERATION;
	}

	@Override
	public Object getValue() {
		throw new UnsupportedOperationException("An Operation has no value");
	}

	@Override
	public void setValue(Object value) {
		throw new UnsupportedOperationException("An Operation has no value");
	}
}
