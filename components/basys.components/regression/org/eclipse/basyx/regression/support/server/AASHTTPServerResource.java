package org.eclipse.basyx.regression.support.server;

import java.util.HashMap;
import java.util.Map;

import org.junit.rules.ExternalResource;



/**
 * This class initializes Tomcat Server and required servlets for all HTTP test classes in this project. 
 * The factory pattern makes sure Tomcat is only started once and teared down after only after all tests have run.
 * 
 * @author pschorn
 *
 */
public class AASHTTPServerResource extends ExternalResource {
	
	
	/**
	 * Active server references
	 */
    //private static int refCount = 0;
    private static Map<Integer, Integer> refCount = new HashMap<>();

    
    /**
     * Server resource instance
     */
    private static AASHTTPServerResource currentInstance;
    
    
    /**
     * Tomcat server instance
     */
    private AASHTTPServer server;
    
    
    /**
     * Servlet context
     */
    protected BaSyxContext context = null;
    
    
   
    
    
    /**
     * Check if the active server context contains the given context
     */
    protected static boolean containsContext(BaSyxContext requestedContext) {
    	// Check presence of all keys
        if (currentInstance.context.keySet().containsAll(requestedContext.keySet())) return true;
        
        // Active server does not contain requested context
        return false;
    }
    
    
    /**
     * Get a test server with a requested context
     * 
     * @param requestedContext
     * @return
     */
    public static AASHTTPServerResource getTestResource(BaSyxContext requestedContext) {
    	// Check if map contains requested port number
    	if (!refCount.containsKey(requestedContext.getPort())) refCount.put(requestedContext.getPort(), 0);
    	
    	// Create server resource if no resource is active at the moment
        if (refCount.get(requestedContext.getPort()) == 0) {
            currentInstance = new AASHTTPServerResource(requestedContext);
        }
        
        // Check if running server conforms to requested context
        // - Check if server contains requested context
        if (containsContext(requestedContext));
        
        // Return instance
        return currentInstance;
    }

    
    
    
    /**
     * Constructor
     */
    private AASHTTPServerResource(BaSyxContext requestedContext) {
    	// Store context reference
    	context = requestedContext;
    }
    
    
    /**
     * Execute before a test case starts
     */
    public void before() {
    	// Try to instantiate a new Tomcat server if refCounter is 0
        try {
            if (refCount.get(context.getPort()) == 0) {
        		// Instantiate and start HTTP server
        		server = new AASHTTPServer(context);
        		server.start();
            }
        }
        
        // Always execute this block
        finally {
        	// Increment reference counter
        	refCount.put(context.getPort(), refCount.get(context.getPort())+1);
        }
    }

    
    /**
     * Execute after test case ends
     */
    public void after() {
        // Decrement reference counter
    	refCount.put(context.getPort(), refCount.get(context.getPort())-1);
        
        // Shutdown server if reference counter reaches zero
        if (refCount.get(context.getPort()) == 0) {
            server.shutdown();
        }
    }
}
