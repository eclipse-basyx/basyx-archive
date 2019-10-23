package org.eclipse.basyx.regression.support.processengine.aas;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.factory.java.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.submodel.metamodel.map.identifier.IdentifierType;

public class DeviceAdministrationShellFactory {
	
	public AssetAdministrationShell create(String aasid, String submodelid){
		MetaModelElementFactory factory = new MetaModelElementFactory();
		
		//create the aas, add submodel to aas using VABMultiSubmodelProvider 
		Set<SubmodelDescriptor> refs = new HashSet<>();
		refs.add(new SubmodelDescriptor(submodelid, IdentifierType.Custom, ""));
		AssetAdministrationShell aas = factory.create(new AssetAdministrationShell(), refs);
		aas.put("idshort", aasid);

		return aas;
	}
}
