package org.eclipse.basyx.regression.support.processengine.executor;

import java.util.List;

import org.eclipse.basyx.aas.manager.api.IAssetAdministrationShellManager;
import org.eclipse.basyx.components.processengine.connector.DeviceServiceExecutor;
import org.eclipse.basyx.regression.support.processengine.SetupAAS;

/**
 * Service executor used by the process engine, defined for the coilcar test case
 * 
 * @author zhangzai
 *
 */
public class CoilcarServiceExecutor extends DeviceServiceExecutor {
	/**
	 * Constructor with defined connection manager 
	 * 
	 * @param connectionmanager for VAB Connection
	 */
	public CoilcarServiceExecutor(IAssetAdministrationShellManager manager) {
		super(manager);
	}

	/**
	 * Execute the service with the given information, sub-model id for this case is known as "submodle1"
	 */
	@Override
	public Object executeService(String serviceName, String deviceid, List<Object> params) throws Exception {
		
		return super.executeService(serviceName, deviceid, SetupAAS.submodelid, params);
	}

}
