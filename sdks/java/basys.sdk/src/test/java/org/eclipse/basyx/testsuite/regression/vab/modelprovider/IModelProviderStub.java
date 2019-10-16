package org.eclipse.basyx.testsuite.regression.vab.modelprovider;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

public class IModelProviderStub implements IModelProvider {

	private String path;
	private Object value;

	@Override
	public Object getModelPropertyValue(String path) {
		value = null;
		this.path = path;
		return null;
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		value = newValue;
		this.path = path;
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		value = newEntity;
		this.path = path;
	}

	@Override
	public void deleteValue(String path) throws Exception {
		value = null;
		this.path = path;
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		value = obj;
		this.path = path;
	}

	@Override
	public Object invokeOperation(String path, Object... parameter) throws Exception {
		value = parameter;
		this.path = path;
		return null;
	}

	public String getPath() {
		return path;
	}

	public Object getValue() {
		return value;
	}

}
