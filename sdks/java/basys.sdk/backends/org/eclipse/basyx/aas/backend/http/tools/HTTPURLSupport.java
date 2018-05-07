package org.eclipse.basyx.aas.backend.http.tools;



/**
 * Support class that defines the mappings from AAS, Submodel, Property IDS to HTTP URLS and URL paths
 * 
 * @author kuhn
 *
 */
public class HTTPURLSupport {


	/**
	 * Get path to children of sub model 
	 * 
	 * @param baseURL Base path to sub model
	 * @param submodelID Submodel ID
	 * 
	 * FIXME: Name of "children" property
	 */
	public static String xxgetSubmodelChildrenPath(String submodelID) {
		return submodelID+"/children";
	}
}
