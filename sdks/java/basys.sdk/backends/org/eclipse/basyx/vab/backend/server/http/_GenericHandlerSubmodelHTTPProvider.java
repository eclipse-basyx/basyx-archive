package org.eclipse.basyx.vab.backend.server.http;

import org.eclipse.basyx.aas.impl.provider.JavaHandlerProvider;
import org.eclipse.basyx.aas.impl.provider.javahandler.genericsm.GenericHandlerSubmodel;




/**
 * Servlet interface for stubbed sub model
 * 
 * @author kuhn
 *
 */
public class _GenericHandlerSubmodelHTTPProvider extends _HTTPProvider<JavaHandlerProvider> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public _GenericHandlerSubmodelHTTPProvider() {
		// Invoke base constructor
		super(new JavaHandlerProvider());
	}

	
	/**
	 * Constructor
	 */
	public _GenericHandlerSubmodelHTTPProvider(GenericHandlerSubmodel submodel, String modelScope) {
		// Invoke base constructor
		super(new JavaHandlerProvider());

		// Register provided models and AAS
		this.getBackendReference().addScopedModel(submodel, modelScope);
	}
}
