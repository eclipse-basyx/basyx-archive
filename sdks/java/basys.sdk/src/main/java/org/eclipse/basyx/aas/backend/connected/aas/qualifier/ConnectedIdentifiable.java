package org.eclipse.basyx.aas.backend.connected.aas.qualifier;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IIdentifiable;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.AdministrativeInformation;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IIdentifiable
 * @author rajashek
 *
 */
public class ConnectedIdentifiable extends ConnectedElement implements IIdentifiable {

	public ConnectedIdentifiable(VABElementProxy proxy) {
		super(proxy);		
	}
	
	@Override
	public IAdministrativeInformation getAdministration() {
		return (IAdministrativeInformation)getProxy().getModelPropertyValue(Identifiable.ADMINISTRATION);
	}

	@Override
	public IIdentifier getIdentification() {
		return (IIdentifier)getProxy().getModelPropertyValue(Identifiable.IDENTIFICATION);
	}

	@Override
	public void setAdministration(String version, String revision) {
		getProxy().setModelPropertyValue(Identifiable.ADMINISTRATION, new AdministrativeInformation(version, revision));
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		getProxy().setModelPropertyValue(Identifiable.IDENTIFICATION, new Identifier(idType, id));
		
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
		return (IReference)getProxy().getModelPropertyValue(Referable.PARENT);
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


