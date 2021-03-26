package org.eclipse.basyx.sandbox.components.cfgprovider.servlet;

import java.util.Properties;

import org.eclipse.basyx.sandbox.components.cfgprovider.RawCFGSubModelProvider;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;

/**
 * Servlet interface for configuration file sub model provider
 * 
 * @author kuhn
 *
 */
public class RawCFGSubModelProviderServlet extends AbstractCFGSubModelProviderServlet {

	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = -8132051635222485719L;


	@Override
	protected String getSubmodelId() {
		return Referable.IDSHORT;
	}


	@Override
	protected SubModelProvider createProvider(Properties properties) {
		return new RawCFGSubModelProvider(properties);
	}
}
