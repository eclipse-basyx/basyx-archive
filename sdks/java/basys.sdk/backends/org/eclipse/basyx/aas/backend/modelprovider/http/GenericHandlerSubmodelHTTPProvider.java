package org.eclipse.basyx.aas.backend.modelprovider.http;

import org.eclipse.basyx.aas.impl.provider.JavaHandlerProvider;
import org.eclipse.basyx.aas.impl.provider.javahandler.genericsm.GenericHandlerSubmodel;




/**
 * Servlet interface for stubbed sub model
 * 
 * @author kuhn
 *
 */
public class GenericHandlerSubmodelHTTPProvider extends HTTPProvider<JavaHandlerProvider> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public GenericHandlerSubmodelHTTPProvider() {
		// Invoke base constructor
		super(new JavaHandlerProvider());
	}

	
	/**
	 * Constructor
	 */
	public GenericHandlerSubmodelHTTPProvider(GenericHandlerSubmodel submodel, String modelScope) {
		// Invoke base constructor
		super(new JavaHandlerProvider());

		// Register provided models and AAS
		this.getBackendReference().addModel(submodel, modelScope);
	}
}
