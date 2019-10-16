package org.eclipse.basyx.submodel.metamodel.api.submodelelement.property;

import java.util.Collection;

import org.eclipse.basyx.vab.exception.ServerException;
import org.eclipse.basyx.vab.exception.TypeMismatchException;

/**
 * Interface for AAS properties that carry a collection
 * 
 * @author kuhn, schnicke
 *
 */
public interface ICollectionProperty extends IProperty {
	/**
	 * Set or override collection
	 * 
	 * @param new
	 *            Collection to be set
	 */
	public void set(Collection<Object> collection) throws ServerException;

	/**
	 * Add value to collection
	 * 
	 * @param newValue
	 *            to be added
	 * @throws TypeMismatchException
	 * @throws Exception
	 */
	void add(Object newValue) throws ServerException, TypeMismatchException;

	/**
	 * Remove property from collection
	 * 
	 * @param objectRef
	 *            Property reference to be removed
	 * @throws ServerException
	 */
	public abstract void remove(Object objectRef) throws ServerException;

	/**
	 * Get property elements from collection
	 * 
	 * @return Collection with values
	 */
	public abstract Collection<Object> getElements() throws ServerException;

	/**
	 * Get element count
	 * 
	 * @return Element count
	 */
	public int getElementCount() throws ServerException;

}
