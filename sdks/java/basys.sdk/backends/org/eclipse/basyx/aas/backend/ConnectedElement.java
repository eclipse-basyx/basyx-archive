package org.eclipse.basyx.aas.backend;

import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.backend.connector.IBasysConnector;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnector;
import org.eclipse.basyx.aas.backend.connector.opcua.OPCUAConnector;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.BaseElement;




/**
 * Base class for all HTTP connected elements  
 * 
 * @author kuhn
 *
 */
public class ConnectedElement implements IElement  {

	
	/**
	 * HTTP connector
	 */
	protected IBasysConnector basysConnector = null; 
	
	
	/**
	 * Store server URL of model provider
	 */
	protected String modelProviderURL = null;

	
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
	 * @param connector 
	 */
	public ConnectedElement(String url, IBasysConnector connector) {
		
		// Store parameter values
		modelProviderURL = url;
		
		// Create HTTP connector
		basysConnector = setConnector(connector);
		
		// Set caching information
		isValid_ 	 = false;
    	isCacheable_ = true; // % TODO Parameterize isCacheable
    	
    	
	}
	
	/**
	 * Initialize new Connector
	 * @param c
	 * @return
	 */
	private IBasysConnector setConnector(IBasysConnector c) {
		
		if (c instanceof OPCUAConnector) {
			return new OPCUAConnector();
		}
		
		else {
			return new HTTPConnector();
		}
		
	}

	/**
	 * TODO where should be decided if an element is cacheable?
	 * @return
	 */
	protected boolean isCacheable() {
		return isCacheable_;
	}
	
	/**
	 * Store element value
	 * @param value
	 */
	protected void setElement(Object value) {
		this.element_ = value;
		this.isValid_ = true;
	}
	
	/**
	 * Return stored element value
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
		return isValid_;
	}
	

	/**
	 * Return parent element
	 */
	//@Override
	public BaseElement getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Change parent element if possible
	 */
	//@Override
	public void setParent(BaseElement parent) {
		// TODO Auto-generated method stub	
	}


	/**
	 * Get element ID
	 */
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * Set element ID
	 */
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		
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
