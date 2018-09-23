package org.eclipse.basyx.aas.backend;

import java.util.Collection;

import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.exception.ResourceNotFoundException;
import org.eclipse.basyx.aas.api.manager.IAssetAdministrationShellManager;
import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.IOperation;
import org.eclipse.basyx.aas.api.resources.basic.IProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.api.services.IDirectoryService;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.backend.connector.ConnectorProvider;
import org.eclipse.basyx.aas.impl.tools.BaSysID;

/**
 * Implement a AAS manager backend that communicates via HTTP/REST
 * 
 * ToDo: - Access check (set) - Frozen models - Operations - create/delete -
 * Events - HTTP server test suite that does not need this SDK - Finish JavaDoc
 * comments - Dynamic deployment of AAS/Submodels - Default deployment target
 * for submodels - Custom deployment target for submodels (technology
 * independent)
 * 
 * @author kuhn
 *
 */
public class ConnectedAssetAdministrationShellManager implements IAssetAdministrationShellManager {

	/**
	 * Directory service reference
	 */
	protected IDirectoryService directoryService = null;
	private ConnectorProvider providerProvider;

	/**
	 * Constructor - create a new HTTP AAS manager
	 * 
	 * @param networkDirectoryService
	 *            The network directory service to use
	 */
	public ConnectedAssetAdministrationShellManager(IDirectoryService networkDirectoryService, ConnectorProvider providerProvider) {
		// Set directory service reference
		directoryService = networkDirectoryService;

		// Set connector reference
		this.providerProvider = providerProvider;
	}

	/**
	 * Creates an Asset Administration Shell based on the given descriptor
	 * structure. For HTTP/REST, this must be called on the server that provides the
	 * AAS.
	 */
	@Override
	public IAssetAdministrationShell createAAS(IAssetAdministrationShell aas) throws Exception {
		throw new RuntimeException("Not implemented");
	}

	/**
	 * Create a connected Asset Administration Shell proxy
	 */
	@Override
	public IAssetAdministrationShell retrieveAAS(String assId) {
		// Get AAS from directory
		String addr = null;

		// Lookup address in directory server
		addr = directoryService.lookup(assId);

		// Handle the case that AAS was not found
		if (addr == null)
			throw new ResourceNotFoundException(assId);

		// Instantiate asset administration shell proxy
		return new ConnectedAssetAdministrationShell(this, assId, getProvider(addr));
	}

	private IModelProvider getProvider(String addr) {
		return providerProvider.getProvider(addr);
	}

	/**
	 * Retrieve all available asset administration shells
	 * 
	 * This function returns the available asset administration shells in the local
	 * scope. Usually these are the shells that are managed by the local directory
	 * server.
	 */
	@Override
	public Collection<IAssetAdministrationShell> retrieveAASAll() {
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
	public IAssetAdministrationShell retrieveAASProxy(IElementReference elementRef) {
		// Return connected AAS
		return retrieveAAS(elementRef.getAASID());
	}

	/**
	 * Create a connected sub model proxy
	 */
	public ISubModel retrieveSubModelProxy(IElementReference elementRef) {
		// Store network address
		String addr = null;

		String path = BaSysID.instance.buildPath(elementRef.getAASID(), elementRef.getSubModelID());

		// Lookup address in directory server
		addr = directoryService.lookup(path);

		// Handle the case that AAS was not found
		if (addr == null)
			throw new ResourceNotFoundException(path);

		// Instantiate sub model
		return new ConnectedSubmodel(this, elementRef.getAASID(), elementRef.getSubModelID(), getProvider(addr));
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
		if (addr == null)
			throw new ResourceNotFoundException(path);

		// Return specific property class
		IProperty p;
		if (elementRef.isMap()) {
			p = new ConnectedMapProperty(elementRef.getAASID(), elementRef.getSubModelID(), elementRef.getPathToProperty(), getProvider(addr), this);
		} else if (elementRef.isCollection()) {
			p = new ConnectedCollectionProperty(elementRef.getAASID(), elementRef.getSubModelID(), elementRef.getPathToProperty(), getProvider(addr), this);
		} else if (elementRef.isPropertyContainer()) {
			p = new ConnectedContainerProperty(elementRef.getAASID(), elementRef.getSubModelID(), elementRef.getPathToProperty(), getProvider(addr), this);
		} else {
			p = new ConnectedSingleProperty(elementRef.getAASID(), elementRef.getSubModelID(), elementRef.getPathToProperty(), getProvider(addr), this);
		}

		String prop = elementRef.getPathToProperty();
		if (prop.contains(".")) {
			p.setId(prop.substring(prop.lastIndexOf(".") + 1));
		} else {
			p.setId(prop);
		}
		return p;
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
		if (addr == null)
			throw new ResourceNotFoundException(path);

		// Instantiate sub model
		return new ConnectedOperation(elementRef.getAASID(), elementRef.getSubModelID(), elementRef.getPathToProperty(), getProvider(addr));
	}
}
