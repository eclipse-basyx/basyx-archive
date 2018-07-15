package org.eclipse.basyx.aas.backend;

import java.util.Collection;

import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.exception.ResourceNotFoundException;
import org.eclipse.basyx.aas.api.manager.IAssetAdministrationShellManager;
import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.basic.IConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.IOperation;
import org.eclipse.basyx.aas.api.resources.basic.IProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.api.services.IDirectoryService;
import org.eclipse.basyx.aas.backend.connector.IBasysConnector;
import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.tools.BaSysID;



/**
 * Implement a AAS manager backend that communicates via HTTP/REST
 * 
 * ToDo:
 * - Access check (set)
 * - Frozen models
 * - Operations
 *   - create/delete
 * - Events
 * - HTTP server test suite that does not need this SDK
 * - Finish JavaDoc comments
 * - Dynamic deployment of AAS/Submodels
 * - Default deployment target for submodels
 * - Custom deployment target for submodels (technology independent)
 * 
 * @author kuhn
 *
 */
public class ConnectedAssetAdministrationShellManager implements IAssetAdministrationShellManager {

	
	/**
	 * Directory service reference
	 */
	protected IDirectoryService directoryService = null;
	
	
	/**
	 * Custom IBasysConnector specified by the user
	 */
	protected IBasysConnector connector = null;
	
	
	
	
	/**
	 * Constructor - create a new HTTP AAS manager
	 * 
	 * @param networkDirectoryService The network directory service to use
	 */
	public ConnectedAssetAdministrationShellManager(IDirectoryService networkDirectoryService, IBasysConnector basysConnector) {
		// Set directory service reference
		directoryService = networkDirectoryService;
		
		// Set connector reference
		setConnector(basysConnector);
	}
	
	
	/**
	 * Creates an Asset Administration Shell based on the given descriptor structure. For HTTP/REST, this must be called on 
	 * the server that provides the AAS.  
	 */
	@Override
	public IConnectedAssetAdministrationShell createAAS(AssetAdministrationShell aas) throws Exception {
		throw new RuntimeException("Not implemented");
	}
	
	
	/**
	 * Set a Basys connector
	 */
	public void setConnector(IBasysConnector c) {
		// Store connector instance
		connector = c;
	}


	/**
	 * Create a connected Asset Administration Shell proxy
	 */
	@Override
	public IConnectedAssetAdministrationShell retrieveAAS(String assId) {
		// Get AAS from directory
		String addr = null;

		// Lookup address in directory server
		addr = directoryService.lookup(assId);
		
		// Handle the case that AAS was not found
		if (addr == null) throw new ResourceNotFoundException(assId);

		// Instantiate asset administration shell proxy
		return new ConnectedAssetAdministrationShell(this, assId, addr, this.connector);
	}


	/**
	 * Retrieve all available asset administration shells
	 * 
	 * This function returns the available asset administration shells in the local scope. Usually these are the shells that are
	 * managed by the local directory server.
	 */
	@Override
	public Collection<IConnectedAssetAdministrationShell> retrieveAASAll() {
		throw new FeatureNotImplementedException();
	}


	/**
	 * Deletes an asset administration shell from the system.
	 */
	@Override
	public void deleteAAS(String id) throws Exception {
		throw new FeatureNotImplementedException();		
	}

	
	/**
	 * Create a connected asset administration shell proxy
	 */
	public IConnectedAssetAdministrationShell retrieveAASProxy(IElementReference elementRef) {
		// Return connected AAS
		return retrieveAAS(elementRef.getAASID());
	}

	
	/**
	 * Create a connected sub model proxy
	 */
	public ISubModel retrieveSubModelProxy(IElementReference elementRef) {
		// Store network address
		String addr = null;
		
		// Lookup address in directory server
		addr = directoryService.lookup(BaSysID.instance.buildPath(elementRef.getAASID(), elementRef.getSubModelID()));
		
		// Handle the case that AAS was not found
		if (addr == null) throw new ResourceNotFoundException(elementRef.getSubModelID()+"."+elementRef.getAASID());
		
		// Instantiate sub model
		return new ConnectedSubmodel(this, elementRef.getAASID(), elementRef.getSubModelID(), addr, this.connector);
	}
	
	
	/**
	 * Create a connected property proxy
	 */
	public IProperty retrievePropertyProxy(IElementReference elementRef) {
		// Store BaSys path and resolved network address
		String path = BaSysID.instance.buildPath(elementRef.getAASID(), elementRef.getSubModelID());
		String addr = null;

		// Lookup address in directory server
		addr = directoryService.lookup(path);
		
		// Handle the case that sub model was not found
		if (addr == null) throw new ResourceNotFoundException(path);
		
		// Return specific property class
		if (elementRef.isMap()) {
			return new ConnectedMapProperty(elementRef.getAASID(), elementRef.getSubModelID(), elementRef.getPathToProperty(), addr, this.connector, this);
		}
		
		if (elementRef.isCollection()) {
			return new ConnectedCollectionProperty(elementRef.getAASID(), elementRef.getSubModelID(), elementRef.getPathToProperty(), addr, this.connector, this);
		}
		
		return new ConnectedSingleProperty(elementRef.getAASID(), elementRef.getSubModelID(), elementRef.getPathToProperty(), addr, this.connector, this);
	}


	/**
	 * Create a connected operation proxy
	 */
	public IOperation retrieveOperationProxy(IElementReference elementRef) {
		// Store BaSys path and resolved network address
		String path = BaSysID.instance.buildPath(elementRef.getAASID(), elementRef.getSubModelID());
		String addr = null;
		
		// Lookup address in directory server
		addr = directoryService.lookup(path);
		
		// Handle the case that AAS was not found
		if (addr == null) throw new ResourceNotFoundException(path);
		
		// Instantiate sub model
		return new ConnectedOperation(elementRef.getAASID(), elementRef.getSubModelID(), elementRef.getPathToProperty(), addr, this.connector);
	}
}

