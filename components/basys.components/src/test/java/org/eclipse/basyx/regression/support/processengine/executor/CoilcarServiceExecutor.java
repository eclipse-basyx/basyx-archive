package org.eclipse.basyx.regression.support.processengine.executor;

import java.util.List;

import org.eclipse.basyx.components.processengine.connector.DeviceServiceExecutor;
import org.eclipse.basyx.regression.support.processengine.SetupAAS;
import org.eclipse.basyx.vab.core.VABConnectionManager;


public class CoilcarServiceExecutor extends DeviceServiceExecutor {

	public CoilcarServiceExecutor(VABConnectionManager connectionmanager) {
		super(connectionmanager);
	}

	@Override
	public Object executeService(String serviceName, String deviceid, List<Object> params) throws Exception {
		
		return super.executeService(serviceName, deviceid, SetupAAS.submodelid, params);
	}

}
