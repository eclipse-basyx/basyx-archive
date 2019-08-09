package org.eclipse.basyx.regression.processengineconnector.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.components.processengine.connector.DeviceServiceExecutor;
import org.eclipse.basyx.regression.support.processengine.aas.DeviceAdministrationShellFactory;
import org.eclipse.basyx.regression.support.processengine.executor.CoilcarServiceExecutor;
import org.eclipse.basyx.regression.support.processengine.stubs.CoilcarStub;
import org.eclipse.basyx.regression.support.processengine.submodel.DeviceSubmodelFactory;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the service invocation using the service executor
 * 
 * @author zhangzai
 *
 */
public class TestAASServicecall {
	/**
	 * Service Executor to be tested, used by the process engine
	 */
	private DeviceServiceExecutor serviceExecutor;

	/**
	 * VAB connection stub needed for setting up service invocation
	 */
	private VABConnectionManagerStub connectionStub;

	/**
	 * A stub for the service sub-model
	 */
	private CoilcarStub coilcar;

	/**
	 * Setup the test environment, create aas and submodels, setup VAB connection
	 */
	@Before
	public void setupDeviceServiceExecutor() {
		// Create a device-aas for coilcar device with id "coilcar" and submodelid "submodel1"
		AssetAdministrationShell aas = new DeviceAdministrationShellFactory().create("coilcar", "submodel1");

		// Create service stub instead of real coilcar services
		coilcar = new CoilcarStub();

		// Create the submodel of services provided by the coilcar with id "submodel1"
		SubModel sm = new DeviceSubmodelFactory().create("submodel1", coilcar);

		// Create VAB multi-submodel provider for holding the sub-models
		VABMultiSubmodelProvider provider = new VABMultiSubmodelProvider();

		// Add sub-model to the provider
		provider.addSubmodel("submodel1", new VirtualPathModelProvider(sm));

		// Add aas to the provider
		provider.setAssetAdministrationShell(new VirtualPathModelProvider(aas));

		// setup the connection-manager with the model-provider
		connectionStub = new VABConnectionManagerStub();
		connectionStub.addProvider("submodel1", "", provider);
		connectionStub.addProvider("coilcar", "", provider);

		// create the service executor that calls the services using aas
		serviceExecutor = new CoilcarServiceExecutor(connectionStub);

	}

	/**
	 * Test the service invocation of the service-executor
	 */
	@Test
	public void testServicecall() {
		/*
		 * Execute the service "moveTo" on the device "coilcar",
		 * the service is located in sub-model "submodel1"
		 * and has a parameter 123
		 */
		serviceExecutor.executeService("moveTo", "coilcar", "submodel1",
				new ArrayList<>(Arrays.asList(new Object[] { 123 })));

		// Validate the parameter and service name is delivered successfully to the device stub
		assertEquals(123, coilcar.getParameter());
		assertTrue(coilcar.getServiceCalled().equals("moveTo"));

		/*
		 * Execute the service "liftTo" on the device "coilcar",
		 * the service is located in sub-model "submodel1"
		 * and has a parameter 456
		 */
		serviceExecutor.executeService("liftTo", "coilcar", "submodel1",
				new ArrayList<>(Arrays.asList(new Object[] { 456 })));

		// Validate the parameter and service name is delivered successfully to the device stub
		assertEquals(456, coilcar.getParameter());
		assertTrue(coilcar.getServiceCalled().equals("liftTo"));

		/*
		 * Execute the service "moveTo" on the device "coilcar",
		 * the service is located in sub-model "submodel1"
		 * and has a parameter 789
		 */
		serviceExecutor.executeService("moveTo", "coilcar", "submodel1",
				new ArrayList<>(Arrays.asList(new Object[] { 789 })));

		// Validate the parameter and service name is delivered successfully to the device stub
		assertEquals(789, coilcar.getParameter());
		assertTrue(coilcar.getServiceCalled().equals("moveTo"));
	}
}
