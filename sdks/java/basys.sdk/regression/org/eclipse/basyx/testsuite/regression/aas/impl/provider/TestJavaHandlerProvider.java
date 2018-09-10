package org.eclipse.basyx.testsuite.regression.aas.impl.provider;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.impl.provider.JavaHandlerProvider;
import org.eclipse.basyx.aas.impl.provider.javahandler.JavaHandler;
import org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments.TestProviderFull_delete;
import org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments.TestProviderFull_get;
import org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments.TestProviderFull_invoke;
import org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments.TestProviderFull_set;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.Stub1AAS;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub1SubmodelType;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub1SubmodelType.NestedPropertyType;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for IModelProviders. It checks whether IModelProviders are able to
 * perform basic VAB operations on AAS without external references in their AAS
 * or sub models. No serialization is evaluated.
 * 
 * @author kuhn
 *
 */

public class TestJavaHandlerProvider {

	JavaHandlerProvider subModelProvider;
	Stub1AAS stub1AAS;
	Stub1SubmodelType stub1SM;

	@Before
	public void buildJavaHandler() {
		subModelProvider = new JavaHandlerProvider();
		// - Create AAS and sub model instances
		stub1AAS = new Stub1AAS();
		stub1SM = new Stub1SubmodelType(stub1AAS);

		JavaHandler<Stub1AAS> aasHandler = new JavaHandler<Stub1AAS>(stub1AAS);
		aasHandler.addSubModel("statusSM");

		JavaHandler<Stub1SubmodelType> smHandler = new JavaHandler<Stub1SubmodelType>(stub1SM);
		smHandler.addProperty("sampleProperty1", (obj) -> {
			return obj.sampleProperty1;
		}, (obj, val) -> {
			obj.sampleProperty1 = (int) val;
		}, null, null);
		smHandler.addProperty("sampleProperty2", (obj) -> {
			return obj.sampleProperty2;
		}, (obj, val) -> {
			obj.sampleProperty2 = (int) val;
		}, null, null);
		smHandler.addProperty("sampleProperty3", (obj) -> {
			return obj.sampleProperty3;
		}, (obj, val) -> {
			obj.sampleProperty3 = (NestedPropertyType) val;
		}, null, null);
		smHandler.addProperty("sampleProperty3.properties.samplePropertyA", (obj) -> {
			return obj.sampleProperty3.samplePropertyA;
		}, (obj, val) -> {
			obj.sampleProperty3.samplePropertyA = (int) val;
		}, null, null);
		smHandler.addProperty("sampleProperty3.properties.samplePropertyB", (obj) -> {
			return obj.sampleProperty3.samplePropertyB;
		}, (obj, val) -> {
			obj.sampleProperty3.samplePropertyB = (int) val;
		}, null, null);
		smHandler.addProperty("sampleProperty3.properties", (obj) -> {
			return obj.sampleProperty3.getProperties();
		}, null, null, null);
		smHandler.addProperty("sampleProperty4", (obj) -> {
			return obj.sampleProperty4;
		}, null, (obj, val) -> {
			obj.sampleProperty4.add((int) ((Object[]) val)[0]);
		}, (obj, val) -> {
			obj.sampleProperty4.remove((int) val);
		});

		smHandler.addOperation("sum", (obj, val) -> {
			return obj.sum((int) val[0], (int) val[1]);
		});
		smHandler.addOperation("sampleProperty3.sub", (obj, val) -> {
			return obj.sampleProperty3.sub((int) val[0], (int) val[1]);
		});

		// - Add models to provider
		subModelProvider.addHandler(aasHandler);
		subModelProvider.addHandler(smHandler);
	}

	@Test
	public void testGet() throws Exception {

		// Java Handler specific testcases
		Object property1I = subModelProvider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/0");
		Object property2I = subModelProvider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/1");
		Object property3I = subModelProvider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/2");
		Object property3AI = subModelProvider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/3");
		Object property3BI = subModelProvider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/4");

		// - Check results
		IAssetAdministrationShell aasStub = (IAssetAdministrationShell) subModelProvider.getModelPropertyValue("Stub1AAS/aas");
		ISubModel subModelStub = (ISubModel) subModelProvider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM");
		assertTrue(aasStub == stub1AAS);
		assertTrue(subModelStub == stub1SM);
		assertTrue((int) property1I == 2);
		assertTrue((int) property2I == 3);
		assertTrue(property3I instanceof Stub1SubmodelType.NestedPropertyType);
		assertTrue((int) property3AI == 4);
		assertTrue((int) property3BI == 5);

		// Get AAS sub model property values via unique sub model ID
		ISubModel subMode2Stub = (ISubModel) subModelProvider.getModelPropertyValue("statusSM/submodel");
		Object property1aI = subModelProvider.getModelPropertyValue("statusSM/submodel/properties/0");
		Object property2aI = subModelProvider.getModelPropertyValue("statusSM/submodel/properties/1");

		// - Check results
		assertTrue(subMode2Stub == stub1SM);
		assertTrue((int) property1aI == 2);
		assertTrue((int) property2aI == 3);

		TestProviderFull_get.testGet(subModelProvider);
	}

	@Test
	public void testSet() throws Exception {
		TestProviderFull_set.testSet(subModelProvider);

		// Set AAS submodel property values over unique ID
		subModelProvider.setModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/0", 2);

		// Test if property value has been set
		Object prop = subModelProvider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM/properties/0");
		assertTrue((int) prop == 2);
	}

	@Test
	public void testInvoke() throws Exception {
		TestProviderFull_invoke.invokeTest(subModelProvider);
	}

	@Test
	public void testDelete() throws Exception {
		TestProviderFull_delete.testDelete(subModelProvider);
	}
}
