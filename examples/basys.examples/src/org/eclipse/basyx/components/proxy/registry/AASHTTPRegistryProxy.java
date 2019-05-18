package org.eclipse.basyx.components.proxy.registry;

import java.net.URLEncoder;
import java.util.Map;

import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.aas.backend.http.tools.factory.DefaultTypeFactory;
import org.eclipse.basyx.tools.webserviceclient.WebServiceRawClient;
import basys.examples.aasdescriptor.AASDescriptor;
import basys.examples.aasdescriptor.ModelUrn;




/**
 * Local proxy class that hides HTTP calls to BaSys registry
 * 
 * @author kuhn
 *
 */
public class AASHTTPRegistryProxy implements AASRegistryProxyIF {

	
	/**
	 * Store AAS registry URL
	 */
	protected String aasRegistryURL = null;

	
	/**
	 * Invoke BaSyx service calls via web services
	 */
	protected WebServiceRawClient client = null;
	
	
	/**
	 * JSON serializer
	 */
	protected GSONTools serializer = null;

	
	
	
	
	/**
	 * Constructor
	 */
	public AASHTTPRegistryProxy(String aasRegURL) {
		// Store URL
		aasRegistryURL = aasRegURL;
		
		// Create web service client
		client = new WebServiceRawClient();
		
		// Create serializer
		serializer = new GSONTools(new DefaultTypeFactory());
	}
	
	
	/**
	 * Register AAS descriptor in registry, delete old registration
	 */
	@Override
	public void register(ModelUrn aasID, AASDescriptor deviceAASDescriptor) {
		// Invoke delete operation of AAS registry
		try {client.delete(aasRegistryURL+"/api/v1/registry/"+URLEncoder.encode(aasID.getURN(), "UTF-8"));} catch (Exception e) {e.printStackTrace();}

		// Perform web service call to registry
		client.post(aasRegistryURL+"/api/v1/registry", serializer.getJsonString(serializer.serialize(deviceAASDescriptor)));
	}

	
	/**
	 * Register AAS descriptor in registry
	 */
	@Override
	public void registerOnly(AASDescriptor deviceAASDescriptor) {
		// Perform web service call to registry
		client.post(aasRegistryURL+"/api/v1/registry", serializer.getJsonString(serializer.serialize(deviceAASDescriptor)));
	}
	
	
	/**
	 * Delete AAS descriptor from registry
	 */
	@Override
	public void delete(ModelUrn aasID) {
		// Invoke delete operation of AAS registry
		try {client.delete(aasRegistryURL+"/api/v1/registry/"+URLEncoder.encode(aasID.getURN(), "UTF-8"));} catch (Exception e) {e.printStackTrace();}
	}
	
	
	/**
	 * Lookup device AAS
	 */
	@Override @SuppressWarnings("unchecked")
	public AASDescriptor lookup(ModelUrn aasID) {
		// Lookup AAS from AAS directory, get AAS descriptor
		String jsonData = client.get(aasRegistryURL+"/api/v1/registry/"+aasID.getEncodedURN());
		
		// Deserialize AAS descriptor
		AASDescriptor aasDescriptor = new AASDescriptor((Map<String, Object>) serializer.deserialize(serializer.getMap(serializer.getObjFromJsonStr(jsonData))));
		
		// Return AAS descriptor
		return aasDescriptor;
	}
}

