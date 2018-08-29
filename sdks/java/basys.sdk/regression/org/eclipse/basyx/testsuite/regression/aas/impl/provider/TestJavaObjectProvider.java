package org.eclipse.basyx.testsuite.regression.aas.impl.provider;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.impl.provider.JavaObjectProvider;
import org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments.TestProviderFull_delete;
import org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments.TestProviderFull_get;
import org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments.TestProviderFull_getChildren;
import org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments.TestProviderFull_invoke;
import org.eclipse.basyx.testsuite.regression.aas.impl.provider.testfragments.TestProviderFull_set;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.Stub1AAS;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub1Submodel;
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

public class TestJavaObjectProvider {

	JavaObjectProvider subModelProvider;
	IAssetAdministrationShell stub1AAS;
	ISubModel stub1SM;

	@Before
	public void buildProvider() {
		// Create model provider
		subModelProvider = new JavaObjectProvider();
		// - Create AAS and sub model instances
		stub1AAS = new Stub1AAS();
		stub1SM = new Stub1Submodel(stub1AAS);
		// - Add models to provider
		subModelProvider.addModel(stub1AAS);
		subModelProvider.addModel(stub1SM);
	}

	@Test
	public void testGet() {
		// Get AAS sub model property values via AAS
		Collection<String> modelNames = subModelProvider.getAllModels();

		TestProviderFull_get.testGet(subModelProvider);

		IAssetAdministrationShell aasStub = (IAssetAdministrationShell) subModelProvider.getModelPropertyValue("Stub1AAS/aas");
		ISubModel subModelStub = (ISubModel) subModelProvider.getModelPropertyValue("Stub1AAS/aas/submodels/statusSM");

		assertTrue(modelNames.contains("statusSM"));
		assertTrue(modelNames.contains("Stub1AAS"));
		assertTrue(aasStub == stub1AAS);
		assertTrue(subModelStub == stub1SM);

		// Get AAS sub model property values via unique sub model ID
		ISubModel subMode2Stub = (ISubModel) subModelProvider.getModelPropertyValue("statusSM/submodel");

		// - Check results
		assertTrue(subMode2Stub == stub1SM);
	}

	@Test
	public void testGetChildren() {
		Collection<String> modelNames = subModelProvider.getAllModels();
		TestProviderFull_getChildren.testGetChildren(subModelProvider, modelNames);
	}

	@Test
	public void testInvoke() throws Exception {
		TestProviderFull_invoke.invokeTest(subModelProvider);
	}

	@Test
	public void testSet() throws Exception {
		TestProviderFull_set.testSet(subModelProvider);
	}

	@Test
	public void testDelete() throws Exception {
		TestProviderFull_delete.testDelete(subModelProvider);
	}
}
