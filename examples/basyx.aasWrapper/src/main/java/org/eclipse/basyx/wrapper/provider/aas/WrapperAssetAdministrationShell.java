package org.eclipse.basyx.wrapper.provider.aas;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;

/**
 * Dummy AAS for the wrapper AAS interface
 * 
 * @author espen
 *
 */
public class WrapperAssetAdministrationShell extends AssetAdministrationShell {
	public WrapperAssetAdministrationShell() {
		setIdShort("WrapperAAS");
		setIdentification(IdentifierType.CUSTOM, "WrapperAAS");
	}
}
