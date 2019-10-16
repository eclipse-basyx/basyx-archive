package org.eclipse.basyx.submodel.metamodel.api.qualifier;


/**
 * Interface for AdministrativeInformation
 * @author rajashek
 *
*/
public interface IAdministrativeInformation extends IHasDataSpecification {
	public String getVersion();
	
	public String getRevision();
}
