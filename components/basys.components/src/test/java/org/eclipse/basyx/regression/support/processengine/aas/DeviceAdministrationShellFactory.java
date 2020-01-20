package org.eclipse.basyx.regression.support.processengine.aas;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.identifier.IdentifierType;

public class DeviceAdministrationShellFactory {

	public AssetAdministrationShell create(String aasid, String submodelid) {
		// create the aas, add submodel to aas using VABMultiSubmodelProvider
		IIdentifier id = new Identifier(IdentifierType.Custom, submodelid);
		AssetAdministrationShell aas = new AssetAdministrationShell();
		aas.addSubModel(new SubmodelDescriptor(submodelid, id, ""));
		aas.put("idshort", aasid);

		return aas;
	}
}
