package org.eclipse.basyx.testsuite.regression.aas.backend.http.serialization.json;

import org.eclipse.basyx.aas.api.annotation.AASProperty;
import org.eclipse.basyx.aas.api.serializableobject.SerializableObject;
import org.eclipse.basyx.aas.backend._ConnectedSerializableObject;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;



/**
 * Test case for JSON serialization of IElements
 * 
 * @author kuhn
 *
 */
public class SerializeSerializableObject {

	
	/**
	 * Base element that can be serialized
	 * 
	 * @author kuhn
	 *
	 */
	class SerializedObject1 extends SerializableObject {
		
		
		/**
		 * Simple property
		 */
		@AASProperty int simpleProperty1;
		
		
		/**
		 * Another simple property
		 */
		@AASProperty float simpleProperty2;
		
		
		/**
		 * Reference to complex property
		 */
		@AASProperty SerializedObjectMember1 complexproperty1;


		/**
		 * Reference to another complex property
		 */
		@AASProperty SerializedObjectMember1 complexproperty2;
	}
	
	
	
	
	/**
	 * Another property that can be serialized
	 * 
	 * @author kuhn
	 *
	 */
	class SerializedObjectMember1 extends SerializableObject {

		
		/**
		 * Simple property
		 */
		@AASProperty String nameProperty = "";
	}
	
	
	
	
	/**
	 * Run test case
	 */
	@Test
	void test() {
		
		// Create serializable objects
		SerializedObjectMember1 serMemberObject1 = new SerializedObjectMember1();
			serMemberObject1.nameProperty = "TestProperty";
		SerializedObject1 serTestObject = new SerializedObject1();
			serTestObject.simpleProperty1 = 19;
			serTestObject.simpleProperty2 = 23.5f;
			serTestObject.complexproperty1 = serMemberObject1;
			serTestObject.complexproperty2 = null;

	
		// Serialize references
		JSONObject serObj1 = JSONTools.Instance.serialize(serTestObject);  
		JSONObject serObj2 = JSONTools.Instance.serialize(serMemberObject1);  

		
		// Output serialized references
		System.out.println("SR1:"+serObj1);
		System.out.println("SR2:"+serObj2);


		// Deserialize object
		_ConnectedSerializableObject connSerObj1 = (_ConnectedSerializableObject) JSONTools.Instance.deserialize(serObj1);
		_ConnectedSerializableObject connSerObj2 = (_ConnectedSerializableObject) JSONTools.Instance.deserialize(serObj2);
		

		System.out.println("XX1::"+connSerObj1.getElements().get("simpleProperty1"));
		System.out.println("XX2::"+connSerObj1.getElements().get("simpleProperty2"));
		System.out.println("XX3::"+((_ConnectedSerializableObject) connSerObj1.getElements().get("complexproperty1")).getElements().get("nameProperty"));
		System.out.println("XX4::"+connSerObj2.getElements().get("nameProperty"));
	}
}


