package basys.examples.frontend.client.proxies;

import java.util.Map;

import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.tools.webserviceclient.WebServiceRawClient;
import basys.examples.aasdescriptor.AASDescriptor;
import basys.examples.urntools.ModelUrn;




/**
 * Local proxy class that hides HTTP calls to BaSys registry
 * 
 * @author kuhn
 *
 */
public class AASRegistryProxy {

	
	/**
	 * Store AAS registry URL
	 */
	protected String aasRegistryURL = null;

	
	/**
	 * Invoke BaSyx service calls via web services
	 */
	protected WebServiceRawClient client = null;

	
	
	
	
	/**
	 * Constructor
	 */
	public AASRegistryProxy(String aasRegURL) {
		// Store URL
		aasRegistryURL = aasRegURL;
		
		// Create web service client
		client = new WebServiceRawClient();
	}
	
	
	/**
	 * Register AAS descriptor in registry
	 */
	public void register(AASDescriptor deviceAASDescriptor) {
		// Perform web service call to registry
		client.post(aasRegistryURL+"/api/v1/registry", GSONTools.Instance.getJsonString(GSONTools.Instance.serialize(deviceAASDescriptor)));
	}
	
	
	/**
	 * Lookup device AAS
	 */
	@SuppressWarnings("unchecked")
	public AASDescriptor lookup(ModelUrn aasID) {
		// Lookup AAS from AAS directory, get AAS descriptor
		String jsonData = client.get(aasRegistryURL+"/api/v1/registry/"+aasID.getEncodedURN());
		
		// Deserialize AAS descriptor
		AASDescriptor aasDescriptor = new AASDescriptor((Map<String, Object>) GSONTools.Instance.deserialize(GSONTools.Instance.getMap(jsonData)));
		
		// Return AAS descriptor
		return aasDescriptor;
	}
}

