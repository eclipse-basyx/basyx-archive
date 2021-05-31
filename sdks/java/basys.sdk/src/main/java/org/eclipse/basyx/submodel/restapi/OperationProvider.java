/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.submodel.restapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.eclipse.basyx.submodel.restapi.operation.AsyncOperationHandler;
import org.eclipse.basyx.submodel.restapi.operation.CallbackResponse;
import org.eclipse.basyx.submodel.restapi.operation.ExecutionState;
import org.eclipse.basyx.submodel.restapi.operation.InvocationRequest;
import org.eclipse.basyx.submodel.restapi.operation.InvocationResponse;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Handles operations according to AAS meta model.
 *
 * @author schnicke
 *
 */
public class OperationProvider implements IModelProvider {
	public static final String ASYNC = "?async=true";
	public static final String INVOCATION_LIST = "invocationList";
	public String operationId;

	private IModelProvider modelProvider;

	public OperationProvider(IModelProvider modelProvider) {
		this.modelProvider = modelProvider;
		operationId = getIdShort(modelProvider.getValue(""));
	}

	@Override
	public Object getValue(String path) throws ProviderException {
		String[] splitted = VABPathTools.splitPath(path);
		if (path.isEmpty()) {
			return modelProvider.getValue("");
		} else if (isInvocationListQuery(splitted)) {
			String requestId = splitted[1];
			return AsyncOperationHandler.retrieveResult(requestId, operationId);

		} else {
			throw new MalformedRequestException("Get of an Operation supports only empty or /invocationList/{requestId} paths");
		}
	}

	private boolean isInvocationListQuery(String[] splitted) {
		return splitted[0].equals(INVOCATION_LIST) && splitted.length == 2;
	}

	@Override
	public void setValue(String path, Object newValue) throws ProviderException {
		throw new MalformedRequestException("Set not allowed at path '" + path + "'");
	}

	@Override
	public void createValue(String path, Object newEntity) throws ProviderException {
		throw new MalformedRequestException("Create not allowed at path '" + path + "'");
	}

	@Override
	public void deleteValue(String path) throws ProviderException {
		// Deletion of operation is handled by parent provider
		throw new MalformedRequestException("Delete not allowed at path '" + path + "'");
	}

