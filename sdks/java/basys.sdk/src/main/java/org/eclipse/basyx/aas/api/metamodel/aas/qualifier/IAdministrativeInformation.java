package org.eclipse.basyx.aas.api.metamodel.aas.qualifier;


/**
 * Interface for AdministrativeInformation
 * @author rajashek
 *
*/
public interface IAdministrativeInformation extends IHasDataSpecification {
	public String getVersion();
	
	public String getRevision();
}
