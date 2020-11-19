package org.eclipse.basyx.aas.metamodel.api;

import java.util.Collection;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.IAsset;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.parts.IConceptDescription;

public interface IAasEnv {

	Collection<IAsset> getAssets();
	
	Collection<IAssetAdministrationShell> getAssetAdministrationShells();
	
	Collection<ISubModel> getSubmodels();
	
	Collection<IConceptDescription> getConceptDescriptions();
}
