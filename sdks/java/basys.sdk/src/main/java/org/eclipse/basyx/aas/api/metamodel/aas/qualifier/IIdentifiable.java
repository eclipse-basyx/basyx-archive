package org.eclipse.basyx.aas.api.metamodel.aas.qualifier;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;

/**
 * Interface for Identifiable
 * 
 * @author rajashek
 *
*/

public interface IIdentifiable {
	
	public IAdministrativeInformation getAdministration();
	
	public IIdentifier getIdentification();
	
	public void setAdministration(String version, String revision);
	
	public void setIdentification(String idType, String id);
}
