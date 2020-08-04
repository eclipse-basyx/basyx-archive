package org.eclipse.basyx.testsuite.regression.aas.aggregator;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

/**
 * The main class that executes the concrete test suite of the registry provider
 * 
 * @author zhangzai
 *
 */
public class AASAggregatorTestApplication {


	public static void main(String[] args) {
		// First argument is the inserted url
		String url = args[0];
		// Run junit test in a java application
		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));

		AASAggregatorSuiteWithDefinedURL.url = url;
		Result result = junit.run(AASAggregatorSuite.class);

		System.out.println("Finished. Result: Failures: " +
				result.getFailureCount() + ". Ignored: " +
				result.getIgnoreCount() + ". Tests run: " +
				result.getRunCount() + ". Time: " +
				result.getRunTime() + "ms.");
	}

}
