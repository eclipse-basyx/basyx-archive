/**
 * 
 */
package org.eclipse.basyx.testsuite.regression.aas.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Map;

import org.eclipse.basyx.aas.restapi.VABMultiAASProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.testsuite.regression.submodel.restapi.SimpleAASSubmodel;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.vab.exception.ServerException;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
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
		VABMultiSubmodelProvider aasProvider = new VABMultiSubmodelProvider();
		aasProvider.addSubmodel("SimpleAASSubmodel", new SubModelProvider(new SimpleAASSubmodel()));
		provider = new VABMultiAASProvider();
		provider.setAssetAdministrationShell("a1", aasProvider);
		stub.addProvider(urn, "", provider);
		proxy = stub.connectToVABElement(urn);
	}

	@Test
	public void clearTest() {
		provider.clear();
		Object result = proxy.getModelPropertyValue("path://a1/aas/submodels/SimpleAASSubmodel/");
		assertNull(result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getTest() {
		// test reading from a valid aas
		Map<String, Object> result = (Map<String, Object>) proxy
				.getModelPropertyValue("path://a1/aas/submodels/SimpleAASSubmodel/dataElements/integerProperty/value");
		assertEquals(123, result.get(SingleProperty.VALUE));

		// test reading from an invalid aas
		assertNull(proxy.getModelPropertyValue("path://A1/aas/submodels/SimpleAASSubmodel/"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void setTest() {
		// test setting in a valid aas
		proxy.setModelPropertyValue("path://a1/aas/submodels/SimpleAASSubmodel/dataElements/integerProperty/value", 100);

		// test setting in an invalid aas
		proxy.setModelPropertyValue("path://A1/aas/submodels/SimpleAASSubmodel/dataElements/integerProperty/value", 200);

		// retrieving property
		Map<String, Object> result = (Map<String, Object>) proxy
				.getModelPropertyValue("path://a1/aas/submodels/SimpleAASSubmodel/dataElements/integerProperty/value");
		assertEquals(100, result.get(SingleProperty.VALUE));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void removeTest() {
		// test deleting from an invalid aas
		proxy.deleteValue("path://A1/aas/submodels/SimpleAASSubmodel/dataElements/integerProperty/value");
		Map<String, Object> result = (Map<String, Object>) proxy
				.getModelPropertyValue("path://a1/aas/submodels/SimpleAASSubmodel/dataElements/integerProperty/value");
		assertEquals(123, result.get(SingleProperty.VALUE));

		// test deleting from a valid aas
		proxy.deleteValue("path://a1/aas/submodels/SimpleAASSubmodel/dataElements/integerProperty/value");
		result = (Map<String, Object>) proxy
				.getModelPropertyValue("path://a1/aas/submodels/SimpleAASSubmodel/dataElements/integerProperty/");
		assertNull(result.get("value"));
	}

	@Test
	public void invokeExceptionTest() {
		// Invoke exception1
		try {
			proxy.invokeOperation("path://a1/aas/submodels/SimpleAASSubmodel/operations/exception1/invokable");
			fail();
		} catch (ServerException e) {
			assertEquals(NullPointerException.class.getCanonicalName(), e.getType());
		}
		// Invoke exception2
		try {
			proxy.invokeOperation("path://a1/aas/submodels/SimpleAASSubmodel/operations/exception2/invokable", "prop1");
			fail();
		} catch (ServerException e) {
			assertEquals("ExType", e.getType());
		}
	}

	@Test
	public void invokeTest() {
		// test invoking from an invalid aas
		assertNull(proxy.invokeOperation("path://A1/aas/submodels/SimpleAASSubmodel/operations/complex/invokable", 10, 3));

		// test invoking with return value
		assertEquals(7, proxy.invokeOperation("path://a1/aas/submodels/SimpleAASSubmodel/operations/complex/invokable", 10, 3));
		assertEquals(true, proxy.invokeOperation("path://a1/aas/submodels/SimpleAASSubmodel/operations/simple/invokable"));
	}
}
