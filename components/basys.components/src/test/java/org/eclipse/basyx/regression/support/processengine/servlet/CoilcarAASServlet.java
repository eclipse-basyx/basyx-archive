package org.eclipse.basyx.regression.support.processengine.servlet;

import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.regression.support.processengine.aas.DeviceAdministrationShellFactory;
import org.eclipse.basyx.regression.support.processengine.stubs.Coilcar;
import org.eclipse.basyx.regression.support.processengine.submodel.DeviceSubmodelFactory;
import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;

/**
 * Servlet for device aas
 * 
 * @author zhangzai
 *
 */
public class CoilcarAASServlet extends VABHTTPInterface<VABMultiSubmodelProvider> {
	private static final long serialVersionUID = 1L;
	private String aasid = "coilcar";
	private String submodelid = "submodel1";

	public CoilcarAASServlet() {
		super(new VABMultiSubmodelProvider());

		// Create the aas
		AssetAdministrationShell coilcarAAS = new DeviceAdministrationShellFactory().create(aasid, submodelid);

		// Set aas Id
		coilcarAAS.setId(aasid);

		// Create the sub-model
		SubModel coilcarSubmodel = new DeviceSubmodelFactory().create(submodelid, new Coilcar());

		getModelProvider().setAssetAdministrationShell(new VirtualPathModelProvider(coilcarAAS));
		getModelProvider().addSubmodel(submodelid, new VirtualPathModelProvider(coilcarSubmodel));
	}

}