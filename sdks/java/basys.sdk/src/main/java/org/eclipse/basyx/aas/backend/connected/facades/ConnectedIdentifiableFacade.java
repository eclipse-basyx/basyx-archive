package org.eclipse.basyx.aas.backend.connected.facades;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IIdentifiable;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.aas.identifier.ConnectedAdministrativeInformation;
import org.eclipse.basyx.aas.backend.connected.aas.identifier.ConnectedIdentifier;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * Facade providing access to a map containing the ConnectedIdentifiableFacade structure
 * @author rajashek
 *
 */
public class ConnectedIdentifiableFacade extends ConnectedElement implements IIdentifiable {

	public ConnectedIdentifiableFacade(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public IAdministrativeInformation getAdministration() {
		return new ConnectedAdministrativeInformation(getProxy().getDeepProxy(Identifiable.ADMINISTRATION));
	}

	@Override
	public IIdentifier getIdentification() {
		return new ConnectedIdentifier(getProxy().getDeepProxy(Identifiable.IDENTIFICATION));
	}

	@Override
	public void setAdministration(String version, String revision) {
		ConnectedAdministrativeInformation connectedAdministrativeInformation = new ConnectedAdministrativeInformation(getProxy().getDeepProxy(Identifiable.ADMINISTRATION));
		 connectedAdministrativeInformation.setRevision(revision);
		 connectedAdministrativeInformation.setVersion(version);;
		 
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		ConnectedIdentifier connectedIdentifier = new ConnectedIdentifier(getProxy().getDeepProxy(Identifiable.IDENTIFICATION));
		connectedIdentifier.setId(id);
		connectedIdentifier.setIdType(idType);
		
		
	}
	
	@Override
	public String getIdshort() {
		return (String) getProxy().getModelPropertyValue(Referable.IDSHORT);
	}

	@Override
	public String getCategory() {
		return (String) getProxy().getModelPropertyValue(Referable.CATEGORY);
	}

	@Override
	public String getDescription() {
		return (String) getProxy().getModelPropertyValue(Referable.DESCRIPTION);
	}

	@Override
	public IReference  getParent() {
		return (IReference )getProxy().getModelPropertyValue(Referable.PARENT);
	}

	@Override
	public void setIdshort(String idShort) {
		getProxy().setModelPropertyValue(Referable.IDSHORT, idShort);
		
	}

	@Override
	public void setCategory(String category) {
		getProxy().setModelPropertyValue(Referable.CATEGORY, category);
		
	}

	@Override
	public void setDescription(String description) {
		getProxy().setModelPropertyValue(Referable.DESCRIPTION, description);
		
	}

	@Override
	public void setParent(IReference  obj) {
		getProxy().setModelPropertyValue(Referable.PARENT, obj);
		
	}


}
