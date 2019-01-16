package org.eclipse.basyx.testsuite.regression.vab.gateway;

import org.eclipse.basyx.vab.core.IModelProvider;

public class PrefixRemovingModelProvider implements IModelProvider {

	private IModelProvider provider;
	private String pathPrefix;

	public PrefixRemovingModelProvider(IModelProvider provider, String pathPrefix) {
		super();
		this.provider = provider;
		this.pathPrefix = pathPrefix;
	}

	@Override
	public Object getModelPropertyValue(String path) {
		return provider.getModelPropertyValue(removePathPrefix(path));
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		provider.setModelPropertyValue(removePathPrefix(path), newValue);
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		provider.createValue(removePathPrefix(path), newEntity);

	}

	@Override
	public void deleteValue(String path) throws Exception {
		provider.deleteValue(removePathPrefix(path));
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		provider.deleteValue(removePathPrefix(path), obj);
	}

	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {
		return provider.invokeOperation(removePathPrefix(path), parameter);
	}

	private String removePathPrefix(String path) {
		return path.replaceFirst(pathPrefix + "//", "");
	}

}
