package org.eclipse.basyx.regression.support.processengine;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.eclipse.basyx.regression.support.processengine.servlet.CoilcarAASServlet;
import org.eclipse.basyx.testsuite.support.backend.servers.AASHTTPServer;
import org.junit.rules.ExternalResource;


public class SetupHTTResource extends ExternalResource {
	 private static int refCount = 0;

	 private static SetupHTTResource currentInstance;
	    
	 private AASHTTPServer server;

	 public static SetupHTTResource getTestResource () {
	     if (refCount == 0) {
	         currentInstance = new SetupHTTResource();
	     }
	     return currentInstance;
	 }

	 protected void before() {
	     try {
	    	 if (refCount == 0) {
           System.out.println("Do actual TestResources init");
           
           Map<String, HttpServlet> servlets = new HashMap<String, HttpServlet>();
           servlets.put("/Testsuite/coilcar/*", new CoilcarAASServlet());
       		
       		server = new AASHTTPServer(servlets);
       		server.start();
       		
           }
	     }
	     finally {
           refCount++;
	     }
	 }

   protected void after() {
       System.out.println("TestResources after");
       refCount--;
       if (refCount == 0) {
           System.out.println("Do actual TestResources destroy");
           
           server.shutdown();
       }
   }
}
