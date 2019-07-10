package org.eclipse.basyx.aas.api.metamodel.aas.qualifier;


/**
 * Interface for AdministrativeInformation
 * @author rajashek
 *
*/
public interface IAdministrativeInformation extends IHasDataSpecification {
	
	public void setVersion(String version);
	
	public String getVersion();
	
	public void setRevision(String revision);
	
	public String getRevision();
	

}
