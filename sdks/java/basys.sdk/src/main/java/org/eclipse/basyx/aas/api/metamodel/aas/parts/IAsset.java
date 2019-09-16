package org.eclipse.basyx.aas.api.metamodel.aas.parts;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IIdentifiable;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.haskind.IHasKind;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;

/**
 * Interface for class Identifier
 * 
 * @author rajashek
 *
 */

public interface IAsset extends IHasDataSpecification,IHasKind,IIdentifiable {
	public IReference getAssetIdentificationModel();
}
