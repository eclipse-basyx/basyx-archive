package org.eclipse.basyx.testsuite.regression.aas.impl.references;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.impl.tools.BaSysID;
import org.junit.jupiter.api.Test;



/**
 * Test case that ensures that everybody is using the correct path format as defined by class BaSysID
 * 
 * @author kuhn
 *
 */
public class TestAASPath {


	/**
	 * Run test case
	 */
	@Test
	void test() {
		// First check path format
		String qualAASID1 = BaSysID.instance.buildAASID("Stub1AAS.scopepart.scopepart.topscope");
		String qualAASID2 = BaSysID.instance.buildAASID("aas.Stub1AAS.scopepart.scopepart.topscope");
		String aasID1     = BaSysID.instance.buildAASID("Stub1AAS");
		String aasID2     = BaSysID.instance.buildAASID("aas.Stub1AAS");
		String smID       = BaSysID.instance.buildSMID("statusSM");
		String qualSMID   = BaSysID.instance.buildPath("Stub1AAS", "statusSM");
		String qualSMID2  = BaSysID.instance.buildPath("Stub1AAS.topscope", "statusSM");
		String qualSMIDa  = BaSysID.instance.buildPath("Stub1AAS", "");
		String qualSMIDb  = BaSysID.instance.buildPath("Stub1AAS.topscope", "");
		String qualSMIDc  = BaSysID.instance.buildPath("aas.Stub1AAS", "");
		String qualSMIDd  = BaSysID.instance.buildPath("aas.Stub1AAS.topscope", "");
		String qualProp   = BaSysID.instance.buildPath(new String[] {"property3", "propertyA"}, "Stub1AAS.topscope", "statusSM");
		String qualPropa  = BaSysID.instance.buildPath(new String[] {"property3", "propertyA"}, "aas.Stub1AAS.topscope", "statusSM");
		String qualProp1  = BaSysID.instance.buildPath(new String[] {"property1"}, "Stub1AAS", "statusSM");
		String qualProp1a = BaSysID.instance.buildPath(new String[] {"property1"}, "aas.Stub1AAS", "statusSM");
		String qualProp2  = BaSysID.instance.buildPath(new String[] {"property3", "propertyA"}, "Stub1AAS", "statusSM");
		String qualProp3  = BaSysID.instance.buildPath(new String[] {"property1"}, "statusSM");
		String qualProp4  = BaSysID.instance.buildPath(new String[] {"property3", "propertyA"}, "statusSM");
		// - Check path format
		assertTrue(qualAASID1.equals("aas.Stub1AAS.scopepart.scopepart.topscope"));
		assertTrue(qualAASID2.equals("aas.Stub1AAS.scopepart.scopepart.topscope"));
		assertTrue(aasID1.equals("aas.Stub1AAS"));
		assertTrue(aasID2.equals("aas.Stub1AAS"));
		assertTrue(smID.equals("statusSM"));
		assertTrue(qualSMID.equals("statusSM.Stub1AAS"));
		assertTrue(qualSMID2.equals("statusSM.Stub1AAS.topscope"));
		assertTrue(qualSMIDa.equals("aas.Stub1AAS"));
		assertTrue(qualSMIDb.equals( "aas.Stub1AAS.topscope"));
		assertTrue(qualSMIDc.equals("aas.Stub1AAS"));
		assertTrue(qualSMIDd.equals("aas.Stub1AAS.topscope"));
		assertTrue(qualProp.equals("statusSM.Stub1AAS.topscope/property3/propertyA"));
		assertTrue(qualPropa.equals("statusSM.Stub1AAS.topscope/property3/propertyA"));
		assertTrue(qualProp1.equals("statusSM.Stub1AAS/property1"));
		assertTrue(qualProp1a.equals("statusSM.Stub1AAS/property1"));
		assertTrue(qualProp2.equals("statusSM.Stub1AAS/property3/propertyA"));
		assertTrue(qualProp3.equals("statusSM/property1"));
		assertTrue(qualProp4.equals("statusSM/property3/propertyA"));
		
		
		// Check path components
		assertTrue(BaSysID.instance.getAASID(qualAASID1).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getQualifiedAASID(qualAASID1).equals("Stub1AAS.scopepart.scopepart.topscope"));
		assertTrue(BaSysID.instance.getSubmodelID(qualAASID1).equals(""));
		assertTrue(BaSysID.instance.getPath(qualAASID1).equals(""));
		
		assertTrue(BaSysID.instance.getAASID(aasID1).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getQualifiedAASID(aasID1).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getSubmodelID(aasID1).equals(""));
		assertTrue(BaSysID.instance.getPath(aasID1).equals(""));
		
		assertTrue(BaSysID.instance.getAASID(smID).equals(""));
		assertTrue(BaSysID.instance.getQualifiedAASID(smID).equals(""));
		assertTrue(BaSysID.instance.getSubmodelID(smID).equals("statusSM"));
		assertTrue(BaSysID.instance.getPath(smID).equals(""));

		assertTrue(BaSysID.instance.getAASID(qualSMID).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getQualifiedAASID(qualSMID).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getSubmodelID(qualSMID).equals("statusSM"));
		assertTrue(BaSysID.instance.getPath(qualSMID).equals(""));

		assertTrue(BaSysID.instance.getAASID(qualSMID2).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getQualifiedAASID(qualSMID2).equals("Stub1AAS.topscope"));
		assertTrue(BaSysID.instance.getSubmodelID(qualSMID2).equals("statusSM"));
		assertTrue(BaSysID.instance.getPath(qualSMID2).equals(""));

		assertTrue(BaSysID.instance.getAASID(qualProp).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getQualifiedAASID(qualProp).equals("Stub1AAS.topscope"));
		assertTrue(BaSysID.instance.getSubmodelID(qualProp).equals("statusSM"));
		assertTrue(BaSysID.instance.getPath(qualProp).equals("property3/propertyA"));

		assertTrue(BaSysID.instance.getAASID(qualProp1).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getQualifiedAASID(qualProp1).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getSubmodelID(qualProp1).equals("statusSM"));
		assertTrue(BaSysID.instance.getPath(qualProp1).equals("property1"));

		assertTrue(BaSysID.instance.getAASID(qualProp2).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getQualifiedAASID(qualProp2).equals("Stub1AAS"));
		assertTrue(BaSysID.instance.getSubmodelID(qualProp2).equals("statusSM"));
		assertTrue(BaSysID.instance.getPath(qualProp2).equals("property3/propertyA"));
		
		assertTrue(BaSysID.instance.getAASID(qualProp3).equals(""));
		assertTrue(BaSysID.instance.getQualifiedAASID(qualProp3).equals(""));
		assertTrue(BaSysID.instance.getSubmodelID(qualProp3).equals("statusSM"));
		assertTrue(BaSysID.instance.getPath(qualProp3).equals("property1"));

		assertTrue(BaSysID.instance.getAASID(qualProp4).equals(""));
		assertTrue(BaSysID.instance.getQualifiedAASID(qualProp4).equals(""));
		assertTrue(BaSysID.instance.getSubmodelID(qualProp4).equals("statusSM"));
		assertTrue(BaSysID.instance.getPath(qualProp4).equals("property3/propertyA"));
	}
}


