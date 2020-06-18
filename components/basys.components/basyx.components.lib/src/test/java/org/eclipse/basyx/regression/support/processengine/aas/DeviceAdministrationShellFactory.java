package org.eclipse.basyx.regression.support.processengine.aas;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

public class DeviceAdministrationShellFactory {

	public AssetAdministrationShell create(String aasid, String submodelid) {
		// create the aas, add submodel to aas using VABMultiSubmodelProvider
		IIdentifier id = new Identifier(IdentifierType.CUSTOM, submodelid);
		SubModel sm = new SubModel();
		sm.setIdentification(id.getIdType(), id.getId());
		AssetAdministrationShell aas = new AssetAdministrationShell();
		aas.addSubModel(sm);
		aas.put("idshort", aasid);

		return aas;
	}
}
