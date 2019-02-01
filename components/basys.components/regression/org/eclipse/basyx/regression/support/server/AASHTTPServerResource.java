package org.eclipse.basyx.regression.support.server;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.eclipse.basyx.components.servlets.CFGSubModelProviderServlet;
import org.eclipse.basyx.components.servlets.RawCFGSubModelProviderServlet;
import org.eclipse.basyx.components.servlets.SQLDirectoryServlet;
import org.eclipse.basyx.components.servlets.SQLSubModelProviderServlet;
import org.eclipse.basyx.components.servlets.StaticCFGDirectoryServlet;
import org.eclipse.basyx.components.servlets.XMLXQueryServlet;
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
    
    /**
     * Main method if you would like to start the Server manually. Stop the server by typing 'Please stop tomcat' (without quotation marks)
     * @param args
     * @throws IOException 
     */
    public static void main(String args[]) throws IOException {
    	
    	// Start server and allocate resource
    	AASHTTPServerResource resource = getTestResource();
    	resource.before();
    	
    	// Prompt for user input
    	InputStreamReader in = new InputStreamReader(System.in);
    	BufferedReader keyboard = new BufferedReader(in);

        System.out.println("Enter 'Please stop tomcat' to stop the server.");
        while (true) {
        	String command = keyboard.readLine();
        	if (command.equals("Please stop tomcat")) {
        		resource.after();
        		break;
        	} else {
        		System.out.println("Command " + command + " not recognized!");
        	}
        }
    }
   
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
        		servlets.put("/Testsuite/components/BaSys/1.0/provider/sqlsm/*", new SQLSubModelProviderServlet());
        		servlets.put("/Testsuite/components/BaSys/1.0/provider/cfgsm/*", new CFGSubModelProviderServlet());
        		servlets.put("/Testsuite/components/BaSys/1.0/provider/rawcfgsm/*", new RawCFGSubModelProviderServlet());
        		servlets.put("/Testsuite/Directory/CFGFile/*", new StaticCFGDirectoryServlet());
        		servlets.put("/Testsuite/Directory/SQL/*", new SQLDirectoryServlet());
        		servlets.put("/Testsuite/components/BaSys/1.0/provider/xmlxquery/*", new XMLXQueryServlet());
        		
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