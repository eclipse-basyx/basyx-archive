package org.eclipse.basyx.submodel.restapi.api;

import java.util.Collection;
import java.util.List;

import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
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
	 * Returns all submodelElements contained by the submodel
	 * 
	 * @return the submodelElements
	 */
	public Collection<ISubmodelElement> getElements();

	/**
	 * Adds a submodelElement to the submodel
	 * 
	 * @param elem
	 *            the submodelElement to be added
	 */
	public void addSubmodelElement(ISubmodelElement elem);

	/**
	 * Adds a submodelElement to the submodel
	 * 
	 * @param idShorts
	 *            the idShort path to the submodelElement
	 * @param  elem
	 *            the submodelElement to be added
	 */
	public void addSubmodelElement(List<String> idShorts, ISubmodelElement elem);

	/**
	 * Retrieves a submodelElement
	 * 
	 * @param idShort
	 *            of the submodelElement
	 * @return the submodelElement
	 */
	public ISubmodelElement getSubmodelElement(String idShort);

	/**
	 * Removes a submodelElement from the submodel
	 * 
	 * @param idShort
	 *            of the submodelElement to be removed
	 */
	public void deleteSubmodelElement(String idShort);

	/**
	 * Removes a submodelElement from a SubmodelElementCollection
	 * 
	 * @param idShort
	 *            of the submodelElement to be removed
	 */
	public void deleteNestedSubmodelElement(List<String> idShorts);

	/**
	 * Helper function for quick access of operations
	 * 
	 * @return all operations contained by the submodel
	 */
	public Collection<IOperation> getOperations();

	/**
	 * Helper function for quick access of submodelElements
	 * 
	 * @return all submodelElements contained by the submodel
	 */
	public Collection<ISubmodelElement> getSubmodelElements();

	/**
	 * Updates the value of a submodelElement
	 * 
	 * @param idShort
	 *            of the submodelElement
	 * @param newValue
	 *            new value of the submodelElement
	 */
	public void updateSubmodelElement(String idShort, Object newValue);

	/**
	 * Updates the value of a submodelElement nested inside a SubmodelElementCollection.
	 * 
	 * @param idShorts
	 *            the idShort path to the submodelElement
	 * @param newValue
	 *            new value of the submodelElement
	 */
	public void updateNestedSubmodelElement(List<String> idShorts, Object newValue);

	/**
	 * Retrieves the value of a submodelElement
	 * 
	 * @param idShort
	 *            of the submodelElement
	 * @return submodelElement value
	 */
	public Object getSubmodelElementValue(String idShort);

	/**
	 * Retrieves the value of a submodelElement nested inside a SubmodelElementCollection.
	 * 
	 * @param idShorts
	 *            the idShort path to the submodelElement
	 */
	public Object getNestedSubmodelElementValue(List<String> idShorts);

	/**
	 * Retrieves a submodel element nested inside a SubmodelElementCollection
	 * 
	 * @param idShorts
	 *            the idShort path to the submodelElement
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

	/**
	 * Invokes an operation
	 * 
	 * @param idShorts
	 *            the idShort path to the operation
	 * @param params
	 *            to be passed to the operation
	 * @return the result of the operation
	 */
	public Object invokeNestedOperation(List<String> idShorts, Object... params);

	/**
	 * Invokes an operation asynchronously
	 * 
	 * @param idShorts
	 *            the idShort path to the operation
	 * @param params
	 *            to be passed to the operation
	 * @return the requestId of the invocation
	 */
	public Object invokeNestedOperationAsync(List<String> idShorts, Object... params);
	
	/**
	 * Gets the result of an asynchronously invoked operation
	 * 
	 * @param idShorts 
	 *            the idShort path to the operation
	 * @param requestId
	 *            the requestId of the invocation
	 * @return the result of the Operation or a Message that it is not finished yet
	 */
	public Object getOperationResult(List<String> idShorts, String requestId);

}
