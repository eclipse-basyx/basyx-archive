/**
 * 
 */
package org.eclipse.basyx.testsuite.regression.aas.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.testsuite.regression.submodel.restapi.SimpleAASSubmodel;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.vab.exception.ServerException;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests the capability to multiplex of a VABMultiSubmodelProvider
 * 
 * @author schnicke, kuhn
 *
 */
public class VABMultiSubmodelProviderTest {
	
	private static Logger logger = LoggerFactory.getLogger(VABMultiSubmodelProviderTest.class);
	
	VABElementProxy proxy;

	@Before
	public void build() {
		VABConnectionManagerStub stub = new VABConnectionManagerStub();
		String urn = "urn:fhg:es.iese:aas:1:1:submodel";
		VABMultiSubmodelProvider provider = new VABMultiSubmodelProvider();
		// set dummy aas
		provider.setAssetAdministrationShell(new AASModelProvider(new HashMap<String, Object>()));
		provider.addSubmodel("SimpleAASSubmodel", new SubModelProvider(new SimpleAASSubmodel()));
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
			proxy.invokeOperation("/aas/submodels/SimpleAASSubmodel/operations/exception1/invokable");
			fail();
		} catch (ServerException e) {
		}
		// Invoke operationEx2
		try {
			proxy.invokeOperation("/aas/submodels/SimpleAASSubmodel/operations/exception2/invokable", "prop1");
			fail();
		} catch (ServerException e) {

		}
	}

	@Test
	public void invokeTest() {
		// Invoke operation
		assertEquals(7, proxy.invokeOperation("/aas/submodels/SimpleAASSubmodel/operations/complex/invokable", 10, 3));
		assertEquals(true, proxy.invokeOperation("/aas/submodels/SimpleAASSubmodel/operations/simple/invokable"));
	}

	@Test
	public void getTest() {
		getTestRunner("SimpleAASSubmodel");
	}

	@Test
	public void createDeleteTest() {

		proxy.createValue("/aas/submodels", new SimpleAASSubmodel("TestSM"));

		getTestRunner("TestSM");

		proxy.deleteValue("/aas/submodels/TestSM");

		try {
			proxy.getModelPropertyValue("/aas/submodels/TestSM");
			fail();
		} catch (ServerException e) {
			logger.trace("[TEST] VABMultiSubmodelProvider CreateDelete passed");
		}
	}

	@SuppressWarnings("unchecked")
	void getTestRunner(String smId) {
		// Get property value
		Map<String, Object> value = (Map<String, Object>) proxy
				.getModelPropertyValue("/aas/submodels/" + smId + "/dataElements/integerProperty/value");
		assertEquals(123, value.get(SingleProperty.VALUE));
	}
}
