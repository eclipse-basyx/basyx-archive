package org.eclipse.basyx.aas.backend;

import java.util.Map;

import org.eclipse.basyx.aas.api.exception.AtomicTransactionFailedException;
import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.api.resources.basic.IOperation;
import org.eclipse.basyx.aas.api.resources.basic.IProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.backend.connector.IBasysConnector;
import org.eclipse.basyx.aas.impl.reference.ElementRef;
import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.SubModel;




/**
 * Implement a AAS sub model that communicates via HTTP/REST
 * 
 * @author kuhn
 *
 */
public class ConnectedSubmodel extends ConnectedElement implements ISubModel {

	
	/**
	 * Store AAS manager
	 */
	protected ConnectedAssetAdministrationShellManager aasManager = null;

	
	/**
	 * Store AAS ID
	 */
	protected String aasID = null;
	
	
	/**
	 * Store sub model ID of this property
	 */
	protected String aasSubmodelID = null;

	
	/**
	 * Cache for registered elements
	 */
	private BaSysCache<ConnectedProperty>  propertyCache = null;
	private BaSysCache<ConnectedOperation> operationCache = null;
	
	private static String PROPERTIES = "properties";
	private static String OPERATIONS = "operations";
	
	/**
	 * submodel clock information
	 */
	private Integer localClock;
	
	/**
	 * Indicates that this submodel and all its children are read only / frozen
	 */
	protected Boolean frozen;
	
	/**
	 * Constructor - expect the URL to the sub model
	 * @param connector 
	 */
	public ConnectedSubmodel(ConnectedAssetAdministrationShellManager aasMngr, String id, String submodelId, String url, IBasysConnector connector) {
		// Invoke base constructor
		super(url, connector);
		
		// Store parameter values
		aasID            = id;
		aasSubmodelID    = submodelId;
		modelProviderURL = url;
		aasManager       = aasMngr;
		
		this.propertyCache  = new BaSysCache<ConnectedProperty>(this);
		this.operationCache = new BaSysCache<ConnectedOperation>(this);
		
		this.initCache(PROPERTIES);
		this.initCache(OPERATIONS);
		
		this.localClock = 0;
		}
	
	
	/**
	 * @param path "properties" or "operations"
	 * Retrieve all registered elements and store in cache
	 */
	protected void initCache(String path) {
		// Get sub model properties
		Map<String, ElementRef> elements = (Map<String, ElementRef>) basysConnector.basysGet(modelProviderURL, aasSubmodelID+"."+aasID+"/"+path);
		
		System.out.println(">> [initCache] FINISH BASYS GET");
		
		// Add properties to cache 
		for (String elementId : elements.keySet()) {
			
			IElement proxy = null;
			
			if (path.equals(PROPERTIES)) { 
				proxy = aasManager.retrievePropertyProxy(elements.get(elementId));  
				this.propertyCache.put(elementId, (ConnectedProperty) proxy);
			}
			
			if (path.equals(OPERATIONS)) { 
				proxy = aasManager.retrieveOperationProxy(elements.get(elementId)); 
				this.operationCache.put(elementId, (ConnectedOperation) proxy);
			}

			System.out.println("Added "+ path + "/"+elementId + " to cache");

		}
	
	}
	
	/**
	 * retrieve submodel clock from server
	 */
	public Integer getServerClock() {
		
		String servicePath = aasSubmodelID + "." + aasID + "/clock";
		Integer serverClock = (Integer) basysConnector.basysGet(modelProviderURL, servicePath);
		
		return serverClock;

	}
	
	/**
	 * Start transaction. Get current server clock for this submodel
	 */
	public void startTransaction() {
		
		this.localClock = getServerClock();
	}
	
	/**
	 * End transaction. Check if Submodel clock has the same clock as the server
	 * @throws AtomicTransactionFailedException 
	 */
	public void endTransaction() throws AtomicTransactionFailedException {
		
		Integer serverClock = this.getServerClock();
		if (this.localClock != serverClock) {
			throw new AtomicTransactionFailedException(this.aasSubmodelID);
		}
	
	}
	
