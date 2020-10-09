package org.eclipse.basyx.components.aas.servlet;

import org.eclipse.basyx.aas.aggregator.AASAggregator;
import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

/**
 * A servlet containing the empty infrastructure needed to support receiving
 * AAS/Submodels by clients and hosting them
 * 
 * @author schnicke
 *
 */
public class AASAggregatorServlet extends VABHTTPInterface<AASAggregatorProvider> {
	private static final long serialVersionUID = 1244938902937878401L;

	public AASAggregatorServlet() {
		super(new AASAggregatorProvider(new AASAggregator()));
	}

	public AASAggregatorServlet(IAASAggregator aggregator) {
		super(new AASAggregatorProvider(aggregator));
	}
}
