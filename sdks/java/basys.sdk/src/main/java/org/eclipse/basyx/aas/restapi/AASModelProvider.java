package org.eclipse.basyx.aas.restapi;

import java.util.Map;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;

/**
 * Model provider explicitely meant to implement the access to the AAS object. This excludes access to the submodels,
 * that are wrapped into their own provider.
 * 
 * @author espen
 *
 */
public class AASModelProvider implements IModelProvider {

	private IModelProvider modelProvider;

	/**
	 * Constructor based on the model provider containing the AAS model
	 */
	public AASModelProvider(IModelProvider modelProvider) {
		this.modelProvider = modelProvider;
	}

	/**
	 * Creates a AASModelProvider based on a lambda provider and a given model
	 */
	public AASModelProvider(Map<String, Object> model) {
		this.modelProvider = new VABLambdaProvider(model);
	}

	@Override
	public Object getModelPropertyValue(String path) throws Exception {
		return modelProvider.getModelPropertyValue(path);
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		modelProvider.setModelPropertyValue(path, newValue);
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		modelProvider.createValue(path, newEntity);
	}

	@Override
	public void deleteValue(String path) throws Exception {
		modelProvider.deleteValue(path);
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		modelProvider.deleteValue(path, obj);
	}

	/**
	 * Operations that can be invoked are not contained inside of AAS, but inside of submodels
	 */
	@Override
	public Object invokeOperation(String path, Object... parameter) throws Exception {
		throw new RuntimeException("");
	}
}
