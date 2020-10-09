package org.eclipse.basyx.regression.AASServer;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.testsuite.regression.submodel.restapi.SimpleAASSubmodel;

public class SimpleNoOpAASSubmodel extends SimpleAASSubmodel {

	public SimpleNoOpAASSubmodel() {
		this("SimpleAASSubmodel");
	}

	public SimpleNoOpAASSubmodel(String idShort) {
		super(idShort);

		// Remove operations
		deleteSubmodelElement("complex");
		deleteSubmodelElement("simple");
		deleteSubmodelElement("exception1");
		deleteSubmodelElement("exception2");

		Map<String, ISubmodelElement> elems = this.getSubmodelElements();
		SubmodelElementCollection root = (SubmodelElementCollection) elems.get("containerRoot");
		SubmodelElementCollection opContainer = (SubmodelElementCollection) root.getSubmodelElement("container");
		opContainer.deleteSubmodelElement("operationId");
		Operation opReplacement = new Operation("operationId");
		opContainer.addSubModelElement(opReplacement);
	}

}
