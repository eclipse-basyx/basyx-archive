package org.eclipse.basyx.regression.support.server;

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
    private static int refCount = 0;

    
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
    	// Create server resource if no resource is active at the moment
        if (refCount == 0) {
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
    	context     = requestedContext;
    }
    
    
    /**
     * Execute before a test case starts
     */
    protected void before() {
    	// Try to instantiate a new Tomcat server if refCounter is 0
        try {
            if (refCount == 0) {
        		// Instantiate and start HTTP server
        		server = new AASHTTPServer(context);
        		server.start();
            }
        }
        
        // Always execute this block
        finally {
        	// Increment reference counter
            refCount++;
        }
    }

    
    /**
     * Execute after test case ends
     */
    protected void after() {
        // Decrement reference counter
        refCount--;
        
        // Shutdown server if reference counter reaches zero
        if (refCount == 0) {
            server.shutdown();
        }
    }
}
