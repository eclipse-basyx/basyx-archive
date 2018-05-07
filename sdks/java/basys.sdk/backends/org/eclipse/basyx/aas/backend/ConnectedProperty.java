package org.eclipse.basyx.aas.backend;

import java.util.Observable;
import org.eclipse.basyx.aas.api.resources.basic.IProperty;
import org.eclipse.basyx.aas.backend.connector.IBasysConnector;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.json.JSONObject;



/**
 * Implement a connected AAS property that communicates via HTTP/REST
 * 
 * @author kuhn
 *
 */
public class ConnectedProperty extends ConnectedElement implements IProperty {

	
	
	/**
	 * Store AAS ID
	 */
	protected String aasID = null;
	
	
	/**
	 * Store sub model ID of this property
	 */
	protected String aasSubmodelID = null;

	
	/**
	 * Store path to this property
	 */
	protected String propertyPath = null;
	
	/**
	 * Store collection info
	 */
	protected boolean isCollection = false;
	
	/**
	 * Store map info
	 */
	protected boolean isMap = false;

	
	/**
	 * Constructor - expect the URL to the sub model
	 * @param connector 
	 */
	public ConnectedProperty(String id, String submodelId, String path, String url, IBasysConnector connector)  {
		// Invoke base constructor
		super(url, connector);

		// Store parameter values
		aasID            = id;
		aasSubmodelID    = submodelId;
		propertyPath     = submodelId+"."+aasID+"/"+path;
		modelProviderURL = url;
		
		System.out.println("PU:"+propertyPath);
	}
	
	
	/**
	 * Returns property value
	 */
	public Object getElement() {
		if (this.isValid()) {
			
			// Get element from cache
			return this.getCachedElement();
		}
		else {
			
			// Get element from server
			return basysConnector.basysGet(this.modelProviderURL, propertyPath);
		}
	}
	
	
	/**
	 * Get property data type
	 * 
	 * @return Property data type
	 */
	@Override
	public DataType getDataType() {
		// Get property value
		JSONObject property = basysConnector.basysGetRaw(this.modelProviderURL, propertyPath);

		// Unknown type
		return JSONTools.Instance.decodeDataType(property);
	}


	/**
	 * Indicate if this property is a collection type
	 * 
	 * @return Collection indicator
	 */
	@Override
	public boolean isCollection() {
		
		//return this.isCollection;
		
		// Get property value
		JSONObject property = basysConnector.basysGetRaw(this.modelProviderURL, propertyPath);

		// Get type
		DataType type = JSONTools.Instance.decodeDataType(property);
		
		// Check data type
		return (type == DataType.COLLECTION);
		
	}



	/**
	 * Indicate if this property is a map type
	 * 
	 * @return Map indicator
	 */
	@Override
	public boolean isMap() {
		
		//return this.isMap;
		
		// Get property value
		JSONObject property = basysConnector.basysGetRaw(this.modelProviderURL, propertyPath);

		// Get type
		DataType type = JSONTools.Instance.decodeDataType(property);
		
		// Check data type
		return (type == DataType.MAP);
		
	}


	
}
