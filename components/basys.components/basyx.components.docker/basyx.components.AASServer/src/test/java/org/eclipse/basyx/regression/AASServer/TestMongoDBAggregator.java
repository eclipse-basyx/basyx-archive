/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.regression.AASServer;

import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.components.aas.mongodb.MongoDBAASAggregator;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.testsuite.regression.aas.aggregator.AASAggregatorSuite;

public class TestMongoDBAggregator extends AASAggregatorSuite {

	@Override
	protected IAASAggregator getAggregator() {
		MongoDBAASAggregator aggregator = new MongoDBAASAggregator(BaSyxContextConfiguration.DEFAULT_CONFIG_PATH);
		aggregator.reset();

		return aggregator;
	}
}
