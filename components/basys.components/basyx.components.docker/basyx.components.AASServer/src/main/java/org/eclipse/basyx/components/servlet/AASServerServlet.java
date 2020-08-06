package org.eclipse.basyx.components.servlet;

import org.eclipse.basyx.aas.aggregator.AASAggregator;
import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

/**
 * A servlet containing the empty infrastructure needed to support receiving
 * AAS/Submodels by clients and hosting them
 * 
 * @author schnicke
 *
 */
public class AASServerServlet extends VABHTTPInterface<AASAggregatorProvider> {
	private static final long serialVersionUID = 1244938902937878401L;

	public AASServerServlet() {
		super(new AASAggregatorProvider(new AASAggregator()));
	}

}
