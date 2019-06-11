package org.eclipse.basyx.regression.support.server;

import java.io.File;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.http.HttpServlet;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;



/**
 * Starter Class for Apache Tomcat 9.0.14 HTTP server that adds the provided servlets and respective mappings on startup.
 * 
 * @author pschorn
 *
 */
public class AASHTTPServer extends Thread {

	
	/**
	 * Apache Tomcat server reference
	 */
	protected Tomcat tomcat;
	
	
	
	/**
	 * Constructor
	 * 
	 * Create new Tomcat instance and add the provided servlet mappings
	 * 
	 * @param context Basyx context with of url mappings to HTTPServlet
	 */
	public AASHTTPServer(BaSyxContext context) {
		// Instantiate and setup Tomcat server
		tomcat = new Tomcat();
		tomcat.setPort(context.port);
		tomcat.setHostname("localhost");
		tomcat.getHost().setAppBase(".");

		// Create servlet context
    	// - Base path for resource files
    	File docBase = new File(context.docBasePath); //System.getProperty("java.io.tmpdir"));
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
        	Tomcat.addServlet(rootCtx, ""+servlet.hashCode(), servlet);
            rootCtx.addServletMapping(mapping, ""+servlet.hashCode());
        }
	}
	
	
	/**
	 * Run Method. Use .start() to start the server in a new thread to avoid blocking the main thread
	 */
	public void run() {
		System.out.println("Starting Tomcat.....");
        
		try {
			tomcat.stop();
			tomcat.start();
			System.out.println("Started BaSyx HTTP Server!");
			tomcat.getServer().await();
			
			
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This Method stops and destroys the tomcat instance. This is important since Tomcat would be already 
	 * bound to port 8080 when new tests are run that require a start of tomcat
	 */
	public void shutdown() {
		System.out.println("Shutting down BaSyx HTTP Server...");
		
		try {
			tomcat.stop();
			tomcat.destroy();
		} catch (LifecycleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
