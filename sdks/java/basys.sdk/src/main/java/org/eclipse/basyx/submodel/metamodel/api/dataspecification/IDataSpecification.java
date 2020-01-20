package org.eclipse.basyx.submodel.metamodel.api.dataspecification;

/**
 * DataSpecification containing a DataSpecificationContent
 * 
 * @author schnicke
 *
 */
public interface IDataSpecification {

	/**
	 * Returns the embedded content of the DataSpecification
	 * 
	 * @return
	 */
	IDataSpecificationContent getContent();
}
