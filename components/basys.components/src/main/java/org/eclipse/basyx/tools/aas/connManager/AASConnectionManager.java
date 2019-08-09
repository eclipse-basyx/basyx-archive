package org.eclipse.basyx.tools.aas.connManager;

import java.util.Map;

import org.eclipse.basyx.aas.backend.connector.MetaprotocolHandler;
import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.aas.backend.http.tools.factory.DefaultTypeFactory;
import org.eclipse.basyx.tools.aasdescriptor.AASDescriptor;
import org.eclipse.basyx.tools.aasdescriptor.SubmodelDescriptor;
import org.eclipse.basyx.tools.modelurn.ModelUrn;
import org.eclipse.basyx.tools.webserviceclient.WebServiceRawClient;
import org.eclipse.basyx.vab.core.IConnectorProvider;
import org.eclipse.basyx.vab.core.IDirectoryService;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.core.tools.VABPathTools;

/**
 * Specific connection manager class that connects to AAS and AAS sub models
 * 
 * @author kuhn
 *
 */
public class AASConnectionManager extends VABConnectionManager {

	/**
	 * JSON serializer
	 */
	protected GSONTools serializer = null;
	private MetaprotocolHandler handler = new MetaprotocolHandler();

	/**
	 * Invoke BaSyx service calls via web services
	 */
	protected WebServiceRawClient client = null;

	/**
	 * Constructor
	 * 
	 * @param networkDirectoryService
	 *            Directory service instance
	 * @param providerProvider
	 *            Connection provider instance
	 */
	public AASConnectionManager(IDirectoryService networkDirectoryService, IConnectorProvider providerProvider) {
		// Invoke base constructor
		super(networkDirectoryService, providerProvider);

		// Create serializer
		serializer = new GSONTools(new DefaultTypeFactory());
	}

	/**
	 * Connect to an Asset Administration Shell
	 * 
	 * @param urn
	 *            the URN that describes the element.
	 */
	@SuppressWarnings("unchecked")
	public VABElementProxy connectToAAS(ModelUrn aasUrn) {
		// Lookup AAS descriptor
		String serializedAASDesc = directoryService.lookup(aasUrn.getEncodedURN());

		System.out.println("ATTEMPT:" + serializedAASDesc);

		try {
			// Deserialize AAS descriptor
			Object obj = handler.verify(serializedAASDesc);
			AASDescriptor aasDescriptor = new AASDescriptor(((Map<String, Object>) obj));
			// Get AAD address from AAS descriptor
			String addr = aasDescriptor.getFirstEndpoint();

			// Return a new VABElementProxy
			return new VABElementProxy(VABPathTools.removeAddressEntry(addr), providerProvider.getConnector(addr));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Connect to an Asset Administration Shell
	 * 
	 * @param urn
	 *            the URN that describes the element.
	 */
	@SuppressWarnings("unchecked")
	public VABElementProxy connectToAASSubModel(ModelUrn aasUrn, String subModelID) {
		// Lookup AAS descriptor
		String serializedAASDesc = directoryService.lookup(aasUrn.getEncodedURN());

		// Deserialize AAS descriptor

		try {
			Object obj = handler.verify(serializedAASDesc);
			AASDescriptor aasDescriptor = new AASDescriptor(((Map<String, Object>) obj));

			// Locate sub model
			SubmodelDescriptor smdescr = aasDescriptor.getSubModelDescriptor(subModelID);
			// - Get submodel endpoint
			String addr = smdescr.getFirstEndpoint();

			// Return a new VABElementProxy
			return new VABElementProxy(VABPathTools.removeAddressEntry(addr), providerProvider.getConnector(addr));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Connect to an VAB element on an HTTP server and add an URL prefix
	 * 
	 * @param urn
	 *            the URN that describes the element.
	 * @param prefix
	 *            the prefix will be added to the address
	 */
	@Override
	public VABElementProxy connectToHTTPVABElement(String urn, String prefix) {
		// Get AAS from directory
		String addr = "";

		// Lookup address in directory server
		addr = directoryService.lookup(urn);
		// - Add prefix to addr
		addr = VABPathTools.concatenatePaths(addr, prefix);

		// Return a new VABElementProxy
		return new VABElementProxy(VABPathTools.removeAddressEntry(addr), providerProvider.getConnector(addr));
	}

	/**
	 * Connect to an VAB element on an HTTP server using a qualified URL
	 * 
	 * @param url
	 *            the URL that describes the element location.
	 */
	@Override
	public VABElementProxy connectToVABElementByURL(String url) {

		// Return a new VABElementProxy
		// - Do not pass URL here to provider as address, as the url parameter is already absolute and contains the
		// address.
		return new VABElementProxy(VABPathTools.removeAddressEntry(url), providerProvider.getConnector(url));
	}

}
