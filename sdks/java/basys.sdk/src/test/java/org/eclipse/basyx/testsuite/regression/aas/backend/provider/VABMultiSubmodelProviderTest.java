/**
 * 
 */
package org.eclipse.basyx.testsuite.regression.aas.backend.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.testsuite.support.aas.vab.stub.elements.SimpleAASSubmodel;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
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
		VABMultiSubmodelProvider provider = new VABMultiSubmodelProvider();
		// set dummy aas
		provider.setAssetAdministrationShell(new VirtualPathModelProvider(new HashMap<String, Object>()));
		provider.addSubmodel("SimpleAASSubmodel", new VirtualPathModelProvider(new SimpleAASSubmodel()));
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
			proxy.invoke("/aas/submodels/SimpleAASSubmodel/operations/exception1/invokable");
			fail();
		} catch (ServerException e) {
		}
		// Invoke operationEx2
		try {
			proxy.invoke("/aas/submodels/SimpleAASSubmodel/operations/exception2/invokable", "prop1");
			fail();
		} catch (ServerException e) {

		}
	}

	@Test
	public void invokeTest() {
		// Invoke operation
		assertEquals(7, proxy.invoke("/aas/submodels/SimpleAASSubmodel/operations/complex/invokable", 10, 3));
		assertEquals(true, proxy.invoke("/aas/submodels/SimpleAASSubmodel/operations/simple/invokable"));
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
			proxy.readElementValue("/aas/submodels/TestSM");
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

		Object value3 = proxy.readElementValue("/aas/submodels/" + smId + "/dataElements/integerProperty");
		System.out.println("V3:" + value3);

		Object value4 = proxy.readElementValue("/aas/submodels/" + smId + "/dataElements/integerProperty/value");
		assertEquals(value4, 123);
	}
}
