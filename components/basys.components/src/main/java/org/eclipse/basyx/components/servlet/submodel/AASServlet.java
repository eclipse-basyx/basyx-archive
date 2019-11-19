package org.eclipse.basyx.components.servlet.submodel;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

/**
 * AAS servlet class that exports a given Asset Administration Shell
 * 
 * @author kuhn
 *
 */
public class AASServlet extends VABHTTPInterface<VABMultiSubmodelProvider> {

	/**
	 * ID of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor - based on a VABMultiSubmodelProvider
	 */
	public AASServlet() {
		super(new VABMultiSubmodelProvider());
	}

	/**
	 * Constructor for directly creating the AAS in the provider
	 */
	public AASServlet(AssetAdministrationShell exportedAAS) {
		this();
		getModelProvider().setAssetAdministrationShell(new AASModelProvider(exportedAAS));
	}
}
