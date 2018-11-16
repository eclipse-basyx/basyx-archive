package org.eclipse.basyx.testsuite.support.backend.basyx.servers;

import org.eclipse.basyx.vab.backend.server.basyx.BaSyxTCPServer;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.Stub1AAS;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.Stub2AAS;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.MainSMSubmodel;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub1Submodel;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub2Submodel;
import org.eclipse.basyx.testsuite.support.vab.stub.elements.SimpleVABElement;



/**
 * BaSyx provider testsuite server providing a SimpleVABElement vab element
 * 
 * @author kuhn
 *
 */
public class AASServer {

	
	/**
	 * Main method
	 */
	public static void main(String[] args) {
		
		// Create IModelProvider that provides a VAB element
		VABHashmapProvider modelProvider = new VABHashmapProvider(new SimpleVABElement());

		// Create native BaSyx TCP server using default port
		BaSyxTCPServer<VABHashmapProvider> tcpServer = new BaSyxTCPServer<VABHashmapProvider>(modelProvider);
		
		// Start tcp server
		tcpServer.start();

		// Wait until TCP server completes (which will never happen) or do something else meaningful
		try {tcpServer.join();} catch (InterruptedException e) {e.printStackTrace();}
	}
}
