package org.eclipse.basyx.testsuite.support.backend.servers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets.StubAASServlet;
import org.eclipse.basyx.testsuite.support.vab.stub.servlet.SimpleVABElementServlet;
import org.junit.rules.ExternalResource;

/**
 * This class initializes Tomcat Server and required servlets for all HTTP test classes in this project. 
 * The factory pattern makes sure Tomcat is only started once and teared down after only after all tests have run.
 * @author pschorn
 *
 */
public class AASHTTPServerResource extends ExternalResource {
    private static int refCount = 0;

    private static AASHTTPServerResource currentInstance;
    
    private AASHTTPServer server;

    public static AASHTTPServerResource getTestResource () {
        if (refCount == 0) {
            currentInstance = new AASHTTPServerResource();
        }
        return currentInstance;
    }

    protected void before() {
        try {
            if (refCount == 0) {
                System.out.println("Do actual TestResources init");
                
                Map<String, HttpServlet> servlets = new HashMap<String, HttpServlet>();
        		servlets.put("/Testsuite/SimpleVAB/*", new SimpleVABElementServlet());
        		servlets.put("/Testsuite/StubAAS/*", new StubAASServlet());
        		
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