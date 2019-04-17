/**
 * 
 */
package org.eclipse.basyx.testsuite.regression.aas.backend.provider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.backend.provider.VABMultiAASProvider;
import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.testsuite.support.aas.vab.stub.elements.SimpleAASSubmodel;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the capability to multiplex of a VABMultiAASProvider
 * 
 * @author espen
 *
 */
public class VABMultiAASProviderTest {
	VABElementProxy proxy;
	VABMultiAASProvider provider;

	@Before
	public void build() {
		VABConnectionManagerStub stub = new VABConnectionManagerStub();
		String urn = "urn:fhg:es.iese:aas:1:1:submodel";
		VABMultiSubmodelProvider<VABHashmapProvider> aasProvider = new VABMultiSubmodelProvider<>();
		aasProvider.addSubmodel("SimpleAASSubmodel", new VABHashmapProvider(new SimpleAASSubmodel()));
		provider = new VABMultiAASProvider();
		provider.setAssetAdministrationShell("a1", aasProvider);
		stub.addProvider(urn, "", provider);
		proxy = stub.connectToVABElement(urn);
	}

	@Test
	public void clearTest() {
		provider.clear();
		Object result = proxy.readElementValue("path://a1/aas/submodels/SimpleAASSubmodel/");
		assertNull(result);
	}

	@Test
	public void getTest() {
		// test reading from a valid aas
		Object result = proxy.readElementValue("path://a1/aas/submodels/SimpleAASSubmodel/properties/prop1/value");
		assertEquals(123, result);

		// test reading from an invalid aas
		assertNull(proxy.readElementValue("path://A1/aas/submodels/SimpleAASSubmodel/"));
	}

	@Test
	public void setTest() {
		// test setting in a valid aas
		proxy.updateElementValue("path://a1/aas/submodels/SimpleAASSubmodel/properties/prop1/value", 100);

		// test setting in an invalid aas
		proxy.updateElementValue("path://A1/aas/submodels/SimpleAASSubmodel/properties/prop1/value", 200);

		// retrieving property
		Object result = proxy.readElementValue("path://a1/aas/submodels/SimpleAASSubmodel/properties/prop1/value");
		assertEquals(100, result);
	}

	@Test
	public void removeTest() {
		// test deleting from an invalid aas
		proxy.deleteElement("path://A1/aas/submodels/SimpleAASSubmodel/properties/prop1/value");
		Object result = proxy.readElementValue("path://a1/aas/submodels/SimpleAASSubmodel/properties/prop1/value");
		assertEquals(123, result);

		// test deleting from a valid aas
		proxy.deleteElement("path://a1/aas/submodels/SimpleAASSubmodel/properties/prop1/value");
		result = proxy.readElementValue("path://a1/aas/submodels/SimpleAASSubmodel/properties/prop1/value");
		assertNull(result);
	}

	@Test
	public void invokeExceptionTest() {
		// Invoke operationEx1
		try {
			proxy.invoke("path://a1/aas/submodels/SimpleAASSubmodel/operations/operationEx1/invokable");
			fail();
		} catch (ServerException e) {
		}
		// Invoke operationEx2
		try {
			proxy.invoke("path://a1/aas/submodels/SimpleAASSubmodel/operations/operationEx2/invokable", "prop1");
			fail();
		} catch (ServerException e) {
		}
	}

	@Test
	public void invokeTest() {
		// test invoking from an invalid aas
		assertNull(proxy.invoke("path://A1/aas/submodels/SimpleAASSubmodel/operations/operation1/invokable", 10, 5));

		// test invoking with return value
		assertEquals(5, proxy.invoke("path://a1/aas/submodels/SimpleAASSubmodel/operations/operation1/invokable", 10, 5));
		assertEquals(15, proxy.invoke("path://a1/aas/submodels/SimpleAASSubmodel/operations/operation2/invokable", 10, 5));
	}
}
