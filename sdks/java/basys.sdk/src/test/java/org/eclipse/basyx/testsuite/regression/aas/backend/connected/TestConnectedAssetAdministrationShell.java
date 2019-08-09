package org.eclipse.basyx.testsuite.regression.aas.backend.connected;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.backend.connected.TypeDestroyer.TypeDestroyer;
import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
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
	private static final String propId = "propId";
	private static final int propVal = 11;

	IAssetAdministrationShell connectedAAS;

	@Before
	public void build() throws Exception {
		MetaModelElementFactory factory = new MetaModelElementFactory();

		// Create a SubModel containing no operations and one property
		Property p = factory.create(new Property(), propVal);
		p.setId(propId);

		SubModel sm = factory.create(new SubModel(), Collections.singletonList(p), new ArrayList<>());
		sm.setId(smId);

		// Create Set containing reference to the created SubModel
		Set<String> refs = new HashSet<>();
		refs.add(smId);

		// Create an AAS containing a reference to the created SubModel
		AssetAdministrationShell aas = factory.create(new AssetAdministrationShell(), refs);
		aas.setId(aasId);
	
		VABMultiSubmodelProvider provider = new VABMultiSubmodelProvider();
		provider.addSubmodel(smId, new VirtualPathModelProvider(TypeDestroyer.destroyType(sm)));
		provider.setAssetAdministrationShell(new VirtualPathModelProvider(TypeDestroyer.destroyType(aas)));
	
		// Add the AAS provider to the ConnectionManagerStub
		VABConnectionManagerStub connectionStub = new VABConnectionManagerStub();
		connectionStub.addProvider(aasId,"",  provider);
		connectionStub.addProvider(smId, "", provider);
	
		// Create connection manager using the dummy
		ConnectedAssetAdministrationShellManager manager = new ConnectedAssetAdministrationShellManager(connectionStub);

		// Create ConnectedAssetAdministrationShell
		connectedAAS = manager.retrieveAAS(aasId);
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
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetSubmodel() throws Exception {
		// Check if the number of SubModels is as expected
		assertEquals(1, connectedAAS.getSubModels().size());

		// Check if the contained SubModel id is as expected
		assertTrue(connectedAAS.getSubModels().containsKey(smId));

		// Check if the submodel has been retrieved correctly
		ISubModel sm = connectedAAS.getSubModels().get(smId);
		ISingleProperty prop = (ISingleProperty) sm.getProperties().get(propId);
		assertEquals(propVal, prop.get());
	}
}
