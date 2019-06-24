/**
 * 
 */
package org.eclipse.basyx.testsuite.regression.aas.backend.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.testsuite.support.aas.vab.stub.elements.SimpleAASSubmodel;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the capability to multiplex of a VABMultiSubmodelProvider
 * 
 * @author schnicke, kuhn
 *
 */
public class VABMultiSubmodelProviderTest {
	VABElementProxy proxy;

	@Before
	public void build() {
		VABConnectionManagerStub stub = new VABConnectionManagerStub();
		String urn = "urn:fhg:es.iese:aas:1:1:submodel";
		VABMultiSubmodelProvider<VABHashmapProvider> provider = new VABMultiSubmodelProvider<>();
		provider.setAssetAdministrationShell(new VABHashmapProvider(new HashMap<String, Object>())); // set dummy aas
		provider.addSubmodel("SimpleAASSubmodel", new VABHashmapProvider(new SimpleAASSubmodel()));
		stub.addProvider(urn, "", provider);
		proxy = stub.connectToVABElement(urn);
	}

	/**
	 * Run JUnit test case
	 */
	@Test
	public void invokeExceptionTest() {
		// Invoke operationEx1
		try {
			proxy.invoke("/aas/submodels/SimpleAASSubmodel/operations/operationEx1/invokable");
			fail();
		} catch (ServerException e) {
		}
		// Invoke operationEx2
		try {
			proxy.invoke("/aas/submodels/SimpleAASSubmodel/operations/operationEx2/invokable", "prop1");
			fail();
		} catch (ServerException e) {

		}
	}

	@Test
	public void invokeTest() {
		// Invoke operation
		assertEquals(5, proxy.invoke("/aas/submodels/SimpleAASSubmodel/operations/operation1/invokable", 10, 5));
		assertEquals(15, proxy.invoke("/aas/submodels/SimpleAASSubmodel/operations/operation2/invokable", 10, 5));
	}

	@Test
	public void getTest() {
		getTestRunner("SimpleAASSubmodel");
	}
	
	@Test
	public void createDeleteTest() {
		
		proxy.createElement("/aas/submodels", new SimpleAASSubmodel("TestSM"));
		
		getTestRunner("TestSM");
		
		proxy.deleteElement("/aas/submodels/TestSM");
		
		
		try {
			Object o = proxy.readElementValue("/aas/submodels/TestSM");
			fail();
		} catch (ServerException e) {
			System.out.println("VABMultiSubmodelProvider CreateDelete passed");
		}
	}
	
	void getTestRunner(String smId) {
		// Get property value
		// Object value1 =
		// connVABElement.readElementValue("/aas/submodels/SimpleAASSubmodel");
		Object value2 = proxy.readElementValue("/aas/submodels/" + smId + "/dataElements");
		System.out.println("V2:" + value2);

		Object value3 = proxy.readElementValue("/aas/submodels/" + smId + "/dataElements/prop1");
		System.out.println("V3:" + value3);

		Object value4 = proxy.readElementValue("/aas/submodels/" + smId + "/dataElements/prop1/value");
		assertEquals(value4, 123);
	}
}
