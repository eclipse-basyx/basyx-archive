package org.eclipse.basyx.testsuite.support.backend.servers;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServlet;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 * Starter Class for Apache Tomcat 9.0.14 HTTP server that adds the provided servlets and respective mappings on startup.
 * @author pschorn
 *
 */
public class AASHTTPServer extends Thread {

	Tomcat tomcat;
	   
	/**
	 * Constructor
	 * 
	 * Create new Tomcat instance and add the provided servlet mappings
	 * 
	 * @param mappings hashmap of url mapping to HTTPServlet
	 */
	public AASHTTPServer(Map<String, HttpServlet> mappings) {
		tomcat = new Tomcat();
		tomcat.setPort(8080);
		tomcat.setHostname("localhost");
		tomcat.getHost().setAppBase(".");
		File docBase = new File(System.getProperty("java.io.tmpdir"));
        Context rootCtx = tomcat.addContext("/basys.sdk", docBase.getAbsolutePath()); 
        
        Iterator<Entry<String, HttpServlet>> it = mappings.entrySet().iterator();
        while (it.hasNext()) {
        	Entry<String, HttpServlet> entry = it.next();
        	String mapping = entry.getKey();
        	HttpServlet servlet = entry.getValue();
        	
        	// Add new Servlet and Mapping
        	Tomcat.addServlet(rootCtx, servlet.getClass().getSimpleName(), servlet.getClass().getName());
            rootCtx.addServletMapping(mapping, servlet.getClass().getSimpleName());
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
