package org.eclipse.basyx.submodel.metamodel.api.qualifier;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * Interface for Identifiable
 * 
 * @author rajashek
 *
*/

public interface IIdentifiable  extends IReferable {
	public IAdministrativeInformation getAdministration();
	
	public IIdentifier getIdentification();
}
