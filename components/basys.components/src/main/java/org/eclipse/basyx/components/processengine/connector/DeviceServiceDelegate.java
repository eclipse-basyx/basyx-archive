package org.eclipse.basyx.components.processengine.connector;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.eclipse.basyx.vab.coder.json.serialization.DefaultTypeFactory;
import org.eclipse.basyx.vab.coder.json.serialization.GSONTools;

/**
 * Java-Delegate is involved when the corresponding service-task of the BPMN-Model is executed. 
 * It invokes the service-call of the defined service in the aas.
 * 
 * @author Zhang, Zai
 * */
public class DeviceServiceDelegate implements JavaDelegate {
	
	// name of the service that is used to navigate the service in the aas
	private Expression serviceName;
	
	// Identify the device that provides the service
	private Expression serviceProvider;
	
	// parameters required by the service. 
	// All parameters are serialized as an array and stored in a Json string
	private Expression serviceParameter;
	
	/* Device service-executor invokes the services defined in the aas
	 * Only one instance is allowed
	 * */
	private static IDeviceServiceExecutor executor;
	
	// Instance of GSONTools used for JSON-serialisation
	private GSONTools gson = new GSONTools(new DefaultTypeFactory());
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(DelegateExecution execution) {
		
		// get name of the service
		String servicename = (String)serviceName.getValue(execution);
		
		// get the Json string of service parameters
		String params = (String) serviceParameter.getValue(execution);
		
		// deserialize the Json-string to get the parameters in an array
		List<Object> paramarray = new ArrayList<>();
		paramarray.addAll((Collection<Object>) gson.deserialize(params));
		// get name of the current process step in the BPMN-Model
		String processName = execution.getCurrentFlowElement().getName();
		String deviceAASId = (String)serviceProvider.getValue(execution);
		System.out.println("#######process instance: "+ execution.getProcessInstanceId()+" current activity: " + processName +" is executed by "+ deviceAASId);
		
		
		try {
			// invoke the specified service using service-executor
			executor.executeService(servicename, deviceAASId, paramarray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	
	public static void setDeviceServiceExecutor(IDeviceServiceExecutor ex) {
		executor = ex;
	}


	public static IDeviceServiceExecutor getExecutor() {
		return executor;
	}
	
	
}
