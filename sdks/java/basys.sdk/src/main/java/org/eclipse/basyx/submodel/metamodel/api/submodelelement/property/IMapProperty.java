package org.eclipse.basyx.submodel.metamodel.api.submodelelement.property;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.vab.exception.ServerException;
import org.eclipse.basyx.vab.exception.TypeMismatchException;

/**
 * Interface for AAS properties that carry a map
 * 
 * @author kuhn, pschorn
 *
 */
public interface IMapProperty extends IProperty {

	/**
	 * Get value for key
	 * 
	 * @throws TypeMismatchException
	 */
	public Object getValue(String key) throws TypeMismatchException, ServerException;

	/**
	 * put entry in map
	 * 
	 * @throws ServerException
	 */
	public void put(String key, Object value) throws ServerException;

	/**
	 * Set new map or override existing
	 * 
	 * @param map
	 * @throws ServerException
	 */
	void set(Map<String, Object> map) throws ServerException;

	/**
	 * Get map keys
	 * 
	 * @return Collection with keys
	 * @throws TypeMismatchException
	 */
	public Collection<String> getKeys() throws TypeMismatchException, ServerException;

	/**
	 * Get entry count
	 * 
	 * @throws TypeMismatchException
	 */
	public Integer getEntryCount() throws TypeMismatchException, ServerException;

	/**
	 * Remove entry
	 * 
	 * @throws ServerException
	 * @throws TypeMismatchException
	 */
	public void remove(String key) throws ServerException, TypeMismatchException;

}
