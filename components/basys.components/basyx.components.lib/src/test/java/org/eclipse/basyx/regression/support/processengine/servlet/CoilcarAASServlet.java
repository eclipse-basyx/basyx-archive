package org.eclipse.basyx.regression.support.processengine.servlet;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.regression.support.processengine.aas.DeviceAdministrationShellFactory;
import org.eclipse.basyx.regression.support.processengine.stubs.Coilcar;
import org.eclipse.basyx.regression.support.processengine.submodel.DeviceSubmodelFactory;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

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
		coilcarAAS.setIdShort(aasid);

		// Create the sub-model
		SubModel coilcarSubmodel = new DeviceSubmodelFactory().create(submodelid, new Coilcar());

		getModelProvider().setAssetAdministrationShell(new AASModelProvider(coilcarAAS));
		getModelProvider().addSubmodel(new SubModelProvider(coilcarSubmodel));
	}

}