package basys.examples.frontend.client.connmanager;

import org.eclipse.basyx.vab.core.IConnectorProvider;
import org.eclipse.basyx.vab.core.IDirectoryService;
import org.eclipse.basyx.vab.core.VABConnectionManager;





/**
 * Connection manager that creates BaSys specific connections on request
 * 
 * @author kuhn
 *
 */
public class BaSysConnectionManager extends VABConnectionManager {

	
	/**
	 * Constructor
	 * 
	 * @param networkDirectoryService Directory service provider
	 * @param providerProvider        Connection provider
	 */
	public BaSysConnectionManager(IDirectoryService networkDirectoryService, IConnectorProvider providerProvider) {
		// Invoke base constructor
		super(networkDirectoryService, providerProvider);
	}



	/**
	 * Connect to an model provider server
	 * 
	 * Model provider servers are network repositories that store models and enable access to them.
	 * 
	 * @param urn the URN that describes the model provider server 
	 */
	public ModelServerProxy connectToModelServer(String urn) {
		// Get AAS from directory
		String addr = null;

		// Lookup address in directory server
		addr = directoryService.lookup(urn);

		// Return a new ModelServerProxy
		return new ModelServerProxy(addr, providerProvider.getConnector(addr));
	}


	/**
	 * Connect to an model provider server through a provided URL
	 * 
	 * @param url to model provider server 
	 */
	public ModelServerProxy connectToModelServerURL(String url) {
		// Return a new ModelServerProxy
		return new ModelServerProxy(url, providerProvider.getConnector(url));
	}
}

