package org.eclipse.basyx.testsuite.support.restAAS;

import org.eclipse.basyx.aas.backend.modelprovider.http.HTTPProvider;
import org.eclipse.basyx.aas.impl.provider.JavaObjectProvider;



/**
 * Servlet interface for AAS
 * 
 * @author kuhn
 *
 */
public class Servlet_RestAAS_Submodels extends HTTPProvider<JavaObjectProvider> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public Servlet_RestAAS_Submodels() {
		// Invoke base constructor
		super(new JavaObjectProvider());
		
		// Register AAS
		this.getBackendReference().addModel(new RestAAS());
		
		// Register provided models and AAS
		this.getBackendReference().addModel(new Submodel_RestAAS_Description(), "RestAAS");
		this.getBackendReference().addModel(new Submodel_RestAAS_Status(),      "RestAAS");
	}
}
