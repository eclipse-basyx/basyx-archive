package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IIdentifiable;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.AdministrativeInformation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;

/**
 * Facade providing access to a map containing the Identifiable structure
 * @author rajashek
 *
 */

public class IdentifiableFacade implements IIdentifiable {

	private Map<String, Object> map;
	


	public IdentifiableFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public IAdministrativeInformation getAdministration() {
		return (IAdministrativeInformation)map.get(Identifiable.ADMINISTRATION);
	}

	@Override
	public IIdentifier getIdentification() {
		return (IIdentifier)map.get(Identifiable.IDENTIFICATION);
	}

	@Override
	public void setAdministration(String version, String revision) {
		map.put(Identifiable.ADMINISTRATION, new AdministrativeInformation(version, revision));
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		map.put(Identifiable.IDENTIFICATION, new Identifier(idType, id));
		
	}
	
	@Override
	public String getIdshort() {
		return (String) map.get(Referable.IDSHORT);
	}

	@Override
	public String getCategory() {
		return (String) map.get(Referable.CATEGORY);
	}

	@Override
	public String getDescription() {
		return (String) map.get(Referable.DESCRIPTION);
	}

	@Override
	public IReference  getParent() {
		return (IReference )map.get(Referable.PARENT);
	}

	@Override
	public void setIdshort(String idShort) {
		map.put(Referable.IDSHORT, idShort);
		
	}

	@Override
	public void setCategory(String category) {
		map.put(Referable.CATEGORY, category);
		
	}

	@Override
	public void setDescription(String description) {
		map.put(Referable.DESCRIPTION, description);
		
	}

	@Override
	public void setParent(IReference  obj) {
		map.put(Referable.PARENT, obj);
		
	}


}
