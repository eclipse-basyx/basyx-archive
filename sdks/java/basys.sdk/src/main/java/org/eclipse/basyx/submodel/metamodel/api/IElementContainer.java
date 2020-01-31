package org.eclipse.basyx.submodel.metamodel.api;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;

/**
 * Base interface for elements containing submodel elements
 * 
 * @author schnicke
 *
 */
public interface IElementContainer {
	/**
	 * Adds a submodel element
	 * 
	 * @param element
	 */
	public void addSubModelElement(ISubmodelElement element);

	/**
	 * Gets all contained submodel elements
	 * 
	 * @return
	 */
	public Map<String, ISubmodelElement> getSubmodelElements();

	/**
	 * Gets only submodel elements that are data elements
	 * 
	 * @return
	 */
	public Map<String, IDataElement> getDataElements();

	/**
	 * Gets only submodel elements that are operations
	 * 
	 * @return
	 */
	public Map<String, IOperation> getOperations();
	
}
