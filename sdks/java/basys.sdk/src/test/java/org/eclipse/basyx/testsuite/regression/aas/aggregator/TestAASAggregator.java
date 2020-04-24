package org.eclipse.basyx.testsuite.regression.aas.aggregator;

import org.eclipse.basyx.aas.aggregator.AASAggregator;
import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;

/**
 * Tests the AASAggregator implementation.
 * 
 * @author schnicke
 *
 */
public class TestAASAggregator extends AASAggregatorSuite {
	@Override
	protected IAASAggregator getAggregator() {
		return new AASAggregator();
	}
}
