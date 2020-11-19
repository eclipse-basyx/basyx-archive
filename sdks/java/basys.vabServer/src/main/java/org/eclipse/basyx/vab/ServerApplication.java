package org.eclipse.basyx.vab;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.basyx.server.BaSyxTCPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A server application that provides a generic VAB-object that can be reset
 * 
 * @author espen
 *
 */
public class ServerApplication {
	private static Logger logger = LoggerFactory.getLogger(ServerApplication.class);

	private static BaSyxTCPServer<IModelProvider> server;

	public static void main(String[] args) {
		int port = 8383;
		if (args.length > 0) {
			// First argument is the port
			port = Integer.parseInt(args[0]);
			logger.info("Starting server at port " + port);
		} else {
			logger.info("Starting server at default port " + port);
		}

		server = new BaSyxTCPServer<>(new SimpleVABProvider(), port);
		server.start();
	}
}
