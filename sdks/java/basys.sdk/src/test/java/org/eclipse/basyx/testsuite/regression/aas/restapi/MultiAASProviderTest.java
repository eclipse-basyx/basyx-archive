/**
 * 
 */
package org.eclipse.basyx.testsuite.regression.aas.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Map;

import org.eclipse.basyx.aas.restapi.MultiAASProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.submodel.restapi.SubmodelElementProvider;
import org.eclipse.basyx.testsuite.regression.submodel.restapi.SimpleAASSubmodel;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the capability to multiplex of a VABMultiAASProvider
 * 
 * @author espen
 *
 */
public class MultiAASProviderTest {
	VABElementProxy proxy;
	MultiAASProvider provider;

	@Before
	public void build() {
		VABConnectionManagerStub stub = new VABConnectionManagerStub();
		String urn = "urn:fhg:es.iese:aas:1:1:submodel";
		VABMultiSubmodelProvider aasProvider = new VABMultiSubmodelProvider();
		aasProvider.addSubmodel("SimpleAASSubmodel", new SubModelProvider(new SimpleAASSubmodel()));
		provider = new MultiAASProvider();
		provider.addMultiSubmodelProvider("a1", aasProvider);
		stub.addProvider(urn, "", provider);
		proxy = stub.connectToVABElement(urn);
	}

	@Test
	public void clearTest() {
		provider.clear();
		Object result = proxy.getModelPropertyValue("a1/aas/submodels/SimpleAASSubmodel/");
		assertNull(result);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getTest() {
		// test reading from a valid aas
		Map<String, Object> result = (Map<String, Object>) proxy
				.getModelPropertyValue("a1/aas/submodels/SimpleAASSubmodel/" + SubmodelElementProvider.PROPERTIES + "/integerProperty/value");
		assertEquals(123, result.get(Property.VALUE));

		// test reading from an invalid aas
		assertNull(proxy.getModelPropertyValue("A1/aas/submodels/SimpleAASSubmodel/"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void setTest() {
		// test setting in a valid aas
		proxy.setModelPropertyValue("a1/aas/submodels/SimpleAASSubmodel/" + SubmodelElementProvider.PROPERTIES + "/integerProperty/value", 100);

		// test setting in an invalid aas
		proxy.setModelPropertyValue("A1/aas/submodels/SimpleAASSubmodel/" + SubmodelElementProvider.PROPERTIES + "/integerProperty/value", 200);

		// retrieving property
		Map<String, Object> result = (Map<String, Object>) proxy
				.getModelPropertyValue("a1/aas/submodels/SimpleAASSubmodel/" + SubmodelElementProvider.PROPERTIES + "/integerProperty/value");
		assertEquals(100, result.get(Property.VALUE));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void removeTest() {
		// test deleting from an invalid aas
		proxy.deleteValue("A1/aas/submodels/SimpleAASSubmodel/" + SubmodelElementProvider.PROPERTIES + "/integerProperty/value");
		Map<String, Object> result = (Map<String, Object>) proxy
				.getModelPropertyValue("a1/aas/submodels/SimpleAASSubmodel/" + SubmodelElementProvider.PROPERTIES + "/integerProperty/value");
		assertEquals(123, result.get(Property.VALUE));

		// test deleting from a valid aas
		proxy.deleteValue("a1/aas/submodels/SimpleAASSubmodel/" + SubmodelElementProvider.PROPERTIES + "/integerProperty");
		try {
			proxy.getModelPropertyValue("a1/aas/submodels/SimpleAASSubmodel/" + SubmodelElementProvider.PROPERTIES + "/integerProperty/");
			fail();
		} catch (ResourceNotFoundException e) {
		}
	}

	@Test
	public void invokeExceptionTest() {
		// Invoke exception1
		try {
			proxy.invokeOperation("a1/aas/submodels/SimpleAASSubmodel/operations/exception1");
			fail();
		} catch (ProviderException e) {
			assertEquals(NullPointerException.class, e.getCause().getClass());
		}
		// Invoke exception2
		try {
			proxy.invokeOperation("a1/aas/submodels/SimpleAASSubmodel/operations/exception2", "prop1");
			fail();
		} catch (ProviderException e) {
			assertEquals("Exception description", e.getMessage());
		}
	}

	@Test
	public void invokeTest() {
		// test invoking from an invalid aas
		assertNull(proxy.invokeOperation("A1/aas/submodels/SimpleAASSubmodel/operations/complex", 10, 3));

		// test invoking with return value
		assertEquals(7, proxy.invokeOperation("a1/aas/submodels/SimpleAASSubmodel/operations/complex", 10, 3));
		assertEquals(true, proxy.invokeOperation("a1/aas/submodels/SimpleAASSubmodel/operations/simple"));
	}
}
