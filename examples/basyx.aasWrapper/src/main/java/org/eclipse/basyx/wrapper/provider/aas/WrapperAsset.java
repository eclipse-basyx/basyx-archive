package org.eclipse.basyx.wrapper.provider.aas;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

/**
 * Dummy asset for the wrapper AAS interface
 * 
 * @author espen
 *
 */
public class WrapperAsset extends Asset {
	public WrapperAsset() {
		setIdShort("WrapperAsset");
		setIdentification(IdentifierType.CUSTOM, "WrapperAsset");
		setAssetKind(AssetKind.INSTANCE);
	}
}
