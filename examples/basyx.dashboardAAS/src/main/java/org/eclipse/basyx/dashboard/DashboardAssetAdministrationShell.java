package org.eclipse.basyx.dashboard;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

/**
 * A minimal AAS header for the dashboard AAS
 * 
 * @author espen
 *
 */
public class DashboardAssetAdministrationShell extends AssetAdministrationShell {
	public DashboardAssetAdministrationShell() {
		setIdShort("DashboardAAS");
		setIdentification(IdentifierType.CUSTOM, "DashboardAAS");
	}
}
