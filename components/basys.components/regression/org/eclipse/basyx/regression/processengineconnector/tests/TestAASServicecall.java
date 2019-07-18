package org.eclipse.basyx.regression.processengineconnector.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.basyx.aas.backend.provider.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.components.processengine.connector.DeviceServiceExecutor;
import org.eclipse.basyx.regression.support.processengine.aas.DeviceAdministrationShellFactory;
import org.eclipse.basyx.regression.support.processengine.executor.CoilcarServiceExecutor;
import org.eclipse.basyx.regression.support.processengine.stubs.CoilcarStub;
import org.eclipse.basyx.regression.support.processengine.submodel.DeviceSubmodelFactory;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;



public class TestAASServicecall {
	private DeviceServiceExecutor serviceExecutor;
	private VABConnectionManagerStub connectionStub;
	private CoilcarStub coilcar;
	

	
	
	@Before
	public void setupDeviceServiceExecutor() {
		AssetAdministrationShell aas = new DeviceAdministrationShellFactory().create( "coilcar", "submodel1");
		coilcar = new CoilcarStub();
		SubModel sm = new DeviceSubmodelFactory().create("submodel1", coilcar);
		// TODO more comments
		VABMultiSubmodelProvider<VABHashmapProvider> provider = new VABMultiSubmodelProvider<>();
		provider.addSubmodel("submodel1", new VABHashmapProvider(sm));
		provider.setAssetAdministrationShell(new VABHashmapProvider(aas));
		
		// setup the connection-manager with the model-provider
		connectionStub = new VABConnectionManagerStub();
		connectionStub.addProvider("submodel1", "",provider);
		connectionStub.addProvider("coilcar", "",provider);
		serviceExecutor = new CoilcarServiceExecutor(connectionStub);
		
	}
	
	@Test
	public void testServicecall() {
		serviceExecutor.executeService("moveTo", "coilcar", "submodel1", new ArrayList<>(Arrays.asList( new Object[] {123})));
		assertEquals(123, coilcar.getParameter());
		assertTrue(coilcar.getServiceCalled().equals("moveTo"));
		
		serviceExecutor.executeService("liftTo", "coilcar", "submodel1", new ArrayList<>(Arrays.asList( new Object[] {456})));
		assertEquals(456, coilcar.getParameter());
		assertTrue(coilcar.getServiceCalled().equals("liftTo"));
		
		serviceExecutor.executeService("moveTo", "coilcar", "submodel1", new ArrayList<>(Arrays.asList( new Object[] {789})));
		assertEquals(789, coilcar.getParameter());
		assertTrue(coilcar.getServiceCalled().equals("moveTo"));
	}
}
