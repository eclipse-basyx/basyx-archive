package org.eclipse.basyx.submodel.restapi;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
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
public class OperationProvider extends MetaModelProvider {

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

	@Override
	public Object invokeOperation(String path, Object... parameters) throws ProviderException {
		
		boolean async = path.endsWith(ASYNC);

		// remove the "invoke" from the end of the path
		path = VABPathTools.stripInvokeFromPath(path);
		
		// Unwrap parameters, if they are wrapped
		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = unwrapParameter(parameters[i]);
		}

		String operationId = "";
		// Invoke /invokable instead of an Operation property if existent
		Object childElement = modelProvider.getModelPropertyValue(path);
		if (Operation.isOperation(childElement)) {
			path = VABPathTools.concatenatePaths(path, Operation.INVOKABLE);
			operationId = getIdShort(childElement);
		}
		
		if(async) {
			return AsyncOperationHandler.invokeAsync(new VABElementProxy(path, modelProvider), operationId, parameters);
		} else {
			// Forward call to model provider
			return modelProvider.invokeOperation(path, parameters);			
		}
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

}
