package org.eclipse.basyx.aas.metamodel.api.parts.asset;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IIdentifiable;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * An Asset describes meta data of an asset that is represented by an AAS. The
 * asset may either represent an asset type or an asset instance. The asset has
 * a globally unique identifier plus – if needed – additional domain specific
 * (proprietary) identifiers.
 * 
 * @author rajashek, schnicke
 *
 */

public interface IAsset extends IHasDataSpecification, IIdentifiable {
	/**
	 * Gets the asset kind
	 * 
	 * @return
	 */
	AssetKind getAssetKind();

	/**
	 * Gets the reference to a Submodel that defines the handling of additional
	 * domain specific (proprietary) Identifiers for the asset like e.g. serial
	 * number etc.
	 * 
	 * @return
	 */
	IReference getAssetIdentificationModel();

	/**
	 * Gets bill of material of the asset represented by a submodel of the same AAS.
	 * This submodel contains a set of entities describing the material used to
	 * compose the composite I4.0 Component.
	 * 
	 * @return
	 */
	IReference getBillOfMaterial();
}
