package org.eclipse.basyx.examples.scenarios.device;

import java.util.function.Supplier;



/**
 * Base class for all BaSyx examples
 * 
 * @author kuhn
 */
public class BaSyxExampleScenario {

	
	
	/**
	 * Wait for a condition
	 */
	protected void waitfor(Supplier<Boolean> function) {
		while (!function.get()) Thread.yield();
	}
}
