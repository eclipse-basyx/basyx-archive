package org.eclipse.basyx.submodel.metamodel.api.qualifier;


/**
 * Administrative metainformation for an element like version information.
 * 
 * @author rajashek, schnicke
 *
 */
public interface IAdministrativeInformation extends IHasDataSpecification {
	/**
	 * Gets the version of the element
	 * 
	 * @return
	 */
	public String getVersion();
	
	/**
	 * Gets the revision of the element
	 * 
	 * @return
	 */
	public String getRevision();
}
