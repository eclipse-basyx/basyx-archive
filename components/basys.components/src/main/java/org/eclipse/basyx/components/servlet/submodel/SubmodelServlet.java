package org.eclipse.basyx.components.servlet.submodel;

import org.eclipse.basyx.aas.backend.provider.SubModelProvider;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.vab.backend.server.http.VABHTTPInterface;

/**
 * A sub model servlet class that exports a given sub model
 * 
 * @author kuhn
 *
 */
public class SubmodelServlet extends VABHTTPInterface<SubModelProvider> {

	/**
	 * ID of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor
	 */
	public SubmodelServlet() {
		// Invoke base constructor
		super(new SubModelProvider());
	}

	/**
	 * Constructor with a predefined submodel
	 */
	public SubmodelServlet(SubModel exportedModel) {
		// Invoke base constructor
		super(new SubModelProvider(exportedModel));
	}
}
