package org.eclipse.basyx.regression.support.processengine.stubs;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.activiti.bpmn.model.ServiceTask;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.eclipse.basyx.components.processengine.connector.DeviceServiceDelegate;


/**
 * BPMNStub that invokes the JavaDelegate through the reflection
 * A test that ensure the right parameters are given to the DeviceServiceExecutor
 * 
 * @author Zhang, Zai
 * */
public class BPMNEngineStub {
	
	private Map<String, String> fieldInjections = new HashMap<>();
	
	private String classPath = "org.eclipse.basyx.components.processengine.connector.DeviceServiceDelegate";
	
	
	/**
	 * Constructor
	 * parameters are value of the injected fields in String
	 * @param serviceParameter all requested parameters in a serialized Json-String
	 * */
	public BPMNEngineStub(String serviceName, String serviceProvider, String serviceParameter, String submodelid) {
		fieldInjections.put("serviceName", serviceName);
		fieldInjections.put("serviceProvider", serviceProvider);
		fieldInjections.put("serviceParameter", serviceParameter);
		fieldInjections.put("submodelId", submodelid);
	}
	
	public void callJavaDelegate() throws Exception {
		// create the class of the java-delegate
		@SuppressWarnings("rawtypes")
		Class clazz = Class.forName(classPath);
		DeviceServiceDelegate delegate = (DeviceServiceDelegate) clazz.newInstance();	
		// get all declare field of the class, including private fields	
		Field fields[] = clazz.getDeclaredFields();
		// set expected value defined in the hashMap to each fields
		for(Field f : fields) {
			if(fieldInjections.containsKey(f.getName())) {
				// get expected value
				String value = fieldInjections.get(f.getName());
				// create expression for this field
				Expression serviceName_expression = createExpressionObejct(value);
				// set the private field accesable
				f.setAccessible(true);
				// set value to the field
				f.set(delegate, serviceName_expression);
			}
		}
		//create execution-stub
		DelegateExecution execution =  ExecutionEntityImpl.createWithEmptyRelationshipCollections();
		// create a flow-element for the execution
		ServiceTask serviceTask = new ServiceTask();
		// set id of the flow element
	    serviceTask.setId("t1");
	    // set name of the flow element
	    serviceTask.setName("task1");
	    // set thsi flow-element to the current element of the execution
	    execution.setCurrentFlowElement(serviceTask);
	    // call the delegate function
		delegate.execute(execution);
	}
	
	/**
	 * create the Expression-instance for the delegate 
	 * */
	private Expression createExpressionObejct(String value) {
		Expression ep = new FixedValue(value);
		return ep;
	}

}
