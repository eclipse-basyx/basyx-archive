package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets;

import java.util.Collections;

import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.testsuite.support.aas.vab.stub.elements.SimpleAASSubmodel;
import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;

public class StubAASServlet extends VABHTTPInterface<VABMultiSubmodelProvider> {
	private static final long serialVersionUID = 8859337501045845823L;

	// Used ids
	public static final String aasId = "urn:StubAAS";
	public static final String smId = "urn:StubAAS:sm";

	public StubAASServlet() {
		super(new VABMultiSubmodelProvider());

		MetaModelElementFactory factory = new MetaModelElementFactory();
		AssetAdministrationShell aas = factory.create(new AssetAdministrationShell(), Collections.singleton(smId));
		aas.put(Referable.IDSHORT, aasId);

		getModelProvider().setAssetAdministrationShell(new VirtualPathModelProvider(aas));
		getModelProvider().addSubmodel(smId, new VirtualPathModelProvider(new SimpleAASSubmodel(smId)));
	}

}
