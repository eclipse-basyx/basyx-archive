package org.eclipse.basyx.testsuite.regression.aas.facade.submodel;


import org.eclipse.basyx.aas.metamodel.facades.SubmodelFacadeCustomSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind.Kind;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable.Qualifier;
import org.junit.Test;



/**
 * Test class that tests the SubModel facade classes
 * FIXME: Not a real test case
 * @author kuhn
 *
 */
public class TestSubmodelFacadeClass {

	
	/**
	 * Test method
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSubmodelFactoryInternalSemantics() throws Exception {
		// Create a submodel facade
		SubmodelFacadeCustomSemantics subModel = new SubmodelFacadeCustomSemantics(
				"", "valIdType",
					"valId",
					"valIdShort",
					"valCategory",
					"valDescription",
				new Qualifier(), 
				new HasDataSpecification(), 
					Kind.Instance,
					"valVersion",
					"valRevision"
				);
		
		// Access values through provider interface
		System.out.println("2:"+subModel.getIDShort());
		
		System.out.println("4:"+subModel.getKind());

	}
	
}
