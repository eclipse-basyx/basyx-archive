/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.vab;

import org.eclipse.basyx.testsuite.regression.aas.aggregator.AASAggregatorSuite;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

/**
 * The main class that executes the test provider.
 * Structure is based on the TCK
 * 
 * @author espen
 *
 */
public class CppVABTestApplication {

	public static void main(String[] args) {
		// First argument is the inserted url
		String url = args[0];
		// Run junit test in a java application
		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));

		VABClientTest.url = url;
		Result result = junit.run(AASAggregatorSuite.class);

		System.out.println("Finished. Result: Failures: " +
				result.getFailureCount() + ". Ignored: " +
				result.getIgnoreCount() + ". Tests run: " +
				result.getRunCount() + ". Time: " +
				result.getRunTime() + "ms.");
	}

}
