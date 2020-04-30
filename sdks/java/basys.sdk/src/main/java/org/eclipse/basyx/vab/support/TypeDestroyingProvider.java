package org.eclipse.basyx.vab.support;

import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Provider used for removing type information from Objects. </br>
 * Similar to a communication over VAB.
 * 
 * @author conradi
 *
 */
public class TypeDestroyingProvider implements IModelProvider {

	private IModelProvider provider;
	
	public TypeDestroyingProvider(IModelProvider provider) {
		this.provider = provider;
	}
	
	@Override
	public Object getModelPropertyValue(String path) throws ProviderException {
		return TypeDestroyer.handle(provider.getModelPropertyValue(path));
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws ProviderException {
		provider.setModelPropertyValue(path, newValue);
	}

	@Override
	public void createValue(String path, Object newEntity) throws ProviderException {
		provider.createValue(path, newEntity);
	}

	@Override
	public void deleteValue(String path) throws ProviderException {
		provider.deleteValue(path);
	}

	@Override
	public void deleteValue(String path, Object obj) throws ProviderException {
		provider.deleteValue(path, obj);
	}

	@Override
	public Object invokeOperation(String path, Object... parameter) throws ProviderException {
		return TypeDestroyer.handle(provider.invokeOperation(path, parameter));
	}

}
