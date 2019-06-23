package org.eclipse.basyx.components.servlet.submodel;

import java.util.HashMap;

import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.eclipse.basyx.vab.provider.lambda.VABLambdaProvider;



/**
 * Dynamic model provider servlet. This model provider can host any VAB objects, but is also prepared to host the specific paths to aas
 * and sub models.
 * 
 * @author kuhn
 *
 */
public class DynamicModelProviderServlet extends VABHTTPInterface<VABHashmapProvider> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public DynamicModelProviderServlet() {
		// Invoke base constructor, instantiate a VAB object
		// - This object initially is empty, it is therefore described by an empty map
		super(new VABLambdaProvider(new HashMap<>()));

		// Add base path for serving AAS and sub models
		try {
			this.getModelProvider().createValue("", new HashMap<String, Object>());
			this.getModelProvider().createValue("aas", new HashMap<String, Object>());
			this.getModelProvider().createValue("aas/submodels", new HashMap<String, Object>());
		} catch (Exception e) {
			// Output exception...
			e.printStackTrace();
		}
		
		System.out.println("PENN:");
		
		
		//BaSyxTCPServer<VABMultiSubmodelProvider<?>> server = new BaSyxTCPServer<VABMultiSubmodelProvider<?>>(new VABMultiSubmodelProvider<VABHashmapProvider>("urn:de.FHG:devices.es.iese:SampleSM:1.0:3:x-509#003", new VABHashmapProvider(submodel)), 9998);
	}
}
