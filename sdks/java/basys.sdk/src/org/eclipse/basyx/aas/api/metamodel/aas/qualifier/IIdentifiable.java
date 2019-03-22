package org.eclipse.basyx.aas.api.metamodel.aas.qualifier;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.AdministrativeInformation;

/**
 * Interface for Identifiable
 * 
 * @author rajashek
 *
*/

public interface IIdentifiable {
	
	public AdministrativeInformation getAdministration();
	
	public Identifier getIdentification();
	
	public void setAdministration(String version, String revision);
	
	public void setIdentification(String idType, String id);
}
