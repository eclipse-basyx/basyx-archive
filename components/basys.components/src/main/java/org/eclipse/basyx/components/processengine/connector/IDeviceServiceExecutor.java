package org.eclipse.basyx.components.processengine.connector;

import java.util.List;

public interface IDeviceServiceExecutor {
	public Object executeService( String servicename, String serviceProvider,String submodelid,  List<Object> params) throws Exception;
	public Object executeService( String serviceName, String deviceid, List<Object> params) throws Exception;
	public String getServiceName();
	public String getServiceProvider();
	public List<Object> getParams();
}
