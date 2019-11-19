package org.eclipse.basyx.regression.support.processengine;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.regression.support.processengine.aas.DeviceAdministrationShellFactory;
import org.eclipse.basyx.regression.support.processengine.stubs.Coilcar;
import org.eclipse.basyx.regression.support.processengine.submodel.DeviceSubmodelFactory;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;

/**
 * Create AAS and VAB connection stub (without communication protocol for test purpose)
 */

public class SetupAAS {

	public static String aasid = "coilcar";
	public static String submodelid = "submodel1";
	private static Coilcar coilcar = new Coilcar();
	private static VABConnectionManagerStub connectionStub;

	static {
		AssetAdministrationShell aas = new DeviceAdministrationShellFactory().create(aasid, submodelid);
		SubModel sm = new DeviceSubmodelFactory().create(submodelid, coilcar);

		VABMultiSubmodelProvider provider = new VABMultiSubmodelProvider();
		provider.addSubmodel(submodelid, new SubModelProvider(sm));
		provider.setAssetAdministrationShell(new AASModelProvider(aas));

		// setup the connection-manager with the model-provider
		connectionStub = new VABConnectionManagerStub();
		connectionStub.addProvider(submodelid, "", provider);
		connectionStub.addProvider(aasid, "", provider);
	}

	public static VABConnectionManagerStub getConnectionStub() {
		return connectionStub;
	}

	public static String getSubmodelid() {
		return submodelid;
	}

}
