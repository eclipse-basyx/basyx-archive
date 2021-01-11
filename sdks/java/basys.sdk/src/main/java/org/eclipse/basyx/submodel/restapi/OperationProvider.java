package org.eclipse.basyx.submodel.restapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.ExecutionState;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.InvokationRequest;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.InvokationResponse;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.eclipse.basyx.submodel.restapi.operation.AsyncOperationHandler;
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

	IModelProvider modelProvider;
	
	public static final String ASYNC = "?async=true";
	public static final String INVOCATION_LIST = "invocationList";


	public OperationProvider(IModelProvider modelProvider) {
		this.modelProvider = modelProvider;
	}

	@Override
	public Object getModelPropertyValue(String path) throws ProviderException {
		String[] splitted = VABPathTools.splitPath(path);
		if (path.isEmpty()) {
			return modelProvider.getModelPropertyValue("");
		} else if (splitted[0].equals(INVOCATION_LIST) && splitted.length == 2) {
			String requestId = splitted[1];
			String operationId = getIdShort(modelProvider.getModelPropertyValue(""));
			return AsyncOperationHandler.retrieveResult(operationId, requestId);
			
		} else {
			throw new MalformedRequestException("Get of an Operation supports only empty or /invocationList/{requestId} paths");
		}
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws ProviderException {
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
		boolean async = path.endsWith(ASYNC);

		// remove the "invoke" from the end of the path
		path = VABPathTools.stripInvokeFromPath(path);
		
		// TODO: Only allow wrapped parameters with InvokationRequests
		Object[] unwrappedParameters;
		InvokationRequest request = getInvokationRequest(parameters);
		if (request != null) {
			// Unwrap input arguments
			Collection<IOperationVariable> inputArguments = request.getInputArguments();
			unwrappedParameters = new Object[inputArguments.size()];
			Iterator<IOperationVariable> iterator = inputArguments.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				IOperationVariable next = iterator.next();
				unwrappedParameters[i] = unwrapParameter(next.getValue());
				i++;
			}
		} else {
			// Unwrap parameters, if they are wrapped
			unwrappedParameters = new Object[parameters.length];
			for (int i = 0; i < parameters.length; i++) {
				unwrappedParameters[i] = unwrapParameter(parameters[i]);
			}
		}

		String operationId = "";
		// Invoke /invokable instead of an Operation property if existent
		Object childElement = modelProvider.getModelPropertyValue(path);
		if (Operation.isOperation(childElement)) {
			path = VABPathTools.concatenatePaths(path, Operation.INVOKABLE);
			operationId = getIdShort(childElement);
		}
		
		if (async) {
			return AsyncOperationHandler.invokeAsync(new VABElementProxy(path, modelProvider), operationId, unwrappedParameters);
		}

		// Forward call to model provider
		Object directResult = modelProvider.invokeOperation(path, unwrappedParameters);
		if (request == null) {
			// Parameters have been passed directly? Directly return the result
			return directResult;
		}

		// Create InvokationResponse from result object:

		// Get SubmodelElement output template
		Collection<IOperationVariable> outputs = Operation
				.createAsFacade((Map<String, Object>) getModelPropertyValue("")).getOutputVariables();
		SubmodelElement outputElem = outputs.iterator().next().getValue().getLocalCopy();
		// Set result object
		outputElem.setValue(directResult);

		// Create and return InvokationResponse
		IOperationVariable outputVariable = new OperationVariable(outputElem);
		Collection<IOperationVariable> outputCol = Arrays.asList(outputVariable);
		String requestId = request.getRequestId();
		return new InvokationResponse(requestId, new ArrayList<>(), outputCol,
				ExecutionState.COMPLETED);
	}
	
	/**
	 * Extracts an invokation request from a generic parameter array
	 * 
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private InvokationRequest getInvokationRequest(Object[] parameters) {
		if (parameters.length == 1 && parameters[0] instanceof Map<?, ?>) {
			Map<String, Object> requestMap = (Map<String, Object>) parameters[0];
			return InvokationRequest.createAsFacade(requestMap);
		}
		return null;
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
	private Object unwrapParameter(Object parameter) {
		if (parameter instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) parameter;
			// Parameters have a strictly defined order and may not be omitted at all.
			// Enforcing the structure with valueType is ok, but we should unwrap null
			// values, too.
			if (map.get("valueType") != null && map.containsKey("value")) {
				return map.get("value");
			}
		}
		return parameter;
	}

}
