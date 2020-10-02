package org.eclipse.basyx.submodel.restapi;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
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

	public OperationProvider(IModelProvider modelProvider) {
		this.modelProvider = modelProvider;
	}

	@Override
	public Object getModelPropertyValue(String path) throws ProviderException {
		if (path.isEmpty()) {
			return modelProvider.getModelPropertyValue("");
		} else {
			throw new MalformedRequestException("Invalid access path");
		}
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws ProviderException {
		throw new MalformedRequestException("Invalid access path");
	}

	@Override
	public void createValue(String path, Object newEntity) throws ProviderException {
		throw new MalformedRequestException("Invalid access path");
	}

	@Override
	public void deleteValue(String path) throws ProviderException {
		// Deletion of operation is handled by parent provider
		throw new MalformedRequestException("Invalid access path");
	}

	@Override
	public void deleteValue(String path, Object obj) throws ProviderException {
		throw new MalformedRequestException("Invalid access path");
	}

	@Override
	public Object invokeOperation(String path, Object... parameters) throws ProviderException {
		
		// remove the "invoke" from the end of the path
		path = VABPathTools.stripInvokeFromPath(path);
		
		// Unwrap parameters, if they are wrapped
		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = unwrapParameter(parameters[i]);
		}

		// Invoke /invokable instead of an Operation property if existent
		Object childElement = modelProvider.getModelPropertyValue(path);
		if (childElement instanceof Map<?, ?> && ((Map<?, ?>) childElement).containsKey(Operation.INVOKABLE)) {
			path = VABPathTools.concatenatePaths(path, Operation.INVOKABLE);
		}

		// Forward call to model provider
		return modelProvider.invokeOperation(path, parameters);
	}

}
