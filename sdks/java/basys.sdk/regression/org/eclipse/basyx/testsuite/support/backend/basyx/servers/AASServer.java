package org.eclipse.basyx.testsuite.support.backend.basyx.servers;

import org.eclipse.basyx.aas.backend.modelprovider.basyx.BaSyxTCPServer;
import org.eclipse.basyx.aas.impl.provider.JavaObjectProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.Stub1AAS;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub1Submodel;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub2Submodel;



/**
 * BaSyx provider for Testsuite AAS
 * 
 * @author kuhn
 *
 */
public class AASServer {

	
	/**
	 * Main method
	 */
	public static void main(String[] args) {
		// Create IModelProvider and setup that provides the AAS
		JavaObjectProvider modelProvider = new JavaObjectProvider();
		// - Register provided models and AAS
		modelProvider.addModel(new Stub1AAS());
		modelProvider.addModel(new Stub1Submodel(), "Stub1AAS");
		modelProvider.addModel(new Stub2Submodel(), "Stub1AAS");

		// Create native BaSyx TCP server using default port
		BaSyxTCPServer<JavaObjectProvider> tcpServer = new BaSyxTCPServer<JavaObjectProvider>(modelProvider);
		
		// Start tcp server
		tcpServer.start();

		// Wait until TCP server completes (which will never happen) or do something else meaningful
		try {tcpServer.join();} catch (InterruptedException e) {e.printStackTrace();}
	}
}
