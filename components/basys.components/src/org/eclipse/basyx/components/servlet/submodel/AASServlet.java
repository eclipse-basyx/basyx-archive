package org.eclipse.basyx.components.servlet.submodel;

import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;



/**
 * AAS servlet class that exports a given Asset Administration Shell 
 * 
 * @author kuhn
 *
 */
public class AASServlet extends VABHTTPInterface<VABMultiSubmodelProvider<VABHashmapProvider>> {

	
	/**
	 * ID of serialized instances
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * Constructor
	 */
	public AASServlet(AssetAdministrationShell exportedAAS) {
		// Invoke base constructor
		super(new VABMultiSubmodelProvider<>());

		// Add provides sub model
		getModelProvider().setAssetAdministrationShell(new VABHashmapProvider(exportedAAS));
	}
}
