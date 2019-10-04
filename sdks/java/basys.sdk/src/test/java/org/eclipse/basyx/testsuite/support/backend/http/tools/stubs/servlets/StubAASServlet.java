package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets;

import java.util.Collections;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.impl.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.testsuite.support.aas.vab.stub.elements.SimpleAASSubmodel;
import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;

public class StubAASServlet extends VABHTTPInterface<VABMultiSubmodelProvider> {
	private static final long serialVersionUID = 8859337501045845823L;

	// Used short ids
	public static final String AASID = "StubAAS";
	public static final String SMID = "StubSM";

	// Used URNs
	public static final ModelUrn AASURN = new ModelUrn("urn:fhg:es.iese:aas:1:1:submodel");

	public StubAASServlet() {
		super(new VABMultiSubmodelProvider());

		MetaModelElementFactory factory = new MetaModelElementFactory();
		AssetAdministrationShell aas = factory.create(new AssetAdministrationShell(), Collections.singleton(SMID));
		aas.put(Referable.IDSHORT, AASID);

		getModelProvider().setAssetAdministrationShell(new VirtualPathModelProvider(aas));
		getModelProvider().addSubmodel(SMID, new VirtualPathModelProvider(new SimpleAASSubmodel(SMID)));
	}

}
