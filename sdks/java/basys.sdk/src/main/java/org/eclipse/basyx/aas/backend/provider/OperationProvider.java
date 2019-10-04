package org.eclipse.basyx.aas.backend.provider;

import java.util.Map;

import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.operation.Operation;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.tools.VABPathTools;

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
	public Object getModelPropertyValue(String path) throws Exception {
		throw new RuntimeException();
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		throw new RuntimeException();
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		throw new RuntimeException();
	}

	@Override
	public void deleteValue(String path) throws Exception {
		throw new RuntimeException();
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		throw new RuntimeException();
	}

	@Override
	public Object invokeOperation(String path, Object... parameters) throws Exception {
		// Unwrap parameters, if they are wrapped
		for (int i = 0; i < parameters.length; i++) {
			parameters[i] = unwrapParameter(parameters[i]);
		}

		// Invoke /invokable instead of an Operation property
		Object childElement = modelProvider.getModelPropertyValue(path);
		if (childElement instanceof Map<?, ?> && ((Map<?, ?>) childElement).containsKey(Operation.INVOKABLE)) {
			path = VABPathTools.concatenatePaths(path, Operation.INVOKABLE);
		}

		// Forward call to model provider
		return modelProvider.invokeOperation(path, parameters);
	}

}
