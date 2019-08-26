package org.eclipse.basyx.aas.backend.connected.aas.qualifier;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IIdentifiable;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.AdministrativeInformation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IIdentifiable
 * @author rajashek
 *
 */
public class ConnectedIdentifiable extends ConnectedElement implements IIdentifiable {

	public ConnectedIdentifiable(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	
	@Override
	public IAdministrativeInformation getAdministration() {
		return (IAdministrativeInformation)getProxy().getModelPropertyValue(constructPath(Identifiable.ADMINISTRATION));
	}

	@Override
	public IIdentifier getIdentification() {
		return (IIdentifier)getProxy().getModelPropertyValue(constructPath(Identifiable.IDENTIFICATION));
	}

	@Override
	public void setAdministration(String version, String revision) {
		getProxy().setModelPropertyValue(constructPath(Identifiable.ADMINISTRATION), new AdministrativeInformation(version, revision));
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		getProxy().setModelPropertyValue(constructPath(Identifiable.IDENTIFICATION), new Identifier(idType, id));
		
	}
	@Override
	public String getIdshort() {
		return (String) getProxy().getModelPropertyValue(constructPath(Referable.IDSHORT));
	}

	@Override
	public String getCategory() {
		return (String) getProxy().getModelPropertyValue(constructPath(Referable.CATEGORY));
	}

	@Override
	public String getDescription() {
		return (String) getProxy().getModelPropertyValue(constructPath(Referable.DESCRIPTION));
	}

	@Override
	public IReference  getParent() {
		return (IReference)getProxy().getModelPropertyValue(constructPath(Referable.PARENT));
	}

	@Override
	public void setIdshort(String idShort) {
		getProxy().setModelPropertyValue(constructPath(Referable.IDSHORT), idShort);
		
	}

	@Override
	public void setCategory(String category) {
		getProxy().setModelPropertyValue(constructPath(Referable.CATEGORY), category);
		
	}

	@Override
	public void setDescription(String description) {
		getProxy().setModelPropertyValue(constructPath(Referable.DESCRIPTION), description);
		
	}

	@Override
	public void setParent(IReference  obj) {
		getProxy().setModelPropertyValue(constructPath(Referable.PARENT), obj);
		
	}

}


