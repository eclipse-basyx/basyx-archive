package org.eclipse.basyx.testsuite.regression.aas.aggregator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.aggregator.restapi.AASAggregationProvider;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.junit.Before;
import org.junit.Test;


/**
 * Test for the AASAggregationProvider
 * 
 * @author conradi
 *
 */
public class TestAASAggregatorProvider {
	
	private static final String PREFIX = "aasList/";
	
	private AssetAdministrationShell aas1;
	private String aas1Id = "aas1";
	private LangStrings description1 = new LangStrings("en", "This is test AAS 1");
	private String aas1Category = "TestCategory1";
	
	private AssetAdministrationShell aas2;
	private String aas2Id = "aas2";
	private LangStrings description2 = new LangStrings("en", "This is test AAS 2");
	private String aas2Category = "TestCategory2";
	
	//initializing the dummy test data
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
	
	
	@Test
	@SuppressWarnings("unchecked")
	public void testGetModelPropertyValue() {
		AASAggregationProvider provider = new AASAggregationProvider(new AASAggregatorStub());
		
		try {
			//Has to fail because of missing "/aasList" prefix
			provider.getModelPropertyValue(aas1Id);
			fail();
		} catch (Exception e) {}
		
		try {
			//Has to fail because requested ID does not exist
			provider.getModelPropertyValue(PREFIX + "nonexistentId");
			fail();
		} catch (Exception e) {}
		
		try {
			//Test getting a specific AAS
			checkAAS1(provider.getModelPropertyValue(PREFIX + aas1Id));
		} catch (Exception e) { fail(); }
		
		try {
			//Test getting all AASs as List
			Object o = provider.getModelPropertyValue(PREFIX);
			assertTrue(o instanceof List);
			
			List<IAssetAdministrationShell> list = (List<IAssetAdministrationShell>) o;
			assertEquals(2, list.size());
			
			for(IAssetAdministrationShell aas: list) {
				if(aas.getIdShort().equals(aas1Id)) {
					checkAAS1(aas);
				} else if(aas.getIdShort().equals(aas2Id)) {
					checkAAS2(aas);
				} else {
					fail();
				}
			}
		} catch (Exception e) { fail(); }
	}
	
	@Test
	public void testSetModelPropertyValue() {
		AASAggregatorStub aggregatorStub = new AASAggregatorStub();
		AASAggregationProvider provider = new AASAggregationProvider(aggregatorStub);
		
		try {
			//Has to fail because of missing "/aasList" prefix
			provider.setModelPropertyValue(aas1Id, aas1);
			fail();
		} catch (Exception e) {}
		
		try {
			//Has to fail because aas to be updated does not exist
			provider.setModelPropertyValue(PREFIX + aas2Id, aas2);
			fail();
		} catch (Exception e) {}
		
		try {
			//Has to fail because aas and provided ID do not match
			provider.setModelPropertyValue(PREFIX + aas1Id, aas2);
			fail();
		} catch (Exception e) {}
		
		try {
			//Test update an AAS
			provider.setModelPropertyValue(PREFIX + aas1Id, aas1);
			
			assertNotNull(aggregatorStub.updateAAS);
			checkAAS1(aggregatorStub.updateAAS);
		} catch (Exception e) { fail(); }
	}
	
	@Test
	public void testCreateValue() {
		AASAggregatorStub aggregatorStub = new AASAggregatorStub();
		AASAggregationProvider provider = new AASAggregationProvider(aggregatorStub);
		
		try {
			//Has to fail because of missing "/aasList" prefix
			provider.createValue("", aas1);
			fail();
		} catch (Exception e) {}
		
		try {
			//Has to fail because of unsupported path
			provider.createValue(PREFIX + aas1Id, aas1);
			fail();
		} catch (Exception e) {}
		
		try {
			//Has to fail because aas already exists
			provider.createValue(PREFIX, aas1);
			fail();
		} catch (Exception e) {}
		
		try {
			//Test creating an AAS
			provider.createValue(PREFIX, aas2);
			checkAAS2(aggregatorStub.createAAS);
		} catch (Exception e) { fail(); }
	}
	
	@Test
	public void testDeleteValue() {
		AASAggregatorStub aggregatorStub = new AASAggregatorStub();
		AASAggregationProvider provider = new AASAggregationProvider(aggregatorStub);
		
		try {
			//Has to fail because of missing "/aasList" prefix
			provider.deleteValue("");
			fail();
		} catch (Exception e) {}
		
		try {
			//Has to fail, because aas to be deleted does not exist
			provider.deleteValue(PREFIX + aas2Id);
			
			fail();
		} catch (Exception e) {}
		
		try {
			//Test deleting an AAS
			provider.deleteValue(PREFIX + aas1Id);
			
			assertNotNull(aggregatorStub.deleteId);
			assertEquals(aas1Id, aggregatorStub.deleteId.getId());
		} catch (Exception e) { fail(); }
	}
	
	
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
	
	
	
	//Stub class of IAASAggregator. Used for testing the AASAggregatorProvider
	class AASAggregatorStub implements IAASAggregator {
		AssetAdministrationShell createAAS = null;
		
		AssetAdministrationShell updateAAS = null;
		
		IIdentifier deleteId = null;
		
		public List<IAssetAdministrationShell> getAASList() {
			return Arrays.asList(aas1, aas2);
		}
		
		public IAssetAdministrationShell getAAS(IIdentifier aasId) {
			if(aasId.getId().equals(aas1Id)) return aas1;
			return null;
		}
		
		public void createAAS(AssetAdministrationShell aas) {
			// store given aas locally for later verification
			createAAS = aas;
		}

		public void updateAAS(AssetAdministrationShell aas) {
			// store given aas locally for later verification
			updateAAS = aas;
		}

		public void deleteAAS(IIdentifier aasId) {
			// store given identifier locally for later verification
			deleteId = aasId;
		}
	}
	
}
