package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.servlets;

import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.impl.provider.JavaObjectVABMapper;
import org.eclipse.basyx.aas.metamodel.hashmap.VABHashmapProvider;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.Stub1AAS;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub1Submodel;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub2Submodel;
import org.eclipse.basyx.vab.backend.provider.ConsistencyProvider;
import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;
import org.eclipse.basyx.vab.backend.server.http._HTTPProvider;




/**
 * Servlet interface for AAS
 * 
 * @author kuhn
 *
 */
public class Stub1AASServlet extends VABHTTPInterface<VABMultiSubmodelProvider<VABHashmapProvider>> {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public Stub1AASServlet() {
		// Invoke base constructor
		super(new VABMultiSubmodelProvider<VABHashmapProvider>());
		
		JavaObjectVABMapper instanceModelProvider1 = new JavaObjectVABMapper(); // one AAS per JOP
		JavaObjectVABMapper instanceModelProvider2 = new JavaObjectVABMapper(); // one AAS per JOP
		JavaObjectVABMapper instanceModelProvider3 = new JavaObjectVABMapper(); // one AAS per JOP
		
		// Register provided models and AAS
		instanceModelProvider1.addModel(new Stub1AAS());
		instanceModelProvider2.addModel(new Stub1Submodel());
		instanceModelProvider3.addModel(new Stub2Submodel());
		
		// Register provider to MultiSubmodelProvider
		this.getModelProvider().setAssetAdministrationShell(instanceModelProvider1);
		this.getModelProvider().addSubmodel("statusSM", instanceModelProvider2);
		this.getModelProvider().addSubmodel("Stub2SM",  instanceModelProvider3);
		
	}
}
