/**
 * 
 */
package org.eclipse.basyx.testsuite.regression.aas.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.basyx.aas.restapi.MultiAASProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.restapi.MultiSubmodelElementProvider;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
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
		aasProvider.addSubmodel(new SubModelProvider(new SimpleAASSubmodel()));
		provider = new MultiAASProvider();
		provider.addMultiSubmodelProvider("a1", aasProvider);
		stub.addProvider(urn, "", provider);
		proxy = stub.connectToVABElement(urn);
	}

	@Test
	public void clearTest() {
		provider.clear();
		
		// test if AAS is deleted
		try {
			proxy.getModelPropertyValue("a1/aas/submodels/SimpleAASSubmodel/");
			fail();
		} catch(ResourceNotFoundException e) {}
	}

	@Test
	public void getTest() {
		// test reading from a valid aas
		Integer result = (Integer) proxy
				.getModelPropertyValue("a1/aas/submodels/SimpleAASSubmodel/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/integerProperty/value");
		assertEquals(123, result.intValue());

		// test reading from an invalid aas
		try {
			proxy.getModelPropertyValue("A1/aas/submodels/SimpleAASSubmodel/submodel");
			fail();
		} catch(ResourceNotFoundException e) {}
	}

	@Test
	public void setTest() {
		// test setting in a valid aas
		proxy.setModelPropertyValue("a1/aas/submodels/SimpleAASSubmodel/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/integerProperty/value", 100);

		// test setting in an invalid aas
		try {
			proxy.setModelPropertyValue("A1/aas/submodels/SimpleAASSubmodel/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/integerProperty/value", 200);
			fail();
		} catch(ResourceNotFoundException e) {}

		// retrieving property
		Integer result = (Integer) proxy
				.getModelPropertyValue("a1/aas/submodels/SimpleAASSubmodel/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/integerProperty/value");
		assertEquals(100, result.intValue());
	}

	@Test
	public void removeTest() {
		// test deleting from an invalid aas
		try {
			proxy.deleteValue("A1/aas/submodels/SimpleAASSubmodel/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/integerProperty/value");
			fail();
		} catch(ResourceNotFoundException e) {}
		
		Integer result = (Integer) proxy
				.getModelPropertyValue("a1/aas/submodels/SimpleAASSubmodel/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/integerProperty/value");
		assertEquals(123, result.intValue());

		// test deleting from a valid aas
		proxy.deleteValue("a1/aas/submodels/SimpleAASSubmodel/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/integerProperty");
		try {
			proxy.getModelPropertyValue("a1/aas/submodels/SimpleAASSubmodel/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/integerProperty/");
			fail();
		} catch (ResourceNotFoundException e) {
		}
	}

	@Test
	public void invokeExceptionTest() {
		// Invoke exception1
		try {
			proxy.invokeOperation("a1/aas/submodels/SimpleAASSubmodel/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/exception1/" + Operation.INVOKE);
			fail();
		} catch (ProviderException e) {
			assertEquals(NullPointerException.class, e.getCause().getClass());
		}
		// Invoke exception2
		try {
			proxy.invokeOperation("a1/aas/submodels/SimpleAASSubmodel/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/exception2/" + Operation.INVOKE, "prop1");
			fail();
		} catch (ProviderException e) {
			assertEquals("Exception description", e.getMessage());
		}
	}

	@Test
	public void invokeTest() {
		// test invoking from an invalid aas
		try {
			proxy.invokeOperation("A1/aas/submodels/SimpleAASSubmodel/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/complex/" + Operation.INVOKE, 10, 3);
			fail();
		} catch(ResourceNotFoundException e) {}

		// test invoking with return value
		assertEquals(7, proxy.invokeOperation("a1/aas/submodels/SimpleAASSubmodel/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/complex/" + Operation.INVOKE, 10, 3));
		assertEquals(true, proxy.invokeOperation("a1/aas/submodels/SimpleAASSubmodel/submodel/" + MultiSubmodelElementProvider.ELEMENTS + "/simple/" + Operation.INVOKE));
	}
}
