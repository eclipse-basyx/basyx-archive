package org.eclipse.basyx.regression.support.processengine;

import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.regression.support.processengine.aas.DeviceAdministrationShellFactory;
import org.eclipse.basyx.regression.support.processengine.stubs.Coilcar;
import org.eclipse.basyx.regression.support.processengine.submodel.DeviceSubmodelFactory;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;

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
		provider.addSubmodel(submodelid, new VirtualPathModelProvider(sm));
		provider.setAssetAdministrationShell(new VirtualPathModelProvider(aas));

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
