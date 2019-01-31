package org.eclipse.basyx.testsuite.support.vab.stub;

import java.util.Arrays;

import org.eclipse.basyx.vab.core.IModelProvider;

public class VABHashMapProviderStub implements IModelProvider {

	@Override
	public Object getModelPropertyValue(String path) {
		System.out.println("Got a message for a GET request on " + path);

		return null;
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		System.out.println("Got a message for a SET request on " + path + " with parameter " +  newValue);
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		System.out.println("Got a message for a CREATE request on " + path + " with parameter " +  newEntity);

		
	}

	@Override
	public void deleteValue(String path) throws Exception {
		System.out.println("Got a message for a DELETE request on " + path);
		
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		System.out.println("Got a message for a DELETE request on " + path + " with parameter " +  obj);
		
	}

	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {
		System.out.println("Got a message for a INVOKE request on " + path + " with parameter " +  Arrays.asList(parameter));
		
		return null;
	}

}
