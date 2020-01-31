package org.eclipse.basyx.submodel.metamodel.api.qualifier;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * An element that has a globally unique identifier.
 * 
 * @author rajashek, schnicke
 *
 */

public interface IIdentifiable  extends IReferable {
	/**
	 * Gets the administrative information of an identifiable element.
	 * 
	 * @return
	 */
	public IAdministrativeInformation getAdministration();
	
	/**
	 * Gets theglobally unique identification of the element.
	 * 
	 * @return
	 */
	public IIdentifier getIdentification();
}
