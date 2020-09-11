package org.eclipse.basyx.regression.support.bundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.eclipse.basyx.aas.aggregator.AASAggregator;
import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.support.bundle.AASBundle;
import org.eclipse.basyx.support.bundle.AASBundleIntegrator;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for the AASBundelIntegrator
 * 
 * @author conradi
 *
 */
public class TestAASBundleIntegrator {

	private static final String AAS_ID = "TestAAS";
	private static final String SM_ID = "TestSM";
	
	
	private AASAggregator aggregator;
	private List<AASBundle> bundles;
	private AASAggregatorProvider provider;
	
	
	
	@Before
	public void init() {
		aggregator = new AASAggregator();
		provider = new AASAggregatorProvider(aggregator);
		bundles = new ArrayList<>();		
	}
	
	/**
	 * This test loads an AAS and its two Submodels into the Aggregator,
	 * runs the integration with AAS and Submodels with the same IDs, but different content,
	 * checks if integration does NOT replace the models in the Aggregator. 
	 */
	@Test
	public void testIntegrationOfExistingAASAndSM() {

		AssetAdministrationShell aas = getTestAAS();
		
		SubModel sm = getTestSM();
		
		// Load AAS and SM AASAggregator
		pushAAS(aas);
		pushSubmodel(sm, aas.getIdentification());
		
		HashSet<ISubModel> submodels = new HashSet<>();
		submodels.add(sm);
		
		AASBundle bundle = new AASBundle(aas, submodels);
		bundles.add(bundle);
		
		
		assertFalse(AASBundleIntegrator.integrate(aggregator, bundles));
		checkAggregatorContent();
	}
	
	/**
	 * This test loads an AAS into the Aggregator,
	 * runs the integration with the AAS and a SM,
	 * checks if both is present in Aggregator afterwards. 
	 */
	@Test
	public void testIntegrationOfExistingAASAndNonexistingSM() {

		AssetAdministrationShell aas = getTestAAS();
		
		SubModel sm = getTestSM();
		
		// Load only AAS into AASAggregator
		pushAAS(aas);
		
		HashSet<ISubModel> submodels = new HashSet<>();
		submodels.add(sm);
		
		AASBundle bundle = new AASBundle(aas, submodels);
		bundles.add(bundle);
		
		
		assertTrue(AASBundleIntegrator.integrate(aggregator, bundles));
		checkAggregatorContent();
	}
	
	/**
	 * This test loads nothing into the Aggregator,
	 * runs the integration with the AAS and a SM,
	 * checks if both is present in Aggregator afterwards. 
	 */
	@Test
	public void testIntegrationOfNonexistingAASAndSM() {

		AssetAdministrationShell aas = getTestAAS();
		
		SubModel sm = getTestSM();
		
		HashSet<ISubModel> submodels = new HashSet<>();
		submodels.add(sm);
		
		AASBundle bundle = new AASBundle(aas, submodels);
		bundles.add(bundle);
		
		
		assertTrue(AASBundleIntegrator.integrate(aggregator, bundles));
		checkAggregatorContent();
	}
	
	private void checkAggregatorContent() {
		IAssetAdministrationShell aas = aggregator.getAAS(new Identifier(IdentifierType.CUSTOM, AAS_ID));
		assertEquals(AAS_ID, aas.getIdShort());
		IModelProvider provider = aggregator.getAASProvider(new Identifier(IdentifierType.CUSTOM, AAS_ID));
		
		ISubModel sm = (ISubModel) provider.getModelPropertyValue("/aas/submodels/" + SM_ID);
		assertEquals(SM_ID, sm.getIdentification().getId());
	}
	
	private void pushAAS(AssetAdministrationShell aas) {
		aggregator.createAAS(aas);
	}
	
	private void pushSubmodel(SubModel sm, IIdentifier aasIdentifier) {
		provider.createValue("/aasList/" + aasIdentifier.getId() + "/aas/submodels", sm);
	}
	
	private AssetAdministrationShell getTestAAS() {
		AssetAdministrationShell aas = new AssetAdministrationShell();
		aas.setIdentification(IdentifierType.CUSTOM, AAS_ID);
		aas.setIdShort(AAS_ID);
		return aas;
	}
	
	private SubModel getTestSM() {
		SubModel sm = new SubModel();
		sm.setIdShort(SM_ID);
		sm.setIdentification(IdentifierType.CUSTOM, SM_ID);
		return sm;
	}
	
}
