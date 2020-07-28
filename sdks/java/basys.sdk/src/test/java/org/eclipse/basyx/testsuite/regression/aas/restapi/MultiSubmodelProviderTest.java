/**
 * 
 */
package org.eclipse.basyx.testsuite.regression.aas.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.testsuite.regression.submodel.restapi.SimpleAASSubmodel;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
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
public class MultiSubmodelProviderTest {
	
	private static Logger logger = LoggerFactory.getLogger(MultiSubmodelProviderTest.class);
	
	VABElementProxy proxy;

	@Before
	public void build() {
		VABConnectionManagerStub stub = new VABConnectionManagerStub();
		String urn = "urn:fhg:es.iese:aas:1:1:submodel";
		VABMultiSubmodelProvider provider = new VABMultiSubmodelProvider();
		// set dummy aas
		provider.setAssetAdministrationShell(new AASModelProvider(new AssetAdministrationShell()));
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
		} catch (ProviderException e) {}

		// Invoke operationEx2
		try {
			proxy.invokeOperation("/aas/submodels/SimpleAASSubmodel/operations/exception2/invokable", "prop1");
			fail();
		} catch (ProviderException e) {}
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
		SubModel sm = new SimpleAASSubmodel("TestSM");
		proxy.createValue("/aas/submodels", sm);

		getTestRunner("TestSM");

		// Ensure that the Submodel References where updated
		ConnectedAssetAdministrationShell shell = new ConnectedAssetAdministrationShell(proxy.getDeepProxy("/aas"), null);
		Collection<IReference> refs = shell.getSubmodelReferences();
		assertEquals(1, refs.size());
		assertEquals(sm.getReference(), refs.iterator().next());

		proxy.deleteValue("/aas/submodels/TestSM");

		// Ensure that the Submodel Reference was removed again
		assertEquals(0, shell.getSubmodelReferences().size());

		try {
			proxy.getModelPropertyValue("/aas/submodels/TestSM");
			fail();
		} catch (ProviderException e) {
			logger.trace("[TEST] VABMultiSubmodelProvider CreateDelete passed");
		}
	}

	@SuppressWarnings("unchecked")
	void getTestRunner(String smId) {
		// Get property value
		Map<String, Object> value = (Map<String, Object>) proxy
				.getModelPropertyValue("/aas/submodels/" + smId + "/dataElements/integerProperty/value");
		assertEquals(123, value.get(Property.VALUE));

		// Get property value with /submodel suffix
		value = (Map<String, Object>) proxy.getModelPropertyValue("/aas/submodels/" + smId + "/submodel/dataElements/integerProperty/value");
		assertEquals(123, value.get(Property.VALUE));
	}
}
