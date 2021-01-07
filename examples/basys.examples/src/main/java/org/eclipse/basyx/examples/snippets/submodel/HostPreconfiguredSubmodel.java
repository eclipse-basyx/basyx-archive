package org.eclipse.basyx.examples.snippets.submodel;

import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.servlet.submodel.SubmodelServlet;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.vab.protocol.http.server.AASHTTPServer;
import org.eclipse.basyx.vab.protocol.http.server.BaSyxContext;

/**
 * This snippet showcases how host a submodel on a standalone server, i.e. a
 * server that is preconfigured with a submodel and does not allow upload of
 * additional AAS/Submodels<br>
 * For instance, this can be used by a device manufacturer to provide a submodel
 * containing live sensor data in combination with a dynamic property (cf.
 * {@link org.eclipse.basyx.examples.snippets.property.CreateLambdaProperty
 * CreateDynamicProperty}). <br>
 * 
 * @author schnicke
 *
 */
public class HostPreconfiguredSubmodel {

	/**
	 * Creates, configures and starts an HTTP Server providing a preconfigured
	 * submodel
	 * 
	 * @param serverContext
	 *            the context configuration of the server
	 * @param sm
	 *            the submodel to be hosted
	 * @return the started server
	 */
	public static AASHTTPServer hostPreconfiguredSubmodel(BaSyxContextConfiguration serverContext, SubModel sm) {
		// Create the BaSyx context from the context configuration
		BaSyxContext context = serverContext.createBaSyxContext();

		// Create a new SubmodelServlet containing the passed submodel
		SubmodelServlet smServlet = new SubmodelServlet(sm);

		// Add the SubmodelServlet mapping to the context at the path "$SmIdShort"
		// Here, it possible to add further submodels using the same pattern
		context.addServletMapping("/" + sm.getIdShort() + "/*", smServlet);

		// Create and start a HTTP server with the context created above
		AASHTTPServer preconfiguredSmServer = new AASHTTPServer(context);
		preconfiguredSmServer.start();

		return preconfiguredSmServer;
	}

}
