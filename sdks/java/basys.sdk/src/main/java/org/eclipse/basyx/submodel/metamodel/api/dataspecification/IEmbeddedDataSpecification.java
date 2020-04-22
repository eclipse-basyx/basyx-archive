package org.eclipse.basyx.submodel.metamodel.api.dataspecification;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * DataSpecification containing a DataSpecificationContent
 * Difference to the IDataSpecification:
 * It is not identifiable, but has a reference to an identifiable DataSpecification template
 * 
 * @author espen
 *
 */
public interface IEmbeddedDataSpecification {
	/**
	 * Returns the reference to the identifiable data specification template
	 */
	public IReference getDataSpecificationTemplate();

	/**
	 * Returns the embedded content of the DataSpecification
	 */
	public IDataSpecificationContent getContent();
}
