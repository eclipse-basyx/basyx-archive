package org.eclipse.basyx.testsuite.support.gateways.stubs.servlets.topo.manufacturing;

import org.eclipse.basyx.aas.backend.modelprovider.http.HTTPProvider;
import org.eclipse.basyx.aas.impl.provider.JavaObjectProvider;



/**
 * Servlet interface for AAS (product_database.office.iese.fraunhofer.de)
 * 
 * @author kuhn
 *
 */
public class Servlet_Line2_Device2_AAS extends HTTPProvider<JavaObjectProvider> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public Servlet_Line2_Device2_AAS() {
		// Invoke base constructor
		super(new JavaObjectProvider());
		
		// Register provided models and AAS
		this.getBackendReference().addModel(new AAS_Line2_Device2());
		
		// Register provided models and AAS
		this.getBackendReference().addModel(new Submodel_Line2_Device2_Description(), "Device2");
		this.getBackendReference().addModel(new Submodel_Line2_Device2_Status(),      "Device2");
	}
}
