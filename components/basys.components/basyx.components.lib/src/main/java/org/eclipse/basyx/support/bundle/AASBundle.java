package org.eclipse.basyx.support.bundle;

import java.util.Set;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;

/**
 * Helper class to bundle an AAS with its corresponding submodels, e.g. for
 * passing them to a server environment
 * 
 * @author schnicke
 *
 */
public class AASBundle {
	private IAssetAdministrationShell aas;
	private Set<ISubModel> submodels;

	public AASBundle(IAssetAdministrationShell aas, Set<ISubModel> submodels) {
		super();
		this.aas = aas;
		this.submodels = submodels;
	}

	public IAssetAdministrationShell getAAS() {
		return aas;
	}

	public Set<ISubModel> getSubmodels() {
		return submodels;
	}

}
