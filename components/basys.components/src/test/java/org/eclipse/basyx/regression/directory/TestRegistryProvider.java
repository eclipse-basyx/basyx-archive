package org.eclipse.basyx.regression.directory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Integration test for a registry. All registry provider implementations have to pass these tests.
 * 
 * @author espen
 * 
 */
public abstract class TestRegistryProvider {
	// The registry proxy that is used to access the sql servlet
	protected final AASRegistryProxy proxy = new AASRegistryProxy(getProxyProvider());

	// Ids, shortIds and endpoints for registered AAS and submodel
	protected IIdentifier aasId1 = new ModelUrn("urn:de.FHG:devices.es.iese:aas:1.0:1:registryAAS#001");
	protected IIdentifier aasId2 = new ModelUrn("urn:de.FHG:devices.es.iese:aas:1.0:1:registryAAS#002");
	protected IIdentifier smId = new ModelUrn("urn:de.FHG:devices.es.iese:aas:1.0:1:statusSM#001");
	protected String aasIdShort1 = "aasIdShort1";
	protected String aasIdShort2 = "aasIdShort2";
	protected String smIdShort = "smIdShort";
	protected String aasEndpoint1 = "http://www.registrytest.de/aas01/aas";
	protected String aasEndpoint2 = "http://www.registrytest.de/aas02/aas";
	protected String smEndpoint = "http://www.registrytest.de/aas01/aas/submodels/" + smIdShort;

	/**
	 * Getter for the tested registry provider. Tests for actual registry provider
	 * have to realize this method.
	 */
	protected abstract IModelProvider getProxyProvider();

	/**
	 * During setup of the tests, new entries are created in the registry using a proxy
	 */
	@Before
	public void setUp() {
		// Create descriptors for AAS and submodels
		AASDescriptor aasDesc1 = new AASDescriptor(aasIdShort1, aasId1, aasEndpoint1);
		aasDesc1.addSubmodelDescriptor(new SubmodelDescriptor(smIdShort, smId, smEndpoint));
		AASDescriptor aasDesc2 = new AASDescriptor(aasIdShort2, aasId2, aasEndpoint2);
		
		// Register Asset Administration Shells
		proxy.register(aasDesc1);
		proxy.register(aasDesc2);
	}
	
	/**
	 * Remove registry entries after each test
	 */
	@After
	public void tearDown() {
		proxy.delete(aasId1);
		proxy.delete(aasId2);
	}

	/**
	 * Tests getting single entries from the registry and validates the result.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetSingleAAS() {
		// Retrieve and check the first AAS
		Object result = proxy.lookupAAS(aasId1);
		AASDescriptor descriptor = new AASDescriptor((Map<String, Object>) result);
		assertEquals(aasId1.getId(), descriptor.getIdentifier().getId());
		assertEquals(aasId1.getIdType(), descriptor.getIdentifier().getIdType());
		assertEquals(aasId1.getIdType(), descriptor.getIdentifier().getIdType());
		assertEquals(aasEndpoint1, descriptor.getFirstEndpoint());
		
		// Check, if the SM descriptor in the AASDescriptor is correct 
		SubmodelDescriptor smDescriptor = descriptor.getSubModelDescriptor(smId.getId());
		assertEquals(smId.getId(), smDescriptor.getIdentifier().getId());
		assertEquals(smId.getIdType(), smDescriptor.getIdentifier().getIdType());
		assertEquals(smIdShort, smDescriptor.get(Referable.IDSHORT));
		assertEquals(smEndpoint, smDescriptor.getFirstEndpoint());
		
		// Retrieve and check the second AAS
		result = proxy.lookupAAS(aasId2);
		descriptor = new AASDescriptor((Map<String, Object>) result);
		assertEquals(aasId2.getId(), descriptor.getIdentifier().getId());
		assertEquals(aasId2.getIdType(), descriptor.getIdentifier().getIdType());
		assertEquals(aasId2.getIdType(), descriptor.getIdentifier().getIdType());
		assertEquals(aasEndpoint2, descriptor.getFirstEndpoint());
	}

	/**
	 * Tests deletion for aas entries
	 */
	@Test
	public void testDeleteCall() {
		// After the setup, both AAS should have been inserted to the registry
		assertNotNull(proxy.lookupAAS(aasId1));
		assertNotNull(proxy.lookupAAS(aasId2));
		
		proxy.delete(aasId2);
		
		// After aas2 has been deleted, only aas1 should be registered
		assertNotNull(proxy.lookupAAS(aasId1));
		assertNull(proxy.lookupAAS(aasId2));
		
		proxy.delete(aasId1);
		
		// After aas2 has been deleted, only aas1 should be registered
		assertNull(proxy.lookupAAS(aasId1));
		assertNull(proxy.lookupAAS(aasId2));
	}
}