	/**
	 * Make IBasysConnector accessible for cache
	 * @return
	 */
	protected IBasysConnector getConnector() {
		return this.basysConnector;
	}
	

	/**
	 * Make modelproviderUrl accessible for cache
	 * @return
	 */
	public String getModelProviderUrl() {
		return this.modelProviderURL;
	}


	/**
	 * Retrieve and return all registered properties
	 */
	@Override
	public Map<String, IProperty> getProperties() {
		return this.propertyCache.getProperties();
	}
	
	
	
	/**
	 * Retrieve and return all registered properties
	 */
	@Override
	public Map<String, IOperation> getOperations() {
		return this.operationCache.getOperations();
	}



	@Override
	public IElement getElement(String name) {
		// TODO Auto-generated method stub
		throw new FeatureNotImplementedException();
	}



	@Override
	public Map<String, IElement> getElements() {
		// TODO Auto-generated method stub
		throw new FeatureNotImplementedException();
	}
	
	/**
	 * Returns whether this submodel is frozen
	 */
	@Override
	public boolean isFrozen() {
		String servicePath = basysConnector.buildPath(aasID, aasSubmodelID, "frozen"); 
		
		// Retrieve "frozen" variable
		boolean frozen = (boolean) basysConnector.basysGet(modelProviderURL, servicePath);
		
		System.out.println("frozen="+frozen);
		return frozen;
	}


	/** 
	 * Freezes this submodel. Sets frozen property true
	 */
	@Override
	public void freeze() {
		String servicePath = basysConnector.buildPath(aasID, aasSubmodelID, "frozen"); 
		
		// Set "frozen" variable to true
		System.out.println("Freezing submodel "+ this.aasSubmodelID);
		basysConnector.basysSet(this.modelProviderURL, servicePath, true);
	}
	
	/** 
	 * Unfreezes this submodel. Sets frozen property false
	 */
	@Override
	public void unfreeze() {
		String servicePath = basysConnector.buildPath(aasID, aasSubmodelID, "frozen"); 
		
		// Set "frozen" variable to false
		System.out.println("Unfreezing submodel "+ this.aasSubmodelID);
		basysConnector.basysSet(this.modelProviderURL, servicePath, false);
	}
	
	/**
	 * Registers the given property on the server
	 * @param property
	 */
	public void createProperty(IProperty property) {
		String servicePath = basysConnector.buildPath(aasID, aasSubmodelID, property.getId()); 
		
		// Set parent references for this property to allow serialization with JSONTools
		// - Create AAS stub
		AssetAdministrationShell tmpAAS = new AssetAdministrationShell();
		tmpAAS.setId(this.aasID);
		
		// - Create SubModel stub
		SubModel tmpSubModel = new SubModel();
		tmpSubModel.setParent(tmpAAS);
		tmpSubModel.setId(this.aasSubmodelID);
		
		// - Add parent references
		tmpSubModel.setParent(tmpAAS);
		property.setParent(tmpSubModel);
		
		// Property attributes
		String name = property.getName();
		String type = property.getDataType().toString();
		boolean isCollection = property.isCollection();
		boolean isMap = property.isMap();
		
		// Send new property to server
		basysConnector.basysPost(this.modelProviderURL, servicePath, "createProperty", property, name, type, isCollection, isMap);
		
		// Add property to cache
		// - Create ConnectedProperty
		ElementRef element = (ElementRef) basysConnector.basysGet(this.modelProviderURL, servicePath);
		IProperty proxy = aasManager.retrievePropertyProxy(element); 
		
		// - Add to Cache
		this.propertyCache.put(property.getId(), (ConnectedProperty) proxy);
	}
	
	/**
	 * Registers the given operation on the server. Function code could be sent as bytecode.
	 * @param property
	 */
	public void createOperation(IOperation operation) {
		// TODO 
		throw new FeatureNotImplementedException();
	}


	public String getAASID() {
		return this.aasID;
	}


	public String getAASSubmodelID() {
		return this.aasSubmodelID;
	}


}

