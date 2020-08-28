package org.eclipse.basyx.components.MongoDBAASServer;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.testsuite.regression.submodel.restapi.SimpleAASSubmodel;

public class SimpleNoOpAASSubmodel extends SimpleAASSubmodel {

	public SimpleNoOpAASSubmodel() {
		this("SimpleAASSubmodel");
	}

	public SimpleNoOpAASSubmodel(String idShort) {
		super(idShort);

		// Remove operations
		Map<String, ISubmodelElement> elems = this.getSubmodelElements();
		elems.remove("complex");
		elems.remove("simple");
		elems.remove("exception1");
		elems.remove("exception2");
	}

}
