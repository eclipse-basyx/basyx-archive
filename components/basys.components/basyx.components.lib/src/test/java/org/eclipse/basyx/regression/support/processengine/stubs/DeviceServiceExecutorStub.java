package org.eclipse.basyx.regression.support.processengine.stubs;

import java.util.List;

import org.eclipse.basyx.components.processengine.connector.IDeviceServiceExecutor;


public class DeviceServiceExecutorStub implements IDeviceServiceExecutor{
	private String serviceName;
	private String serviceProvider;
	@SuppressWarnings("unused")
	private String serviceSubmodelid;
	private List<Object> params;
	
	
	// whether the right service is invoked
	@Override
	public Object executeService(String servicename, String serviceProvider, String submodelid,  List<Object> params){
		this.serviceName = servicename;
		this.serviceProvider = serviceProvider;
		this.serviceSubmodelid = submodelid;
		this.params = params;
		System.out.printf("service: %s, executed by device: %s , parameters: ", servicename, serviceProvider);
		if (params.size() == 0) {
			System.out.println("[]");
		} else {
			for (Object p : params) {
				System.out.printf("%s, ", p);
			}
			System.out.println("");

		}
		
		return 1;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getServiceProvider() {
		return serviceProvider;
	}

	public List<Object> getParams() {
		return params;
	}

}
