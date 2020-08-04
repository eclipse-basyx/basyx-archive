package org.eclipse.basyx.testsuite.regression.aas.aggregator;

import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.aggregator.proxy.AASAggregatorProxy;

/**
 * Instantiate a concrete test suite for AASAggregator from the abstract test
 * suite
 * 
 * @author zhangzai
 *
 */
public class AASAggregatorSuiteWithDefinedURL extends AASAggregatorSuite {

	public static String url;

	@Override
	protected IAASAggregator getAggregator() {
		return new AASAggregatorProxy(url);
	}

}
