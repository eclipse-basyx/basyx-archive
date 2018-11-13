package org.eclipse.basyx.testsuite.support.gateways.stubs.servlets.topo.manufacturing;

import org.eclipse.basyx.aas.impl.provider.JavaObjectVABMapper;
import org.eclipse.basyx.vab.backend.server.http._HTTPProvider;



/**
 * Servlet interface for AAS (product_database.office.iese.fraunhofer.de)
 * 
 * @author kuhn
 *
 */
public class Servlet_Line2int_Device10_AAS extends _HTTPProvider<JavaObjectVABMapper> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public Servlet_Line2int_Device10_AAS() {
		// Invoke base constructor
		super(new JavaObjectVABMapper());
		
		// Register provided models and AAS
		this.getBackendReference().addScopedModel(new AAS_Line2Int_Device10());
		
		// Register provided models and AAS
		this.getBackendReference().addScopedModel(new Submodel_Line2Int_Device10_Description(), "Device10");
		this.getBackendReference().addScopedModel(new Submodel_Line2Int_Device10_Status(),      "Device10");
	}
}
