package org.eclipse.basyx.components.processengine.connector;

import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connected.aas.ConnectedSubModel;
import org.eclipse.basyx.components.service.BaseBaSyxService;
import org.eclipse.basyx.vab.core.VABConnectionManager;


/**
 * A service executor that invokes services defined in the administration shells.
 * The service executor is called by the Java-delegate class of the process-engine
 * All necessary parameters are delivered through field injections
 * 
 * @author Zhang, Zai
 * */
public abstract class DeviceServiceExecutor extends BaseBaSyxService implements IDeviceServiceExecutor {
	
	protected  ConnectedAssetAdministrationShellManager manager;
	
	
	/**
	 * private constructor
	 * create the connected administration shell for data exchange
	 * */
	public DeviceServiceExecutor(VABConnectionManager connectionmanager) {
		setConnectionManager(connectionmanager);
		//set-up the administration shell manager to create connected aas
		manager = new ConnectedAssetAdministrationShellManager(connectionmanager);
	};
	
	/**
	 * Synchronous invocation the expected service specified by the BPMN-model
	 * */
	public Object executeService( String servicename, String serviceProvider, String submodelid, List<Object> params) {
		try {
			// create the submodel of the corresponding aas
			ISubModel serviceSubmodel = manager.retrieveSM(submodelid);
			// navigate to the expected service 
			Map<String, IOperation> operations = serviceSubmodel.getOperations();
			IOperation op = operations.get(servicename);
			
			// invoke the service
			System.out.printf("#Service Executor#--Call service: %s with parameter: %s \n", servicename,  params);
			Object position = op.invoke(params.toArray());
			
			return position;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	


	/**
	 * Get value of a property from a submodel
	 * */
	protected Object getProperty(String submodelid, String propertyName) throws Exception {
		// retrieve submodel with id
		ISubModel statusSubmodel = manager.retrieveSM(submodelid);
		
		// get properties of the submodel
		Map<String, IProperty> properties = ((ConnectedSubModel) statusSubmodel).getProperties();
		
		// get specific property
		ISingleProperty pro_EXST = (ISingleProperty)properties.get(propertyName);
		
		//get value-information of property in map
		Object obj = pro_EXST.get();
		
		//type-check
		if(obj instanceof Map) {
			//convert to map
			@SuppressWarnings("unchecked")
			Map<String, Object> valuemap = (Map<String, Object>)pro_EXST.get();
			
			// get value of the property
			Object val = valuemap.get("value");
			System.out.println("value of " + propertyName+ " is "+val);
			return val;
		}else if(obj instanceof String) {
			String value = (String) obj;
			System.out.println("value of " + propertyName+ " is "+value);
			return value;
		}
		return null;
	}
	
	
	@Override
	public String getServiceName() {
		return null;
	}

	@Override
	public String getServiceProvider() {
		return null;
	}

	@Override
	public List<Object> getParams() {
		return null;
	}

	/**
	 * For the case that the executor knows which submodel to call
	 * */
	@Override
	public abstract Object executeService(String serviceName, String deviceid, List<Object> params) throws Exception ;
	

	
}
