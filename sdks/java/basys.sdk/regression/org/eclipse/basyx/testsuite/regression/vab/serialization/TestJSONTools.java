package org.eclipse.basyx.testsuite.regression.vab.serialization;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.basyx.aas.api.annotation.AASOperation;
import org.eclipse.basyx.aas.api.annotation.AASProperty;
import org.eclipse.basyx.aas.backend.http.tools.JSONTools;
import org.eclipse.basyx.aas.impl.provider.JavaObjectVABMapper;
import org.eclipse.basyx.aas.impl.resources.basic.ElementContainer;
import org.eclipse.basyx.aas.metamodel.builder.ElementBuilder;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.VABElementContainer;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel_;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.ComplexDataProperty;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.eclipse.basyx.vab.provider.lambda.VABLambdaProvider;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;

/**
 * 
 * @author pschorn
 *
 */

public class TestJSONTools {

	VABLambdaProvider provider;

	public class TestClass {
		
		@AASProperty
		int[] array = new int[5];
		
		@AASProperty
		int a = 5;

		@AASProperty
		Double b = 5.4;

		@AASProperty
		Float c = 5.2f;

		@AASProperty
		Collection<Integer> d = new LinkedList<Integer>();

		@AASProperty
		Map<String, Integer> e = new HashMap<String, Integer>();

		@AASOperation
		void foo() {
			System.out.println("Foo");
		}

		class NestedPropertyClass extends ComplexDataProperty {

			@AASProperty
			int f1 = 42;

			@AASOperation
			void innerFoo() {
				System.out.println("Inner Foo");
			}
		}

		@AASProperty
		NestedPropertyClass f = new NestedPropertyClass();

		public TestClass() {
			array[0]=4; array[1]=8; array[4]=3;
			d.add(1);
			d.add(2);
			e.put("one", 1);
			e.put("two", 2);

		}

	}

	@Test
	public void testSerializeSubmodel() {

		System.out.println("TestSerializeSubmodel");

		// Create TestClass
		TestClass test = new TestClass();

		// Create VABElementContainer and build
		SubModel_ submodel = (SubModel_) new ElementBuilder(new SubModel_()).set("idShort", "MySubmodel")
				.set("category", "My category").set("id_carrier", "id_carrier test")
				.set("id_submodelDefinition", "id_submodelDefinition test..").build();

		// Map it to VAB Element
		VABElementContainer container = new JavaObjectVABMapper(new MetaModelElementFactory()).map(submodel, test);
		Map<String, Object> root = new HashMap<>();
		root.put("root", container.getAsMap());
		provider = new VABLambdaProvider(root);
		Object map = provider.getModelPropertyValue("root");

		// Serialize Submodel
		JSONObject json = JSONTools.Instance.serialize(map);

		System.out.println("Serialized: " + json.toString(5));
		
		// TODO decompose test cases so it is transparent which component fails
		String message 			= json.toString();
		String expected_message = new JSONObject(TestJSONAcceptors.message).toString();
		
		assertTrue(message.equals(expected_message));
	}
	
	@Test  
	public void testDeserializeSubmodel() {
		
		System.out.println("TestDeserializeSubmodel");
		
		JSONObject serializedValue = new JSONObject(TestJSONAcceptors.message);
		
		Map<String, Object> map = JSONTools.Instance.deserialize(serializedValue);
		
		// Can use hashmap provider to test deserialized map
		VABHashmapProvider provider = new VABHashmapProvider(map);
		
		int a = (int) provider.getModelPropertyValue("entity/properties/a/value/value");
		int f1 = (int) provider.getModelPropertyValue("entity/properties/f/properties/f1/value/value");
		
		assertTrue(a == 5);
		assertTrue(f1 == 42);
	}
	

}
