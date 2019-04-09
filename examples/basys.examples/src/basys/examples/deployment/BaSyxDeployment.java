package basys.examples.deployment;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.regression.support.server.AASHTTPServerResource;
import org.eclipse.basyx.regression.support.server.BaSyxContext;
import org.junit.rules.ExternalResource;




/**
 * This class represents a BaSyx deployment with servers / servlets and assets for running BaSyx examples
 * 
 * @author kuhn
 *
 */
public class BaSyxDeployment extends ExternalResource {

	
	/**
	 * Store context objects
	 */
	protected Object[] contextComponents = null;
	
	
	/**
	 * Map runnable names to runnables
	 */
	protected Map<String, BaSyxContextRunnable> contectRunnablesByName = new HashMap<>();
	
	
	
	
	/**
	 * Constructor - accept parameter of type BaSyxContext or Runnable
	 */
	public BaSyxDeployment(Object... components) {
		// Store context objects
		contextComponents = components;
		
		// Store runnable objects by name
		for (Object obj: contextComponents) {
			// Check component type
			if (!(obj instanceof BaSyxContextRunnable)) continue;
			
			// Add BaSyxContextRunnable to context
			// - Cast object
			BaSyxContextRunnable contextRunnable = (BaSyxContextRunnable) obj;
			// - Add context runnable to map if it is named
			if (contextRunnable.getName() == null) continue;
			// - Add context runnable to map
			contectRunnablesByName.put(contextRunnable.getName(), contextRunnable);
		}
	}
	
	
	
    /**
     * Execute before a test case starts
     */
    protected void before() {
    	// Iterate context components
    	for (Object contextComponent: contextComponents) {
    		// Process BaSyx context objects that run in a tomcat server
    		if (contextComponent instanceof BaSyxContext) {
    			// Get HTTP server resource
    			AASHTTPServerResource resource = AASHTTPServerResource.getTestResource((BaSyxContext) contextComponent);
    			// - Invoke 'before' operation that starts the server
    			resource.before();
    			// - Continue loop
    			continue;
    		}
    		
    		// Process runnables
    		if (contextComponent instanceof BaSyxContextRunnable) {
    			// Execute runnable
    			((BaSyxContextRunnable) contextComponent).start();
    			// - Continue loop
    			continue;
    		}
    		
    		// Unknown deployment context
    		throw new UnknownContextComponentTypeException();
    	}
    }

    
    /**
     * Execute after test case ends
     */
    protected void after() {
    	// Iterate context components
    	for (Object contextComponent: contextComponents) {
    		// Process BaSyx context objects that run in a tomcat server
    		if (contextComponent instanceof BaSyxContext) {
    			// Get HTTP server resource
    			AASHTTPServerResource resource = AASHTTPServerResource.getTestResource((BaSyxContext) contextComponent);
    			// - Invoke 'before' operation that starts the server
    			resource.after();
    			// - Continue loop
    			continue;
    		}
    		
    		// Process runnables
    		if (contextComponent instanceof BaSyxContextRunnable) {
    			// Execute runnable
    			((BaSyxContextRunnable) contextComponent).stop();
    			// - Continue loop
    			continue;
    		}
    		
    		// Unknown deployment context
    		throw new UnknownContextComponentTypeException();
    	}
    }
    
    
    /**
     * Get context runnable by name
     */
    public BaSyxContextRunnable getRunnable(String name) {
    	return contectRunnablesByName.get(name);
    }
}

