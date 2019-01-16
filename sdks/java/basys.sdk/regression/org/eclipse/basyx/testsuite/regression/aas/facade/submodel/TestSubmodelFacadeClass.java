package org.eclipse.basyx.testsuite.regression.aas.facade.submodel;


import java.util.Collections;

import org.eclipse.basyx.aas.metamodel.facades.SubmodelFacadeInternalSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.dataspecification.DataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind.Kind;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable.Formula;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Key;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.enums.KeyElements;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.enums.KeyType;
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
	@Test
	public void testSubmodelFactoryInternalSemantics() {
		// Create a submodel facade
		SubmodelFacadeInternalSemantics subModel = new SubmodelFacadeInternalSemantics(
					new Reference(Collections.singletonList(new Key(KeyElements.Asset, false, "", KeyType.IdShort))),
					"valId",
					"valIdShort",
					"valCategory",
					"valDescription",
					new Formula(),
					new DataSpecification(),  
					Kind.Instance,
					"valVersion",
					"valRevision"
				);
		
		// Access values through provider interface
		System.out.println("1:"+subModel.getModelPropertyValue("idShort"));
		System.out.println("2:"+subModel.getIDShort());
		
		System.out.println("3:"+subModel.getModelPropertyValue("kind"));
		System.out.println("4:"+subModel.getKind());

	}
	
}
