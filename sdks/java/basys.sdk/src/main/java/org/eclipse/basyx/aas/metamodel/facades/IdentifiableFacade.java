package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IIdentifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.AdministrativeInformation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;

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


}
