package org.eclipse.basyx.testsuite.regression.aas.aggregator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.junit.Before;
import org.junit.Test;


/**
 * Testsuite for implementations of the IAASAggregator interface
 * 
 * @author conradi, schnicke
 *
 */
public abstract class AASAggregatorSuite {

	protected AssetAdministrationShell aas1;

	// Choose AAS Id that needs encoding due to '/'
	private static final String aas1Id = "aas1/s";
	private static final LangStrings description1 = new LangStrings("en", "This is test AAS 1");
	private static final String aas1Category = "TestCategory1";
	private static final String aas1AltCategory = "OtherTestCategory1";
	
	protected AssetAdministrationShell aas2;
	private static final String aas2Id = "aas2";
	private static final LangStrings description2 = new LangStrings("en", "This is test AAS 2");
	private static final String aas2Category = "TestCategory2";
	
	// initializing dummy test data
	@Before
	public void initAASDummies() {
		aas1 = new AssetAdministrationShell();
		aas1.setIdentification(IdentifierType.CUSTOM, aas1Id);
		aas1.setIdShort(aas1Id);
		aas1.setDescription(description1);
		aas1.setCategory(aas1Category);
		
		aas2 = new AssetAdministrationShell();
		aas2.setIdentification(IdentifierType.CUSTOM, aas2Id);
		aas2.setIdShort(aas2Id);
		aas2.setDescription(description2);
		aas2.setCategory(aas2Category);
	}
	
	protected abstract IAASAggregator getAggregator();
	
	
	@Test
	public void testCreateAndGetAAS() {
		IAASAggregator aggregator = getAggregator();
		
		//create a new AAS
		aggregator.createAAS(aas1);
		
		//get and check the created AAS
		checkAAS1(aggregator.getAAS(new ModelUrn(aas1Id)));
	}
	
	@Test
	public void testGetAASList() throws Exception {
		IAASAggregator aggregator = getAggregator();
		
		// Create two AASs
		aggregator.createAAS(aas1);
		aggregator.createAAS(aas2);
		
		// get the collection of all AASs
		Collection<IAssetAdministrationShell> coll = aggregator.getAASList();
		assertEquals(2, coll.size());
		
		// check the AAS collection
		for (IAssetAdministrationShell aas : coll) {
			if(aas.getIdShort().equals(aas1Id)) {
				checkAAS1(aas);
			} else if(aas.getIdShort().equals(aas2Id)) {
				checkAAS2(aas);
			} else {
				fail();
			}
		}
	}
	
	@Test
	public void testUpdate() throws Exception {
		IAASAggregator aggregator = getAggregator();
		
		// Create a new AAS
		aggregator.createAAS(aas1);
		
		// Get and check the unchanged AAS
		checkAAS1(aggregator.getAAS(new ModelUrn(aas1Id)));

		// Change category of AAS locally
		aas1.setCategory(aas1AltCategory);
		
		// Update the changed AAS
		aggregator.updateAAS(aas1);
		
		// Get the updated AAS and check its category
		IAssetAdministrationShell aas = aggregator.getAAS(new ModelUrn(aas1Id));
		assertEquals(aas1AltCategory, aas.getCategory());
	}
	
	@Test
	public void testDelete() throws Exception {
		IAASAggregator aggregator = getAggregator();
		
		// Create two new AASs
		aggregator.createAAS(aas1);
		aggregator.createAAS(aas2);
		
		// Get AAS collection and check, that both are present
		Collection<IAssetAdministrationShell> coll = aggregator.getAASList();
		assertEquals(2, coll.size());
		
		// Delete one of the AASs
		aggregator.deleteAAS(new ModelUrn(aas1Id));
		
		// Get AAS collection and check, that one of them is deleted
		coll = aggregator.getAASList();
		assertEquals(1, coll.size());
		
		for (IAssetAdministrationShell aas : coll) {
			if(aas.getIdShort().equals(aas1Id)) { //aas1 should be deleted
				fail();
			} else if(aas.getIdShort().equals(aas2Id)) {
				checkAAS2(aas);
			} else {
				fail();
			}
		}
	}
	
	// Methods to verify, that AAS objects contain the correct test data
	private void checkAAS1(Object o) {
		assertTrue(o instanceof AssetAdministrationShell);
		AssetAdministrationShell aas = (AssetAdministrationShell) o;
		
		assertEquals(aas1Id, aas.getIdShort());
		assertEquals(aas1Id, aas.getIdentification().getId());
		assertEquals(description1.get("en"), aas.getDescription().get("en"));
		assertEquals(aas1Category, aas.getCategory());
	}
	
	private void checkAAS2(Object o) {
		assertTrue(o instanceof AssetAdministrationShell);
		AssetAdministrationShell aas = (AssetAdministrationShell) o;
		
		assertEquals(aas2Id, aas.getIdShort());
		assertEquals(aas2Id, aas.getIdentification().getId());
		assertEquals(description2.get("en"), aas.getDescription().get("en"));
		assertEquals(aas2Category, aas.getCategory());
	}
	
}
