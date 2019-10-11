package org.eclipse.basyx.examples.snippets.undoc.aas.embedhttp;

import java.util.HashMap;

import org.eclipse.basyx.vab.core.IModelProvider;





/**
 * Implements an embedded HTTP server that hosts BaSys sub models
 * 
 * @author kuhn
 *
 */
public class EmbeddedHTTPSubModelServer extends EmbeddedHTTPServer {

	
	/**
	 * Constructor
	 */
	public EmbeddedHTTPSubModelServer(String context, IModelProvider provider) {
		// Invoke base constructor
		super(context, provider);

		// Prepare model provider for hosting a sub model
		try {
			provider.createValue("", new HashMap<String, Object>());
			provider.createValue("aas", new HashMap<String, Object>());
			provider.createValue("aas/submodels", new HashMap<String, Object>());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

