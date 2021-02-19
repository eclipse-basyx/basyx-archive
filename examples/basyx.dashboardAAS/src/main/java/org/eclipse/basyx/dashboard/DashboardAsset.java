package org.eclipse.basyx.dashboard;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

/**
 * A dummy Asset for the dashboar AAS
 * 
 * @author espen
 *
 */
public class DashboardAsset extends Asset {
	public DashboardAsset() {
		setIdShort("DashboardAsset");
		setIdentification(IdentifierType.CUSTOM, "DashboardAsset");
		setAssetKind(AssetKind.INSTANCE);
	}
}
