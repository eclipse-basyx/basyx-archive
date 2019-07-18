package org.eclipse.basyx.regression.support.processengine.servlet;


import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.regression.support.processengine.aas.DeviceAdministrationShellFactory;
import org.eclipse.basyx.regression.support.processengine.stubs.Coilcar;
import org.eclipse.basyx.regression.support.processengine.submodel.DeviceSubmodelFactory;
import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;



public class CoilcarAASServlet extends VABHTTPInterface<VABMultiSubmodelProvider<VABHashmapProvider>> {
	private static final long serialVersionUID = 1L;
	private String aasid = "coilcar";
	private String submodelid = "submodel1";

	public CoilcarAASServlet() {
		super(new VABMultiSubmodelProvider<>());
		
		AssetAdministrationShell coilcarAAS = new DeviceAdministrationShellFactory().create(aasid,  submodelid);
		// Set Id
		coilcarAAS.setId(aasid);
		SubModel coilcarSubmodel = new DeviceSubmodelFactory().create(submodelid, new Coilcar());
		
		getModelProvider().setAssetAdministrationShell(new VABHashmapProvider(coilcarAAS));
		getModelProvider().addSubmodel(submodelid, new VABHashmapProvider(coilcarSubmodel));
	}

}