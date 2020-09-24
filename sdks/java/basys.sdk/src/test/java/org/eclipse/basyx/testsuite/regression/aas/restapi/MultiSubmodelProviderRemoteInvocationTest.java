package org.eclipse.basyx.testsuite.regression.aas.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.memory.InMemoryRegistry;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.testsuite.regression.submodel.restapi.SimpleAASSubmodel;
import org.eclipse.basyx.vab.protocol.basyx.connector.BaSyxConnectorProvider;
import org.eclipse.basyx.vab.protocol.basyx.server.BaSyxTCPServer;
import org.eclipse.basyx.vab.service.api.BaSyxService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the capability remote submodel invocation from registry 
 * in VABMultiSubmodelProvider
 * 
 * @author haque
 */
public class MultiSubmodelProviderRemoteInvocationTest {
	private static final IIdentifier AASID1 = new ModelUrn("aas");
	private static final String AASIDSHORT1 = "aasIdShort1";

	private static final IIdentifier REMOTESMID = new ModelUrn("remoteSm");
	private static final String REMOTESMIDSHORT = "remoteSmIdShort";
	private static final String REMOTEPATH = "/aas/submodels/" + REMOTESMIDSHORT;

	private List<BaSyxService> services = new ArrayList<>();

	private VABMultiSubmodelProvider provider;

	@Before
	public void init() {
		// Creating a new AAS Registry
		IAASRegistryService registry = new InMemoryRegistry();
		
		
		// Create descriptors for AAS and submodels
		String aasEndpoint = "basyx://localhost:8000/aas";
		String remoteSmEndpoint = "basyx://localhost:8001/submodel";

		AASDescriptor aasDesc = new AASDescriptor(AASIDSHORT1, AASID1, aasEndpoint);
		aasDesc.addSubmodelDescriptor(new SubmodelDescriptor(REMOTESMIDSHORT, REMOTESMID, remoteSmEndpoint));


		// Register Asset Administration Shells
		registry.register(aasDesc);
		

		// Create a VABMultiSubmodelProvider using the registry and a http connector
		provider = new VABMultiSubmodelProvider(registry, new BaSyxConnectorProvider());
		
		// Create and add an AAS to the provider with same id as the AAS in the registry
		AssetAdministrationShell aas = new AssetAdministrationShell();
		aas.setIdShort(AASIDSHORT1);
		aas.setIdentification(AASID1);
		provider.setAssetAdministrationShell(new AASModelProvider(aas));

		// Create the remote SM
		SubModel remoteSm = new SimpleAASSubmodel(REMOTESMIDSHORT);
		remoteSm.setIdentification(REMOTESMID.getIdType(), REMOTESMID.getId());

		// Setup and start the BaSyx TCP servers
		services.add(new BaSyxTCPServer<>(provider, 8000));
		services.add(new BaSyxTCPServer<>(new SubModelProvider(remoteSm), 8001));

		services.forEach(b -> b.start());
	}
	
	/**
	 * Checks if GET is correctly forwarded by checking if the Id of the remote
	 * submodel can be retrieved
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetModelPropertyValue() throws Exception {
		SubModel sm = getRemoteSubmodel();
		assertEquals(sm.getIdentification().getId(), REMOTESMID.getId());
	}
	
	/**
	 * Checks if SET is correctly forwarded by checking if a property value of the
	 * remote submodel can be changed
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSetModelPropertyValue() throws Exception {
		int newVal = 0;
		provider.setModelPropertyValue(REMOTEPATH + "/properties/" + SimpleAASSubmodel.INTPROPIDSHORT + "/" + Property.VALUE, newVal);

		SubModel sm = getRemoteSubmodel();
		assertEquals(newVal, sm.getProperties().get(SimpleAASSubmodel.INTPROPIDSHORT).get());
	}
	
	/**
	 * Checks if CREATE is correctly forwarded by checking if a new property can be
	 * created in the remote submodel
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCreateModelPropertyValue() throws Exception {
		Property p = new Property(5);
		String testPropIdShort = "testProperty";
		p.setIdShort(testPropIdShort);

		provider.createValue(REMOTEPATH + "/submodelElements", p);

		assertTrue(getRemoteSubmodel().getProperties().containsKey(testPropIdShort));
	}
	
	/**
	 * Checks if DELETE is correctly forwarded by checking if a property can be
	 * deleted in the remote submodel
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteModelPropertyValue() throws Exception {
		provider.deleteValue(REMOTEPATH + "/properties/" + SimpleAASSubmodel.INTPROPIDSHORT);
		assertFalse(getRemoteSubmodel().getProperties().containsKey(SimpleAASSubmodel.INTPROPIDSHORT));
	}

	/**
	 * Checks if INVOKE is correctly forwarded by checking if an operation can be
	 * called in the remote submodel
	 * 
	 * @throws Exception
	 */
	@Test
	public void testInvoke() throws Exception {
		assertTrue((Boolean) provider.invokeOperation(REMOTEPATH + "/operations/" + SimpleAASSubmodel.OPERATIONSIMPLEIDSHORT));
	}

	@SuppressWarnings("unchecked")
	private SubModel getRemoteSubmodel() {
		return SubModel.createAsFacade((Map<String, Object>) provider.getModelPropertyValue(REMOTEPATH));
	}

	@After
	public void tearDown() {
		services.forEach(b -> b.stop());
	}
}
