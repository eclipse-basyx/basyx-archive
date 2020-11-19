package org.eclipse.basyx.components.servlet.aas;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.MultiAASProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.support.bundle.AASBundle;
import org.eclipse.basyx.vab.protocol.http.server.VABHTTPInterface;

/**
 * Servlet providing multiple AAS based on a passed {@link AASBundle}
 * 
 * @author schnicke
 *
 */
public class AASBundleServlet extends VABHTTPInterface<MultiAASProvider> {
	private static final long serialVersionUID = 4441135540490088430L;

	/**
	 * Creates a servlet hosting the AAS and its submodels specified in the bundle
	 * 
	 * @param bundle
	 */
	public AASBundleServlet(AASBundle bundle) {
		this(Collections.singleton(bundle));
	}

	/**
	 * Creates a servlet hosting multiple AAS and their submodels as specified in
	 * the bundle list
	 * 
	 * @param bundles
	 */
	public AASBundleServlet(Collection<AASBundle> bundles) {
		super(new MultiAASProvider());

		MultiAASProvider multiAASProvider = getModelProvider();

		for (AASBundle bundle : bundles) {
			multiAASProvider.addMultiSubmodelProvider(bundle.getAAS().getIdShort(), createMultiSubmodelProvider(bundle));
		}
	}

	/**
	 * Creates the MultiSubmodelProvider based on a single bundle
	 * 
	 * @param bundle
	 * @return
	 */
	private VABMultiSubmodelProvider createMultiSubmodelProvider(AASBundle bundle) {
		VABMultiSubmodelProvider provider = new VABMultiSubmodelProvider();
		IAssetAdministrationShell shell = bundle.getAAS();

		// Check for correct type
		if (!(shell instanceof AssetAdministrationShell)) {
			throw new RuntimeException("Only instances of AssetAdministrationShell are allowed here");
		}

		provider.setAssetAdministrationShell(new AASModelProvider((AssetAdministrationShell) shell));
		for (ISubModel sm : bundle.getSubmodels()) {

			if (!(sm instanceof SubModel)) {
				throw new RuntimeException("Only instances of SubModel are allowed here");
			}

			provider.addSubmodel(new SubModelProvider((SubModel) sm));
		}
		return provider;
	}
}
