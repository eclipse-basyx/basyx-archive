package org.eclipse.basyx.vab.modelprovider.consistency;

import org.eclipse.basyx.vab.exception.ReadOnlyException;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

public class ConsistencyProvider<T extends IModelProvider> implements IModelProvider {

	/**
	 * Reference to IModelProvider backend
	 */
	protected T providerBackend = null;

	/**
	 * Constructor
	 */
	public ConsistencyProvider(T modelProviderBackend) {
		// Store reference to backend
		providerBackend = modelProviderBackend;
	}

	/**
	 * Get backend reference
	 */
	public T getBackendReference() {
		return providerBackend;
	}

	/**
	 * Server Clock that gets incremented when a property of this submodel is
	 * changed
	 */
	protected Integer clock = 0;

	/**
	 * Makes this provider block any write requests
	 */
	private boolean frozen = false;

	/**
	 * Increments the clock property for the given submodel
	 * 
	 * @param submodelPath
	 */
	private void incrementClock() {
		this.clock += 1;
	}

	private boolean isFrozen() {
		return this.frozen;
	}

	@Override
	public Object getModelPropertyValue(String path) throws Exception {

		if (path.endsWith("/frozen")) {
			return this.frozen;
		} else if (path.endsWith("/clock")) {
			return this.clock;
		} else {
			return providerBackend.getModelPropertyValue(path);
		}
	}

	/**
	 * Validate frozen property to make sure the submodel is not read-only, increase
	 * clock
	 * 
	 * @param path
	 * @param newValue
	 * @throws Exception
	 */
	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {

		if (path.endsWith("/frozen")) {

			// Set frozen property
			this.frozen = (boolean) newValue;

		} else if (this.frozen == false) {

			// Increment Clock
			incrementClock();

			// Set the value of the element
			providerBackend.setModelPropertyValue(path, newValue);

		} else {
			throw new ReadOnlyException(path);
		}

	}

	/**
	 * Create new Entity, check if submodel is frozen
	 * 
	 * @param path
	 * @param newEntity
	 * @throws Exception
	 */
	@Override
	public void createValue(String path, Object newEntity) throws Exception {

		if (!isFrozen() || path.endsWith("/frozen")) {
			providerBackend.createValue(path, newEntity);
		}
	}

	/**
	 * Delete entity, check if submodel is frozen
	 * 
	 * @param path
	 * @throws Exception
	 */
	@Override
	public void deleteValue(String path) throws Exception {

		if (!isFrozen() || path.endsWith("/frozen")) {
			providerBackend.deleteValue(path);
		}

	}

	/**
	 * Delete value from collection or map, check if submodel is frozen and increase
	 * clock
	 * 
	 * @param path
	 * @param obj
	 * @throws Exception
	 */
	@Override
	public void deleteValue(String path, Object obj) throws Exception {

		if (!isFrozen() || path.endsWith("/frozen")) {
			// Increment Clock
			incrementClock();

			// Set the value of the element
			providerBackend.deleteValue(path, obj);

		} else {
			throw new ReadOnlyException(path);
		}

	}

	/**
	 * Invoke and operation FIXME address security problems
	 * 
	 * @param path
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object invokeOperation(String path, Object... parameter) throws Exception {
		return providerBackend.invokeOperation(path, parameter);
	}

}
