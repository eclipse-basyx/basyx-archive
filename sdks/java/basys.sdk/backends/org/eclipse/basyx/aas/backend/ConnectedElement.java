package org.eclipse.basyx.aas.backend;

import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.BaseElement;

/**
 * Base class for all HTTP connected elements
 * 
 * @author kuhn
 *
 */
public class ConnectedElement implements IElement {

	protected IModelProvider provider = null;

	/**
	 * Caching information
	 */
	private boolean isValid_;
	private boolean isCacheable_;
	private String id;

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

	/**
	 * Return parent element
	 */
	// @Override
	@Override
	public BaseElement getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Change parent element if possible
	 */
	// @Override
	@Override
	public void setParent(BaseElement parent) {
		// TODO Auto-generated method stub
	}

	/**
	 * Get element ID
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * Set element ID
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Get asset kind
	 */
	@Override
	public AssetKind getAssetKind() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Set asset kind
	 */
	@Override
	public void setAssetKind(AssetKind kind) {
		// TODO Auto-generated method stub

	}

	/**
	 * Get element name
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Set element name
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
	}

}
