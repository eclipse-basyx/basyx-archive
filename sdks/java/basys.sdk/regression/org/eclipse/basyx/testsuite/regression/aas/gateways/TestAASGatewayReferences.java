package org.eclipse.basyx.testsuite.regression.aas.gateways;

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.backend.connector.http.HTTPConnectorProvider;
import org.eclipse.basyx.aas.impl.provider._RESTHTTPClientProvider;
import org.eclipse.basyx.testsuite.support.gateways.common.directory.GatewayTestsuiteDirectoryLine2IESE;
import org.eclipse.basyx.testsuite.support.gateways.stubs.servlets.topo.manufacturing.AAS_Line2_Device2;
import org.eclipse.basyx.testsuite.support.gateways.stubs.servlets.topo.manufacturing.Submodel_Line2_Device2_Description;
import org.junit.jupiter.api.Test;

public class TestAASGatewayReferences {

	/**
	 * Run test case
	 */
	@Test
	void test() {
		// Create model provider
		_RESTHTTPClientProvider subModelProvider = new _RESTHTTPClientProvider("line2.manufacturing.de",
				new GatewayTestsuiteDirectoryLine2IESE(), new HTTPConnectorProvider());

		// - Create AAS and sub model instances
		IAssetAdministrationShell stub1AAS = new AAS_Line2_Device2();
		ISubModel stub1SM = new Submodel_Line2_Device2_Description(stub1AAS);

		// - Add models to provider
		subModelProvider.addScopedModel(stub1AAS);
		subModelProvider.addScopedModel(stub1SM);

		// Get element references from object provider
		Map<String, IElementReference> stub1AASModels = subModelProvider
				.getContainedElements("line2.manufacturing.de/device2/aas/submodels/status/properties"); // FIXME not
																											// able to
																											// resolve
																											// address

		// Print contained elements
		for (Entry<String, IElementReference> entry : stub1AASModels.entrySet()) {
			System.out.println(entry.getKey() + " -> " + entry.getValue());
		}

	}
}
