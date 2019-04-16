package basys.examples.example;

import java.util.function.Supplier;



/**
 * Base class for all BaSyx examples
 * 
 * @author kuhn
 */
public class BaSyxExample {

	
	
	/**
	 * Wait for a condition
	 */
	protected void waitfor(Supplier<Boolean> function) {
		while (!function.get()) Thread.yield();
	}
}
