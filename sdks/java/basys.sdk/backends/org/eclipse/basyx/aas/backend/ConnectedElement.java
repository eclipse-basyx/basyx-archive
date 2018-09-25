package org.eclipse.basyx.aas.backend;

import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.impl.resources.basic.BaseElement;

/**
 * Base class for all HTTP connected elements
 * 
 * @author kuhn
 *
 */
public class ConnectedElement extends BaseElement {

	protected IModelProvider provider = null;

	/**
	 * Caching information
	 */
	private boolean isValid_;
	private boolean isCacheable_;

	/**
	 * Cached property value
	 */
	private Object element_ = null;

	/**
	 * Constructor - expect the URL to the sub model
	 * 
	 * @param provider
	 */
	public ConnectedElement(IModelProvider provider) {
		// Set connector
		this.provider = provider;

		// Set caching information
		isValid_ = false;
		isCacheable_ = true; // % TODO Parameterize isCacheable
	}

	/**
	 * TODO where should be decided if an element is cacheable?
	 * 
	 * @return
	 */
	protected boolean isCacheable() {
		return isCacheable_;
	}

	/**
	 * Store element value
	 * 
	 * @param value
	 */
	protected void setElement(Object value) {
		this.element_ = value;
		this.isValid_ = true;
	}

	/**
	 * Return stored element value
	 * 
	 * @return
	 */
	protected Object getCachedElement() {
		return this.element_;
	}

	/**
	 * Inform cache that property shall be reloaded from server
	 */
	public void invalidate() {
		this.isValid_ = false;

	}

	/**
	 * Return true if property has a valid cached value
	 */
	public boolean isValid() {
		return false;
	}
}
