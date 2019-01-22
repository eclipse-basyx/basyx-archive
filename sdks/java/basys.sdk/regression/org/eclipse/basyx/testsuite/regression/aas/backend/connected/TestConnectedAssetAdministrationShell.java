package org.eclipse.basyx.testsuite.regression.aas.backend.connected;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell_;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel_;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a ConnectedAssetAdministrationShell can be created and used
 * correctly
 * 
 * @author schnicke
 *
 */
public class TestConnectedAssetAdministrationShell {

	// String constants used in this test case
	private static final String smId = "smId";
	private static final String aasId = "aasId";

	VABConnectionManagerStub connectionStub;
	ConnectedAssetAdministrationShell connectedAAS;

	@Before
	public void build() {
		MetaModelElementFactory factory = new MetaModelElementFactory();

		// Create a SubModel containing no operations or properties
		SubModel_ sm = factory.create(new SubModel_(), new ArrayList<>(), new ArrayList<>());
		sm.setId(smId);

		// Create a dummy connection manager containing the created SubModel map
		connectionStub = new VABConnectionManagerStub();

		// Add the SubModel provider to the ConnectionManagerStub
		connectionStub.addProvider(smId, new VABHashmapProvider(sm));

		// Create Set containing reference to the created SubModel
		Set<String> refs = new HashSet<>();
		refs.add(smId);

		// Create an AAS containing a reference to the created SubModel
		AssetAdministrationShell_ aas = factory.create(new AssetAdministrationShell_(), refs);
		aas.setId(aasId);

		// Add the AAS provider to the ConnectionManagerStub
		connectionStub.addProvider(aasId, new VABHashmapProvider(aas));

		// Retrieve the VABElementProxy
		VABElementProxy aasProxy = connectionStub.connectToVABElement(aasId);

		// Create ConnectedAssetAdministrationShell
		connectedAAS = new ConnectedAssetAdministrationShell("", aasProxy, connectionStub);
	}

	/**
	 * Tests the getId() function
	 */
	@Test
	public void testGetId() {
		assertEquals(aasId, connectedAAS.getId());
	}

	/**
	 * Tests retrieving the contained SubModels
	 */
	@Test
	public void testGetRef() {
		// Check if the number of SubModels is as expected
		assertEquals(1, connectedAAS.getSubModels().size());

		// Check if the contained SubModel id is as expected
		assertTrue(connectedAAS.getSubModels().containsKey(smId));
	}
}
