package org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.vab.exception.TypeMismatchException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;

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
	public Object getValue(String key) throws TypeMismatchException, ProviderException;

	/**
	 * put entry in map
	 * 
	 * @throws ProviderException
	 */
	public void put(String key, Object value) throws ProviderException;

	/**
	 * Set new map or override existing
	 * 
	 * @param map
	 * @throws ProviderException
	 */
	void set(Map<String, Object> map) throws ProviderException;

	/**
	 * Get map keys
	 * 
	 * @return Collection with keys
	 * @throws TypeMismatchException
	 */
	public Collection<String> getKeys() throws TypeMismatchException, ProviderException;

	/**
	 * Get entry count
	 * 
	 * @throws TypeMismatchException
	 */
	public Integer getEntryCount() throws TypeMismatchException, ProviderException;

	/**
	 * Remove entry
	 * 
	 * @throws ProviderException
	 * @throws TypeMismatchException
	 */
	public void remove(String key) throws ProviderException, TypeMismatchException;

}
