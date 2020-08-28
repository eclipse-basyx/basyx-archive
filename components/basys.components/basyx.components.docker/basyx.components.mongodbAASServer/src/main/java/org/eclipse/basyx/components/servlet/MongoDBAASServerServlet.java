package org.eclipse.basyx.components.servlet;

import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.components.configuration.BaSyxMongoDBConfiguration;
import org.eclipse.basyx.components.mongodb.MongoDBAASAggregator;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

/**
 * A servlet containing the empty infrastructure needed to support receiving
 * AAS/Submodels by clients and hosting them for MongoDB backend
 * 
 * @author espen
 *
 */
public class MongoDBAASServerServlet extends VABHTTPInterface<AASAggregatorProvider> {
	private static final long serialVersionUID = 1244938902937878401L;

	public MongoDBAASServerServlet() {
		super(new AASAggregatorProvider(new MongoDBAASAggregator()));
	}

	public MongoDBAASServerServlet(BaSyxMongoDBConfiguration config) {
		super(new AASAggregatorProvider(new MongoDBAASAggregator(config)));
	}

}
