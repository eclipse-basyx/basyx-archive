package org.eclipse.basyx.submodel.restapi.api;

import java.util.Collection;
import java.util.List;

import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;

/**
 * Specifies overall Submodel API
 * 
 * @author schnicke
 *
 */
public interface ISubmodelAPI {

	/**
	 * Retrieves the underlying submodel
	 * 
	 * @return the submodel
	 */
	public ISubModel getSubmodel();

	/**
	 * Returns all submodel elements contained by the submodel
	 * 
	 * @return the submodel elements
	 */
	public Collection<ISubmodelElement> getElements();

	/**
	 * Adds a submodel element to the submodel
	 * 
	 * @param elem
	 *            the element to be added
	 */
	public void addSubmodelElement(ISubmodelElement elem);

	/**
	 * Retrieves a submodel element
	 * 
	 * @param idShort
	 *            of the submodel element
	 * @return the submodel element
	 */
	public ISubmodelElement getSubmodelElement(String idShort);

	/**
	 * Removes a submodel element from the submodel
	 * 
	 * @param idShort
	 *            of the element to be removed
	 */
	public void deleteSubmodelElement(String idShort);

	/**
	 * Helper function for quick access of operations
	 * 
	 * @return all operations contained by the submodel
	 */
	public Collection<IOperation> getOperations();

	/**
	 * Helper function for quick access of properties
	 * 
	 * @return all properties contained by the submodel
	 */
	public Collection<IProperty> getProperties();

	/**
	 * Updates the value of a property
	 * 
	 * @param idShort
	 *            of the property
	 * @param newValue
	 *            new value of the property
	 */
	public void updateProperty(String idShort, Object newValue);

	/**
	 * Retrieves the value of a property
	 * 
	 * @param idShort
	 *            of the property
	 * @return property value
	 */
	public Object getPropertyValue(String idShort);

	/**
	 * Retrieves the value of a property nested inside a SubmodelElementCollection.
	 * 
	 * @param idShorts
	 *            the idShort path to the property
	 */
	public Object getNestedPropertyValue(List<String> idShorts);

	/**
	 * Retrieves a submodel element nested inside a SubmodelElementCollection
	 * 
	 * @param idShorts
	 *            the idShort path to the property
	 * @return
	 */
	public ISubmodelElement getNestedSubmodelElement(List<String> idShorts);

	/**
	 * Invokes an operation
	 * 
	 * @param idShort
	 *            of the operation
	 * @param params
	 *            to be passed to the operation
	 * @return the result of the operation
	 */
	public Object invokeOperation(String idShort, Object... params);
}
