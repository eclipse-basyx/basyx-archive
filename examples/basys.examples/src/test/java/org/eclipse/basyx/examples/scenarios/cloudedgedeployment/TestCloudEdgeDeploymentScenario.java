package org.eclipse.basyx.examples.scenarios.cloudedgedeployment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.manager.ConnectedAssetAdministrationShellManager;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCloudEdgeDeploymentScenario {

	private static CloudEdgeDeploymentScenario scenario;
	
	@BeforeClass
	public static void startup() throws Exception {
		scenario = new CloudEdgeDeploymentScenario();
	}
	
	@AfterClass
	public static void tearDown() {
		scenario.stop();
	}

	private IAASRegistryService getRegistry() {
		return new AASRegistryProxy(CloudEdgeDeploymentScenario.registryPath);
	}

	/**
	 * This tests if all the expected registry entries are present
	 * 
	 */
	@Test
	public void testRegistry() throws Exception {
		List<AASDescriptor> aasDescriptors = getRegistry().lookupAll();
		assertEquals(1, aasDescriptors.size());
		
		AASDescriptor aasDescriptor = aasDescriptors.get(0);
		
		// Check if aasDescriptor has the correct endpoint
		checkEndpoint(ComponentBuilder.AAS_ENDPOINT, aasDescriptor.getEndpoints());
		
		// Check if aasDescriptor has the correct number of SMDescriptors
		assertEquals(2, aasDescriptor.getSubModelDescriptors().size());
		
		// Iterate over the Collection of SMDescriptors and
		// test if both have the expected idShort and endpoint
		for(SubmodelDescriptor smDescriptor: aasDescriptor.getSubModelDescriptors()) {
			if(smDescriptor.getIdShort().equals(ComponentBuilder.EDGESM_ID_SHORT)) {
				checkEndpoint(ComponentBuilder.EDGESM_ENDPOINT, smDescriptor.getEndpoints());
			} else if(smDescriptor.getIdShort().equals(ComponentBuilder.DOCUSM_ID_SHORT)) {
				checkEndpoint(ComponentBuilder.DOCUSM_ENDPOINT, smDescriptor.getEndpoints());
			} else {
				// There is a SMDescriptor with an unexpected idShort
				fail();
			}
		}
	}
	
	/**
	 * This tests if the AAS is retrievable, has the correct idShot and
	 * contains the expected Submodels 
	 * 
	 */
	@Test
	public void testAAS() throws Exception {
		ConnectedAssetAdministrationShellManager manager =
				new ConnectedAssetAdministrationShellManager(getRegistry());
		
		IAssetAdministrationShell aas = manager.retrieveAAS(scenario.aasIdentifier);
		
		// Check if it has the correct idShort
		assertEquals(ComponentBuilder.AAS_ID_SHORT, aas.getIdShort());
		
		// Get the Submodels and check if both expected SMs are present 
		Map<String, ISubModel> submodels = aas.getSubModels();
		
		assertTrue(submodels.containsKey(ComponentBuilder.EDGESM_ID_SHORT));
		assertTrue(submodels.containsKey(ComponentBuilder.DOCUSM_ID_SHORT));
	}
	
	/**
	 * This test checks if the DocuSM is retrievable, has the correct idShort and
	 * contains the correct number of SubmodelElements
	 * 
	 */
	@Test
	public void testDocuSM() {
		ConnectedAssetAdministrationShellManager manager =
				new ConnectedAssetAdministrationShellManager(getRegistry());
		
		ISubModel docuSM = manager.retrieveSubModel(scenario.aasIdentifier, scenario.docuSmIdentifier);
		
		// Check if it has the correct idShort, and 1 SubmodelElement
		assertEquals(ComponentBuilder.DOCUSM_ID_SHORT, docuSM.getIdShort());
		assertEquals(1, docuSM.getSubmodelElements().size());
	}
	
	/**
	 * This test checks if the EdgeSM is retrievable, has the correct idShort and
	 * contains the correct number of SubmodelElements
	 * 
	 */
	@Test
	public void testEdgeSM() {
		ConnectedAssetAdministrationShellManager manager =
				new ConnectedAssetAdministrationShellManager(getRegistry());
		
		ISubModel edgeSM = manager.retrieveSubModel(scenario.aasIdentifier, scenario.edgeSmIdentifier);
		
		// Check if it has the correct idShort, and 1 SubmodelElement
		assertEquals(ComponentBuilder.EDGESM_ID_SHORT, edgeSM.getIdShort());
		assertEquals(1, edgeSM.getSubmodelElements().size());
	}
	
	/**
	 * This is a helper function used to check if a given endpoint is
	 * contained in a collection.
	 * 
	 * @param expected the expected endpoint address
	 * @param actual the endpoint collection
	 */
	private void checkEndpoint(String expected, Collection<Map<String, Object>> actual) {
		assertEquals(1, actual.size());
		Map<String, Object> endpoint = new ArrayList<Map<String, Object>>(actual).get(0);
		assertEquals(expected, endpoint.get("address"));
	}
}
