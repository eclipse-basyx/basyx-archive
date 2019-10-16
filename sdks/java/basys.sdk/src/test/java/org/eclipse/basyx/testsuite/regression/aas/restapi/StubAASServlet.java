package org.eclipse.basyx.testsuite.regression.aas.restapi;

import java.util.Collections;

import org.eclipse.basyx.aas.factory.java.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.testsuite.regression.submodel.restapi.SimpleAASSubmodel;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

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

		getModelProvider().setAssetAdministrationShell(new AASModelProvider(aas));
		getModelProvider().addSubmodel(SMID, new SubModelProvider(new SimpleAASSubmodel(SMID)));
	}

}
