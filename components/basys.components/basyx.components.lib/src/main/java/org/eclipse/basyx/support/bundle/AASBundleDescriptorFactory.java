package org.eclipse.basyx.support.bundle;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.components.servlet.aas.AASBundleServlet;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;

/**
 * Helper class that supports AASDescriptor utilization for an AASBundle under
 * the assumption, that is is hosted using {@link AASBundleServlet}
 * 
 * @author schnicke
 *
 */
public class AASBundleDescriptorFactory {
	/**
	 * Creates the AASDescriptor for the given bundle and hostPath
	 * @param bundle
	 * @param hostBasePath the hostBasePath the  {@link AASBundleServlet} will be hosted on
	 * @return
	 */
	public static AASDescriptor createAASDescriptor(AASBundle bundle, String hostBasePath) {
		// Normalize hostBasePath to ensure consistent usage of /
		String nHostBasePath = VABPathTools.stripSlashes(hostBasePath);

		// Create AASDescriptor
		String endpointId = bundle.getAAS().getIdentification().getId();
		endpointId = VABPathTools.encodePathElement(endpointId);
		String aasBase = VABPathTools.concatenatePaths(nHostBasePath, endpointId, "aas");
		AASDescriptor desc = new AASDescriptor(bundle.getAAS(), aasBase);
		bundle.getSubmodels().stream().forEach(s -> {
			SubmodelDescriptor smDesc = new SubmodelDescriptor(s, VABPathTools.concatenatePaths(aasBase, "submodels", s.getIdShort(), "submodel"));
			desc.addSubmodelDescriptor(smDesc);
		});
		return desc;
	}
}
