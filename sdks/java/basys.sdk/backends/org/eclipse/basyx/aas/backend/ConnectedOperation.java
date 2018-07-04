package org.eclipse.basyx.aas.backend;

import java.util.List;

import org.eclipse.basyx.aas.api.resources.basic.IOperation;
import org.eclipse.basyx.aas.backend.connector.IBasysConnector;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.ParameterType;



/**
 * Implement a connected AAS property that communicates via HTTP/REST
 * 
 * @author kuhn
 *
 */
public class ConnectedOperation extends ConnectedElement implements IOperation {

	
	
	/**
	 * Store AAS ID
	 */
	protected String aasID = null;
	
	
	/**
	 * Store sub model ID of this property
	 */
	protected String aasSubmodelID = null;

	
	/**
	 * Store path to this operation
	 */
	protected String operationPath = null;

	
	
	
	
	/**
	 * Constructor - expect the URL to the sub model
	 * @param connector 
	 */
	public ConnectedOperation(String aasId, String submodelId, String path, String url, IBasysConnector connector) {
		// Invoke base constructor
		super(url, connector);

		// Store parameter values
		aasID            = aasId;
		aasSubmodelID    = submodelId;
		operationPath    = connector.buildPath(aasId, submodelId, path, "operations");
		modelProviderURL = url;
	}
	
	
	@Override
	public List<ParameterType> getParameterTypes() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DataType getReturnDataType() {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * Invoke operation
	 */
	public Object invoke(Object... parameter) throws Exception {
		// Get property value
		Object returnValue = basysConnector.basysInvoke(this.modelProviderURL, operationPath, parameter);
		
		// Return property value
		return returnValue;
	}
}

