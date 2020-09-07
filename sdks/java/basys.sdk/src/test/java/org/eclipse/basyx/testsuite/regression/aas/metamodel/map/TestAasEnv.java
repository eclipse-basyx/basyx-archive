package org.eclipse.basyx.testsuite.regression.aas.metamodel.map;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.AasEnv;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.parts.ConceptDescription;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.junit.Test;

public class TestAasEnv {

	
	@Test
	public void testAssetsGetSet() {
		AasEnv env = new AasEnv();
		Asset asset = new Asset();
		asset.setIdShort("TestAasEnv");
		env.setAssets(Arrays.asList(asset));
		assertEquals(asset, env.getAssets().toArray()[0]);
	}

	@Test
	public void testAssetAdministrationShellGetSet() {
		AasEnv env = new AasEnv();
		AssetAdministrationShell ass = new AssetAdministrationShell();
		ass.setIdShort("TestAasEnv");
		env.setAssetAdministrationShells(Arrays.asList(ass));
		assertEquals(ass, env.getAssetAdministrationShells().toArray()[0]);
	}
	
	@Test
	public void testConceptDescriptionsGetSet() {
		AasEnv env = new AasEnv();
		ConceptDescription conceptDescriptions = new ConceptDescription();
		conceptDescriptions.setIdShort("TestAasEnv");
		env.setConceptDescriptions(Arrays.asList(conceptDescriptions));
		assertEquals(conceptDescriptions, env.getConceptDescriptions().toArray()[0]);
	}
	
	@Test
	public void testSubmodelsGetSet() {
		AasEnv env = new AasEnv();
		SubModel submodels = new SubModel();
		submodels.setIdShort("TestAasEnv");
		env.setSubmodels(Arrays.asList(submodels));
		assertEquals(submodels, env.getSubmodels().toArray()[0]);
	}
	
	@Test
	public void testCreateAsFacade() {
		Map<String, Object> asset = new HashMap<>();
		asset.put(ModelType.MODELTYPE, Asset.MODELTYPE);
		asset.put(Referable.IDSHORT, "TestAsset");
		
		Map<String, Object> assetAdministrationShell = new HashMap<>();
		assetAdministrationShell.put(ModelType.MODELTYPE, AssetAdministrationShell.MODELTYPE);
		assetAdministrationShell.put(Referable.IDSHORT, "TestAssetAdministrationShell");

		Map<String, Object> submodel = new HashMap<>();
		submodel.put(ModelType.MODELTYPE, SubModel.MODELTYPE);
		submodel.put(Referable.IDSHORT, "TestSubmodel");
		submodel.put(SubModel.SUBMODELELEMENT, new ArrayList<Object>());
		
		Map<String, Object> conceptDescription = new HashMap<>();
		submodel.put(ModelType.MODELTYPE, ConceptDescription.MODELTYPE);
		submodel.put(Referable.IDSHORT, "TestConceptDescription");
		
		
		Map<String, Object> aasEnvAsMap = new HashMap<>();
		aasEnvAsMap.put(AasEnv.ASSETS, Arrays.asList(asset));
		aasEnvAsMap.put(AasEnv.ASSETADMINISTRATIONSHELLS, Arrays.asList(assetAdministrationShell));
		aasEnvAsMap.put(AasEnv.SUBMODELS, Arrays.asList(submodel));
		aasEnvAsMap.put(AasEnv.CONCEPTDESCRIPTIONS, Arrays.asList(conceptDescription));
		
		AasEnv aasEnv = AasEnv.createAsFacade(aasEnvAsMap);
		
		Asset assetObj = (Asset)aasEnv.getAssets().toArray()[0];
		assertEquals(assetObj.getIdShort(), asset.get(Referable.IDSHORT));
		AssetAdministrationShell assetAdministrationShellObj = (AssetAdministrationShell)aasEnv.getAssetAdministrationShells().toArray()[0];
		assertEquals(assetAdministrationShellObj.getIdShort(), assetAdministrationShell.get(Referable.IDSHORT));
		SubModel submodelObj = (SubModel)aasEnv.getSubmodels().toArray()[0];
		assertEquals(submodelObj.getIdShort(), submodel.get(Referable.IDSHORT));
		ConceptDescription conceptDescriptionObj = (ConceptDescription)aasEnv.getConceptDescriptions().toArray()[0];
		assertEquals(conceptDescriptionObj.getIdShort(), conceptDescription.get(Referable.IDSHORT));
	}
}
