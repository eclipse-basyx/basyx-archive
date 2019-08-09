package org.eclipse.basyx.components.servlet.submodel;

import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;

/**
 * A sub model servlet class that exports a given sub model
 * 
 * @author kuhn
 *
 */
public class SubmodelServlet extends VABHTTPInterface<VABMultiSubmodelProvider> {

	/**
	 * ID of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public SubmodelServlet(SubModel exportedModel) {
		// Invoke base constructor
		super(new VABMultiSubmodelProvider());

		// Create dummy AAS
		AssetAdministrationShell aas = new AssetAdministrationShell();
		aas.setId("---");

		// Add provides sub model
		getModelProvider().setAssetAdministrationShell(new VirtualPathModelProvider(aas));
		getModelProvider().addSubmodel(exportedModel.getId(), new VirtualPathModelProvider(exportedModel));
	}
}
