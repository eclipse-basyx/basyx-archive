package org.eclipse.basyx.testsuite.regression.aas.backend.http.serialization.json;

import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.Property;
import org.eclipse.basyx.aas.impl.resources.basic.SubModel;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;



/**
 * Test case for JSON serialization of IElements
 * 
 * @author kuhn
 *
 */
public class SerializeIElement {


	/**
	 * Run test case
	 */
	@Test
	void test() {
		
		// Create IElements
		Property prop1 = new Property();
			prop1.setId("prop1");
			prop1.setName("prop1");
		SubModel subMod1 = new SubModel();
			subMod1.setId("submod1");
			subMod1.setName("submod1");
			subMod1.addProperty(prop1);
		AssetAdministrationShell aas1 = new AssetAdministrationShell(); 
			aas1.setId("aas1");
			aas1.addSubModel(subMod1);
		
		// Serialize references
		JSONObject serRef1 = JSONTools.Instance.serialize(prop1); // "int1", 
		JSONObject serRef2 = JSONTools.Instance.serialize(subMod1); // "int2", 
		JSONObject serRef3 = JSONTools.Instance.serialize(aas1); // "pri1", 

		System.out.println("SR1:"+serRef1);
		System.out.println("SR2:"+serRef2);
		
		
		// Deserialize references
		IElementReference ref1 = (IElementReference) JSONTools.Instance.deserialize(serRef1);
		//IElementReference ref2 = (IElementReference) JSONTools.Instance.deserialize(serRef2);
		//IElementReference ref3 = (IElementReference) JSONTools.Instance.deserialize(serRef3);
		
		// Check results
		
		System.out.println("YY1:"+serRef1);
		System.out.println("YY1:"+ref1.getId());
		System.out.println("YY1:"+ref1.getAASID());
		
		System.out.println("YY2:"+serRef2);
		//System.out.println("YY2:"+ref2.getId());
		//System.out.println("YY2:"+ref2.getAASID());
		
		System.out.println("YY3:"+serRef3);
		//System.out.println("YY3:"+ref3.getId());
		//System.out.println("YY3:"+ref3.getAASID());
	}
}


