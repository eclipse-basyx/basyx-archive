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
		return (IAdministrativeInformation)getProxy().readElementValue(constructPath(Identifiable.ADMINISTRATION));
	}

	@Override
	public IIdentifier getIdentification() {
		return (IIdentifier)getProxy().readElementValue(constructPath(Identifiable.IDENTIFICATION));
	}

	@Override
	public void setAdministration(String version, String revision) {
		getProxy().updateElementValue(constructPath(Identifiable.ADMINISTRATION), new AdministrativeInformation(version, revision));
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		getProxy().updateElementValue(constructPath(Identifiable.IDENTIFICATION), new Identifier(idType, id));
		
	}
	@Override
	public String getIdshort() {
		return (String) getProxy().readElementValue(constructPath(Referable.IDSHORT));
	}

	@Override
	public String getCategory() {
		return (String) getProxy().readElementValue(constructPath(Referable.CATEGORY));
	}

	@Override
	public String getDescription() {
		return (String) getProxy().readElementValue(constructPath(Referable.DESCRIPTION));
	}

	@Override
	public IReference  getParent() {
		return (IReference)getProxy().readElementValue(constructPath(Referable.PARENT));
	}

	@Override
	public void setIdshort(String idShort) {
		getProxy().updateElementValue(constructPath(Referable.IDSHORT), idShort);
		
	}

	@Override
	public void setCategory(String category) {
		getProxy().updateElementValue(constructPath(Referable.CATEGORY), category);
		
	}

	@Override
	public void setDescription(String description) {
		getProxy().updateElementValue(constructPath(Referable.DESCRIPTION), description);
		
	}

	@Override
	public void setParent(IReference  obj) {
		getProxy().updateElementValue(constructPath(Referable.PARENT), obj);
		
	}

}


