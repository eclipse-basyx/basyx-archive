package org.eclipse.basyx.aas.backend.connected.facades;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IIdentifiable;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.aas.identifier.ConnectedAdministrativeInformation;
import org.eclipse.basyx.aas.backend.connected.aas.identifier.ConnectedIdentifier;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * Facade providing access to a map containing the ConnectedIdentifiableFacade structure
 * @author rajashek
 *
 */
public class ConnectedIdentifiableFacade extends ConnectedElement implements IIdentifiable {

	public ConnectedIdentifiableFacade(String path, VABElementProxy proxy) {
		super(path, proxy);
		
	}

	@Override
	public IAdministrativeInformation getAdministration() {
		//return (IAdministrativeInformation)getProxy().readElementValue(constructPath(Identifiable.ADMINISTRATION));
		return new ConnectedAdministrativeInformation(constructPath(Identifiable.ADMINISTRATION),getProxy());
	}

	@Override
	public IIdentifier getIdentification() {
		return new ConnectedIdentifier(constructPath(Identifiable.IDENTIFICATION), getProxy());
	}

	@Override
	public void setAdministration(String version, String revision) {
		 ConnectedAdministrativeInformation connectedAdministrativeInformation = new ConnectedAdministrativeInformation(constructPath(Identifiable.ADMINISTRATION),getProxy());
		 connectedAdministrativeInformation.setRevision(revision);
		 connectedAdministrativeInformation.setVersion(version);;
		 
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		ConnectedIdentifier connectedIdentifier = new ConnectedIdentifier(constructPath(Identifiable.IDENTIFICATION), getProxy());
		connectedIdentifier.setId(id);
		connectedIdentifier.setIdType(idType);
		
		
	}

}
