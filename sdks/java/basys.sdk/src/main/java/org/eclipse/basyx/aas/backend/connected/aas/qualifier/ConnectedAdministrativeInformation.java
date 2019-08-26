package org.eclipse.basyx.aas.backend.connected.aas.qualifier;

import java.util.HashSet;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasDataSpecificationFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.AdministrativeInformation;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IAdministrativeInformation
 * @author rajashek
 *
 */
public class ConnectedAdministrativeInformation extends ConnectedElement implements IAdministrativeInformation {
	public ConnectedAdministrativeInformation(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	
	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new ConnectedHasDataSpecificationFacade(getPath(),getProxy()).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new ConnectedHasDataSpecificationFacade(getPath(),getProxy()).setDataSpecificationReferences(ref);
		
	}

	@Override
	public void setVersion(String version) {
		getProxy().setModelPropertyValue(constructPath(AdministrativeInformation.VERSION), version);
		
	}

	@Override
	public String getVersion() {
		return (String)getProxy().getModelPropertyValue(constructPath(AdministrativeInformation.VERSION));
	}

	@Override
	public void setRevision(String revision) {
		getProxy().setModelPropertyValue(constructPath(AdministrativeInformation.REVISION), revision);
		
	}

	@Override
	public String getRevision() {
		return (String)getProxy().getModelPropertyValue(constructPath(AdministrativeInformation.REVISION));
	}
}
