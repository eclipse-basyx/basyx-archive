package org.eclipse.basyx.sandbox.components.cfgprovider.servlet;

import java.util.Properties;

import org.eclipse.basyx.components.provider.BaseConfiguredProvider;
import org.eclipse.basyx.sandbox.components.cfgprovider.CFGSubModelProvider;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;

/**
 * Servlet interface for configuration file sub model provider
 * 
 * @author kuhn
 *
 */
public class CFGSubModelProviderServlet extends AbstractCFGSubModelProviderServlet {

	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = -7525848804623194574L;


	@Override
	protected String getSubmodelId() {
		return BaseConfiguredProvider.buildBasyxCfgName(BaseConfiguredProvider.SUBMODELID);
	}

	@Override
	protected SubModelProvider createProvider(Properties properties) {
		return new CFGSubModelProvider(properties);
	}
}
