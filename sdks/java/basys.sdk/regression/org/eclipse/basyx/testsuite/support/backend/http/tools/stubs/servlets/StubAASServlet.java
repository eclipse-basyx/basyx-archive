package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets;

import java.util.Collections;

import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell_;
import org.eclipse.basyx.testsuite.support.aas.vab.stub.elements.SimpleAASSubmodel;
import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;

public class StubAASServlet extends VABHTTPInterface<VABMultiSubmodelProvider<VABHashmapProvider>> {
	private static final long serialVersionUID = 8859337501045845823L;

	// Used ids
	public static final String aasId = "urn:StubAAS";
	public static final String smId = "urn:StubAAS:sm";

	public StubAASServlet() {
		super(new VABMultiSubmodelProvider<>());

		MetaModelElementFactory factory = new MetaModelElementFactory();
		AssetAdministrationShell_ aas = factory.create(new AssetAdministrationShell_(), Collections.singleton(smId));
		aas.put("idShort", aasId);

		getModelProvider().setAssetAdministrationShell(new VABHashmapProvider(aas));
		getModelProvider().addSubmodel(smId, new VABHashmapProvider(new SimpleAASSubmodel(smId)));
	}

}
