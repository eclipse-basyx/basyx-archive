package org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * Response when invoking operation submodel elements
 * 
 * @author espen
 *
 */
public class InvokationResponse extends VABModelMap<Object> {
	public static final String REQUESTID = "requestId";
	public static final String INOUTARGUMENTS = "inoutputArguments";
	public static final String OUTPUTARGUMENTS = "outputArguments";
	public static final String EXECUTIONSTATE = "executionState";

	private InvokationResponse() {
	}

	public InvokationResponse(String requestId, Collection<IOperationVariable> inoutArguments,
			Collection<IOperationVariable> outputArguments, ExecutionState executionState) {
		put(REQUESTID, requestId);
		put(INOUTARGUMENTS, inoutArguments);
		put(OUTPUTARGUMENTS, outputArguments);
		put(EXECUTIONSTATE, executionState.toString());
	}

	public static InvokationResponse createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		InvokationResponse resp = new InvokationResponse();
		resp.setRequestId((String) map.get(REQUESTID));
		Collection<IOperationVariable> inoutArguments = createInoutArguments(map);
		resp.setInOutArguments(inoutArguments);
		Collection<IOperationVariable> outputArguments = createOutputArguments(map);
		resp.setOutputArguments(outputArguments);

		Object execStateObj = map.get(EXECUTIONSTATE);
		if (execStateObj instanceof String) {
			resp.setExecutionState(ExecutionState.fromString((String) execStateObj));
		} else if (execStateObj instanceof ExecutionState) {
			resp.setExecutionState((ExecutionState) execStateObj);
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	private static Collection<IOperationVariable> createOutputArguments(Map<String, Object> map) {
		Collection<Map<String, Object>> outputMap = (Collection<Map<String, Object>>) map.get(OUTPUTARGUMENTS);
		return createOperationVariables(outputMap);
	}

	/**
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	private static Collection<IOperationVariable> createInoutArguments(Map<String, Object> map) {
		Collection<Map<String, Object>> inoutMap = (Collection<Map<String, Object>>) map.get(INOUTARGUMENTS);
		return createOperationVariables(inoutMap);
	}

	private static Collection<IOperationVariable> createOperationVariables(
			Collection<Map<String, Object>> variableMap) {
		if (variableMap != null) {
			return variableMap.stream().map(OperationVariable::createAsFacade).collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}

	private void setRequestId(String request) {
		put(REQUESTID, request);
	}

	private void setInOutArguments(Collection<IOperationVariable> inoutArguments) {
		put(INOUTARGUMENTS, inoutArguments);
	}

	private void setOutputArguments(Collection<IOperationVariable> inputArguments) {
		put(OUTPUTARGUMENTS, inputArguments);
	}

	private void setExecutionState(ExecutionState state) {
		put(EXECUTIONSTATE, state.toString());
	}

	public String getRequestId() {
		return (String) get(REQUESTID);
	}

	@SuppressWarnings("unchecked")
	public Collection<IOperationVariable> getInOutArguments() {
		return (Collection<IOperationVariable>) get(INOUTARGUMENTS);
	}

	@SuppressWarnings("unchecked")
	public Collection<IOperationVariable> getOutputArguments() {
		return (Collection<IOperationVariable>) get(OUTPUTARGUMENTS);
	}

	public ExecutionState getExecutionState() {
		return ExecutionState.fromString((String) get(EXECUTIONSTATE));
	}
}
