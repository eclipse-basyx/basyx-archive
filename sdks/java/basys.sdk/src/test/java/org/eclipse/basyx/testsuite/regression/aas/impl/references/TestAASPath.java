package org.eclipse.basyx.testsuite.regression.aas.impl.references;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.impl.tools.BaSysID;
import org.junit.Test;



/**
 * Test case that ensures that everybody is using the correct path format as defined by class BaSysID
 * 
 * TODO adapt test cases to new identifier pattern
 * 
 * @author kuhn
 *
 */
public class TestAASPath {


	/**
	 * Run test case
	 */
	@Test
	public void test() {
		// First check path format
		String qualAASID1 = BaSysID.instance.buildAASID("scopepart.scopepart.topscope/Stub1AAS");
		String aasID1     = BaSysID.instance.buildAASID("Stub1AAS");
		String aasID2     = BaSysID.instance.buildAASID("Stub1AAS/aas");
		String smID       = BaSysID.instance.buildSMID("statusSM");
		String smID2      = BaSysID.instance.buildSMID("statusSM/submodel");
		String qualSMID   = BaSysID.instance.buildPath("Stub1AAS", "statusSM");
		String qualSMID2  = BaSysID.instance.buildPath("topscope/Stub1AAS", "statusSM");
		String qualSMIDa  = BaSysID.instance.buildPath("Stub1AAS", "");
		String qualSMIDb  = BaSysID.instance.buildPath("topscope/Stub1AAS", "");
		String qualSMIDc  = BaSysID.instance.buildPath("Stub1AAS/aas", "");
		String qualSMIDd  = BaSysID.instance.buildPath("topscope/Stub1AAS/aas", "");
		String qualProp   = BaSysID.instance.buildPath("topscope/Stub1AAS", "statusSM", "property3.propertyA" ,"properties");
		String qualPropa  = BaSysID.instance.buildPath("topscope/Stub1AAS/aas", "statusSM", "property3.propertyA" ,"properties");
		String qualProp1  = BaSysID.instance.buildPath("Stub1AAS", "statusSM", "property1" ,"properties");
		String qualProp1a = BaSysID.instance.buildPath("Stub1AAS/aas", "statusSM", "property1" ,"properties");
		String qualProp2  = BaSysID.instance.buildPath("Stub1AAS/aas", "statusSM", "property3.propertyA" ,"properties");
		String qualProp3  = BaSysID.instance.buildPath(null, "statusSM", "property1", "properties");
		String qualProp4  = BaSysID.instance.buildPath(null, "statusSM", "property3.propertyA", "properties");
		// - Check path format
		assertTrue(qualAASID1.equals("scopepart.scopepart.topscope/Stub1AAS/aas"));
		assertTrue(aasID1.equals("Stub1AAS/aas"));
		assertTrue(aasID2.equals("Stub1AAS/aas"));
		assertTrue(smID.equals("statusSM/submodel"));
		assertTrue(smID2.equals("statusSM/submodel"));
		assertTrue(qualSMID.equals("Stub1AAS/aas/submodels/statusSM"));
		assertTrue(qualSMID2.equals("topscope/Stub1AAS/aas/submodels/statusSM"));
		assertTrue(qualSMIDa.equals("Stub1AAS/aas"));
		assertTrue(qualSMIDb.equals("topscope/Stub1AAS/aas"));
		assertTrue(qualSMIDc.equals("Stub1AAS/aas"));
		assertTrue(qualSMIDd.equals("topscope/Stub1AAS/aas"));
		assertTrue(qualProp.equals("topscope/Stub1AAS/aas/submodels/statusSM/properties/property3.propertyA"));
		assertTrue(qualPropa.equals("topscope/Stub1AAS/aas/submodels/statusSM/properties/property3.propertyA"));
		assertTrue(qualProp1.equals("Stub1AAS/aas/submodels/statusSM/properties/property1"));
		assertTrue(qualProp1a.equals("Stub1AAS/aas/submodels/statusSM/properties/property1"));
		assertTrue(qualProp2.equals("Stub1AAS/aas/submodels/statusSM/properties/property3.propertyA"));
		assertTrue(qualProp3.equals("statusSM/submodel/properties/property1"));
		assertTrue(qualProp4.equals("statusSM/submodel/properties/property3.propertyA"));
		
		
		// Check path components
		assertTrue(BaSysID.instance.getAASID(qualAASID1).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getScopedServicePath(qualAASID1, "Stub1AAS/aas").equals("Stub1AAS/aas"));
		assertTrue(BaSysID.instance.getSubmodelID(qualAASID1).equals(""));
		assertTrue(BaSysID.instance.getPath(qualAASID1).equals(""));
		
		assertTrue(BaSysID.instance.getAASID(aasID1).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getScopedServicePath(aasID1, "Stub1AAS/aas").equals("Stub1AAS/aas"));
		assertTrue(BaSysID.instance.getSubmodelID(aasID1).equals(""));
		assertTrue(BaSysID.instance.getPath(aasID1).equals(""));
		
		assertTrue(BaSysID.instance.getAASID(smID).equals(""));
		assertTrue(BaSysID.instance.getScopedServicePath(smID, "statusSM/submodel").equals("statusSM/submodel"));
		assertTrue(BaSysID.instance.getSubmodelID(smID).equals("statusSM"));
		assertTrue(BaSysID.instance.getPath(smID).equals(""));

		assertTrue(BaSysID.instance.getAASID(qualSMID).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getScopedServicePath(qualSMID, "Stub1AAS/aas/submodels/statusSM").equals("Stub1AAS/aas/submodels/statusSM"));
		assertTrue(BaSysID.instance.getSubmodelID(qualSMID).equals("statusSM"));
		assertTrue(BaSysID.instance.getPath(qualSMID).equals(""));

		assertTrue(BaSysID.instance.getAASID(qualSMID2).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getScopedServicePath(qualSMID2, "topscope/Stub1AAS/aas/submodels/statusSM").equals("topscope/Stub1AAS/aas/submodels/statusSM"));
		assertTrue(BaSysID.instance.getSubmodelID(qualSMID2).equals("statusSM"));
		assertTrue(BaSysID.instance.getPath(qualSMID2).equals(""));

		assertTrue(BaSysID.instance.getAASID(qualProp).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getScopedServicePath(qualProp, "Stub1AAS/aas/submodels/statusSM").equals("Stub1AAS/aas/submodels/statusSM/properties/property3.propertyA"));
		assertTrue(BaSysID.instance.getSubmodelID(qualProp).equals("statusSM"));
		assertTrue(BaSysID.instance.getPath(qualProp).equals("property3.propertyA"));

		assertTrue(BaSysID.instance.getAASID(qualProp1).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getSubmodelID(qualProp1).equals("statusSM"));
		assertTrue(BaSysID.instance.getPath(qualProp1).equals("property1"));

		assertTrue(BaSysID.instance.getAASID(qualProp2).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getSubmodelID(qualProp2).equals("statusSM"));
		assertTrue(BaSysID.instance.getPath(qualProp2).equals("property3.propertyA"));
		
		assertTrue(BaSysID.instance.getAASID(qualProp3).equals(""));
		assertTrue(BaSysID.instance.getSubmodelID(qualProp3).equals("statusSM"));
		assertTrue(BaSysID.instance.getPath(qualProp3).equals("property1"));

		assertTrue(BaSysID.instance.getAASID(qualProp4).equals(""));
		assertTrue(BaSysID.instance.getSubmodelID(qualProp4).equals("statusSM"));
		assertTrue(BaSysID.instance.getPath(qualProp4).equals("property3.propertyA"));
	}
}


