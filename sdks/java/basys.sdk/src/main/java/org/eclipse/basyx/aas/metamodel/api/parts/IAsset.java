package org.eclipse.basyx.aas.metamodel.api.parts;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IIdentifiable;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.IHasKind;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * Interface for class Identifier
 * 
 * @author rajashek
 *
 */

public interface IAsset extends IHasDataSpecification,IHasKind,IIdentifiable {
	public IReference getAssetIdentificationModel();
}