	@Override
	public void deleteValue(String path, Object obj) throws ProviderException {
		throw new MalformedRequestException("Delete not allowed at path '" + path + "'");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object invokeOperation(String path, Object... parameters) throws ProviderException {
		boolean isAsync = isAsyncInvokePath(path);
		path = VABPathTools.stripInvokeFromPath(path);
		
		// Invoke /invokable instead of an Operation property if existent
		Object childElement = modelProvider.getValue(path);
		if (!Operation.isOperation(childElement)) {
			throw new MalformedRequestException("Only operations can be invoked.");
		}

		Operation op = Operation.createAsFacade((Map<String, Object>) childElement);

		// TODO: Only allow wrapped parameters with InvokationRequests
		Object[] unwrappedParameters;
		InvocationRequest request = getInvocationRequest(parameters, op);
		String requestId;
		if (request != null) {
			unwrappedParameters = request.unwrapInputParameters();
			requestId = request.getRequestId();
		} else {// => not necessary, if it is only allowed to use InvocationRequests
			unwrappedParameters = unwrapDirectParameters(parameters);
			requestId = UUID.randomUUID().toString();
		}

		if (isAsync) {
			return handleAsyncOperationInvokation(path, unwrappedParameters, request, requestId);
		} else {
			return handleSynchroneousOperationInvokation(path, unwrappedParameters, request);
		}
	}

	/**
	 * Executes an invocation synchronously
	 * 
	 * @param path the invocation path
	 * @param unwrappedParameters the parameters for the invocation
	 * @param request the invocation request
	 * @return the result of the operation
	 * @throws ProviderException
	 */
	private Object handleSynchroneousOperationInvokation(String path, Object[] unwrappedParameters, InvocationRequest request) throws ProviderException {
		// Forward direct operation call to modelprovider
		path = VABPathTools.concatenatePaths(path, Operation.INVOKABLE);
		Object directResult = modelProvider.invokeOperation(path, unwrappedParameters);
		if (request == null) {
			// Parameters have been passed directly? Directly return the result
			return directResult;
		}
		return createInvocationResponseFromDirectResult(request, directResult);
	}

	/**
	 * Executes an invocation asynchronously
	 * 
	 * @param path the invocation path
	 * @param unwrappedParameters the parameters for the invocation
	 * @param request the invocation request
	 * @param requestId the id for the request
	 * @return the result of the operation
	 */
	private Object handleAsyncOperationInvokation(String path, Object[] unwrappedParameters, InvocationRequest request, String requestId) {
		Collection<IOperationVariable> outputVars = copyOutputVariables();
		IModelProvider provider = new VABElementProxy(path + "/invokable", modelProvider);

		// Only necessary as long as invocations without InvokationRequest is allowed
		if (request != null) {
			AsyncOperationHandler.invokeAsync(provider, operationId, request, outputVars);
		} else {
			AsyncOperationHandler.invokeAsync(provider, operationId, requestId, unwrappedParameters, outputVars, 10000);
		}

		// Request id has to be returned for caller to be able to retrieve result
		// => Use callback response and leave url empty
		return new CallbackResponse(requestId, "");
	}

	/**
	 * Determines if an invocation path is an async invocation
	 * 
	 * @param path the invocation path
	 * @return the result
	 */
	private boolean isAsyncInvokePath(String path) {
		return path.endsWith(ASYNC);
	}

	/**
	 * Directly creates an InvocationResponse from an operation result
	 */
	private Object createInvocationResponseFromDirectResult(InvocationRequest request, Object directResult) {
		// Get SubmodelElement output template
		Collection<IOperationVariable> outputs = copyOutputVariables();
		if(outputs.size() > 0) {
			SubmodelElement outputElem = (SubmodelElement) outputs.iterator().next().getValue();
			// Set result object
			outputElem.setValue(directResult);
		}

		// Create and return InvokationResponse
		return new InvocationResponse(request.getRequestId(), new ArrayList<>(), outputs, ExecutionState.COMPLETED);
	}

	/**
	 * Extracts an invocation request from a generic parameter array
	 * Matches parameters to order of Operation inputs by id
	 * Throws MalformedArgumentException if a required parameter is missing
	 * 
	 * @param parameters the input parameters
	 * @param op the Operation providing the inputVariables to be matched to the actual input
	 * @return the build InvocationRequest
	 */
	@SuppressWarnings("unchecked")
	private InvocationRequest getInvocationRequest(Object[] parameters, Operation op) {
		if (!isInvokationRequest(parameters)) {
			return null;
		}

		Map<String, Object> requestMap = (Map<String, Object>) parameters[0];
		InvocationRequest request = InvocationRequest.createAsFacade(requestMap);

		// Sort parameters in request by InputVariables of operation
		Collection<IOperationVariable> vars = op.getInputVariables();
		Collection<IOperationVariable> ordered = createOrderedInputVariablesList(request, vars);
		
		InvocationRequest orderedRequest = new InvocationRequest(request.getRequestId(), request.getInOutArguments(), ordered, request.getTimeout());

		return orderedRequest;
	}

	private Collection<IOperationVariable> createOrderedInputVariablesList(InvocationRequest request,
			Collection<IOperationVariable> vars) {
		Collection<IOperationVariable> ordered = new ArrayList<>();

		for (IOperationVariable var : vars) {
			String id = var.getValue().getIdShort();
			ordered.add(findOperationVariableById(id, request.getInputArguments()));
		}

		return ordered;
	}

	private IOperationVariable findOperationVariableById(String id, Collection<IOperationVariable> vars) {
		for (IOperationVariable input : vars) {
			if (input.getValue().getIdShort().equals(id)) {
				return input;
			}
		}

		throw new MalformedRequestException("Expected parameter " + id + " missing in request");
	}

	private boolean isInvokationRequest(Object[] parameters) {
		if(parameters.length != 1) {
			return false;
		}
		return InvocationRequest.isInvocationRequest(parameters[0]);
	}

	/**
	 * Gets the (first) output parameter from the underlying object
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Collection<IOperationVariable> copyOutputVariables() {
		Map<String, Object> operationMap = (Map<String, Object>) getValue("");
		Operation op = Operation.createAsFacade(operationMap);
		Collection<IOperationVariable> outputs = op.getOutputVariables();
		Collection<IOperationVariable> outCopy = new ArrayList<>();
		outputs.stream().forEach(o -> outCopy.add(new OperationVariable(o.getValue().getLocalCopy())));
		return outCopy;
	}

	@SuppressWarnings("unchecked")
	private String getIdShort(Object operation) {
		if(Operation.isOperation(operation)) {
			return Operation.createAsFacade((Map<String, Object>) operation).getIdShort();
		} else {
			// Should never happen as SubmodelElementProvider.getElementProvider
			// already checked that it is an Operation, if the Provider is used as intended
			throw new ProviderException("The Object this OperationProvider is pointing to is not an Operation");
		}
	}

	/**
	 * Unwraps a parameter by retrieving the "value" entry
	 *
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Object[] unwrapDirectParameters(Object[] parameters) {
		// Unwrap parameters, if they are wrapped
		Object[] unwrappedParameters = new Object[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			Object parameter = parameters[i];
			if (parameter instanceof Map<?, ?>) {
				Map<String, Object> map = (Map<String, Object>) parameter;
				// Parameters have a strictly defined order and may not be omitted at all.
				// Enforcing the structure with valueType is ok, but we should unwrap null
				// values, too.
				if (map.get("valueType") != null && map.containsKey("value")) {
					unwrappedParameters[i] = map.get("value");
					continue;
				}
			}
			unwrappedParameters[i] = parameter;
		}
		return unwrappedParameters;
	}

}
