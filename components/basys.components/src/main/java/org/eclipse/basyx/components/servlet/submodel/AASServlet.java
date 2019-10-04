package org.eclipse.basyx.components.servlet.submodel;

import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;

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

		getModelProvider().setAssetAdministrationShell(new VirtualPathModelProvider(exportedAAS));
	}
}
