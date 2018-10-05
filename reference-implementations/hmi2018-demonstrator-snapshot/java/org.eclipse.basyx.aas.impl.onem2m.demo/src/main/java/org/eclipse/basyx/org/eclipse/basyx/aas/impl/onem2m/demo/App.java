package org.eclipse.basyx.org.eclipse.basyx.aas.impl.onem2m.demo;

import org.eclipse.basyx.aas.impl.onem2m.OneM2MAssetAdministrationShellManager;
import org.eclipse.basyx.aas.impl.onem2m.managed.AASHandler;
import org.eclipse.basyx.onem2m.clients.OneM2MHttpClient;
import org.eclipse.basyx.onem2m.clients.OneM2MHttpClientConfig;
import org.eclipse.basyx.onem2m.clients.OneM2MHttpClientConfig.Protocol;
import org.eclipse.basyx.onem2m.manager.OneM2MResourceExplorer;
import org.eclipse.basyx.onem2m.manager.OneM2MResourceManager;

/**
 * Simple program that shows how operations of a device can be executed. First, 
 * the BaSys middleware (outdated version, from HMI2018) is initialized. Here, Eclipse oM2M 
 * is used as underlying middleware technology. Therefore, a oneM2M client (Eclipse oM2M uses 
 * oneM2M as communication protocol) has to be initialized before.  
 *
 */
public class App 
{    
		
	public static void main( String[] args ) throws Exception {
		
		// Disable Jetty log output on console
    	System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
    	System.setProperty("org.eclipse.jetty.LEVEL", "OFF");

	    OneM2MHttpClient client = null;
		OneM2MResourceManager oneM2MresourceManager = null;
		OneM2MResourceExplorer oneM2MresourceExplorer = null;
		OneM2MAssetAdministrationShellManager aasManager = null;

		// Initialized oneM2M client
		// Eclipse oM2M must be deployed on localhost. Moreover, port must be 8282 (change to 8080 if needed; 8080 is default port of Eclipse oM2M)
		client = new OneM2MHttpClient(new OneM2MHttpClientConfig(Protocol.HTTP, "127.0.0.1", 8282, "admin:admin")); 
		client.start();
		oneM2MresourceManager = new OneM2MResourceManager(client);
		oneM2MresourceManager.startSubscriptionHandler("demosubscriptionhandler", "127.0.0.1", 51338);		
		oneM2MresourceExplorer = new OneM2MResourceExplorer(client);
		
		// Initialization of the oneM2M implementation of the Basyx's asset administration shell manager 
		aasManager = new OneM2MAssetAdministrationShellManager(oneM2MresourceManager, oneM2MresourceExplorer);		
		AASHandler aasHandler = new AASHandler(oneM2MresourceManager);

		// Initialization of an example device 
		Device device = new Device(aasManager, aasHandler);
		device.init();
			    	    
	    for (int i = 0; i < 10; ++i) {
	    	// The device offers a "sum"-operation. The index of the for loop is used as parameter. 
	    	// Keep in mind: In a real world scenario, a device's operation would typically trigger some I/Os etc.  
	    	Object[] params = {new Integer(i), new Integer(i)};
	    	Integer retval = (Integer) aasManager.callOperation(Device.ID, Device.SUBMODEL_NAME, Device.OPERATION_NAME, params, 10000);
	    	System.out.println("Result: " + retval);
	    }
	   	    
	    //device.cleanup();
		client.stop();
		oneM2MresourceManager.stopSubscriptionHandler();
	}
    
}
