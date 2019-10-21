package org.eclipse.basyx.vab.protocol.http.server;

import java.io.File;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.http.HttpServlet;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.startup.Tomcat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Starter Class for Apache Tomcat 8.0.53 HTTP server that adds the provided servlets and respective mappings on startup.
 * 
 * @author pschorn, espen
 * 
 */
public class AASHTTPServer {
	
	private static Logger logger = LoggerFactory.getLogger(AASHTTPServer.class);
	
	private Tomcat tomcat;
	   
	/**
	 * Constructor
	 * 
	 * Create new Tomcat instance and add the provided servlet mappings
	 * 
	 * @param context
	 *            Basyx context with of url mappings to HTTPServlet
	 */
	public AASHTTPServer(BaSyxContext context) {
		// Instantiate and setup Tomcat server
		tomcat = new Tomcat();
		tomcat.setPort(context.port);
		tomcat.setHostname(context.hostname);
		tomcat.getHost().setAppBase(".");

		// Create servlet context
		// - Base path for resource files
		File docBase = new File(context.docBasePath); // System.getProperty("java.io.tmpdir"));
		// - Create context for servlets
		Context rootCtx = tomcat.addContext(context.contextPath, docBase.getAbsolutePath());

		// Iterate all servlets in context
		Iterator<Entry<String, HttpServlet>> it = context.entrySet().iterator();
		while (it.hasNext()) {
			// Servlet entry
			Entry<String, HttpServlet> entry = it.next();

			// Servlet mapping
			String mapping = entry.getKey();
			HttpServlet servlet = entry.getValue();

			// Add new Servlet and Mapping to tomcat environment
			Tomcat.addServlet(rootCtx, Integer.toString(servlet.hashCode()), servlet);
			rootCtx.addServletMappingDecoded(mapping, Integer.toString(servlet.hashCode()));
		}
	}
	
	/**
	 * Starts the server in a new thread to avoid blocking the main thread
	 */
	public void start() {
		logger.trace("Starting Tomcat.....");
        
		Thread serverThread = new Thread(() -> {
			try {
				tomcat.stop();

				// Adds listener that notifies the tomcat object when the server has started
				tomcat.getServer().addLifecycleListener(new LifecycleListener() {
					@Override
					public void lifecycleEvent(LifecycleEvent event) {
						if (event.getLifecycle().getState() == LifecycleState.STARTED) {
							synchronized (tomcat) {
								tomcat.notifyAll();
							}
						}
					}
				});

				tomcat.start();
				
				// Keeps the server thread alive until the server is shut down
				tomcat.getServer().await();
			} catch (LifecycleException e) {
				logger.error("Exception in start", e);
			}
		});
		serverThread.start();

		synchronized (tomcat) {
			try {
				tomcat.wait();
			} catch (InterruptedException e) {
				logger.error("Exception in start", e);
			}
		}
	}
	
	/**
	 * This Method stops and destroys the tomcat instance. This is important since Tomcat would be already 
	 * bound to port 8080 when new tests are run that require a start of tomcat
	 */
	public void shutdown() {
		logger.trace("Shutting down BaSyx HTTP Server...");
		
		try {
			tomcat.stop();
			tomcat.destroy();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			logger.error("Exception in shutdown", e);
		}
	}
	
	
}
