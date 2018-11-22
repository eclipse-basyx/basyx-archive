package org.eclipse.basyx.testsuite.regression.aas.facade.submodel;


import java.util.Map;

import org.eclipse.basyx.aas.metamodel.facades.SubmodelFacadeInternalSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identification;
import org.junit.Test;



/**
 * Test class that tests the SubModel facade classes
 * 
 * @author kuhn
 *
 */
public class TestSubmodelFacadeClass {

	
	/**
	 * Test method
	 */
	@Test @SuppressWarnings("unchecked")
	public void testSubmodelFactoryInternalSemantics() {
		// Create a submodel facade
		SubmodelFacadeInternalSemantics subModel = new SubmodelFacadeInternalSemantics(
					"internalSemanticsDescription",
					Identification.Internal,
					"valId",
					"valIdShort",
					"valCategory",
					"valDescription",
					"valQualifier",
					"valVersion",
					"valRevision"
				);
		
		// Access values through provider interface
		System.out.println("1:"+subModel.getModelPropertyValue("idShort"));
		System.out.println("2:"+subModel.getIDShort());

		
		System.out.println("3:"+((Map<String, Object>) subModel.getModelPropertyValue("administration")).get("revision"));
		System.out.println("4:"+subModel.getAdministration().getRevision());

		
		System.out.println("5:"+subModel.getModelPropertyValue("kind"));
		System.out.println("6:"+subModel.getKind());

	}
	
}
