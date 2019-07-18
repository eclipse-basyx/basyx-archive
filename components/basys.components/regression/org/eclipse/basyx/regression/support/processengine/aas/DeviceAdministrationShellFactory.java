package org.eclipse.basyx.regression.support.processengine.aas;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;

public class DeviceAdministrationShellFactory {
	
	public AssetAdministrationShell create(String aasid, String submodelid){
		MetaModelElementFactory factory = new MetaModelElementFactory();
		
		//create the aas, add submodel to aas using VABMultiSubmodelProvider 
		Set<String> refs = new HashSet<>();
		refs.add(submodelid);
		AssetAdministrationShell aas = factory.create(new AssetAdministrationShell(), refs);
		aas.put("idshort", aasid);

		return aas;
	}
}
