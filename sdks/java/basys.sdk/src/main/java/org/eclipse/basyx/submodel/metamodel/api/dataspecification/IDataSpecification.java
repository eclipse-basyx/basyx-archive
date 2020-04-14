package org.eclipse.basyx.submodel.metamodel.api.dataspecification;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IIdentifiable;

/**
 * Template for an DataSpecification
 * 
 * 
 * @author schnicke, espen
 *
 */
public interface IDataSpecification extends IIdentifiable {

	/**
	 * Returns the embedded content of the DataSpecification
	 * 
	 * @return
	 */
	public IDataSpecificationContent getContent();
}
