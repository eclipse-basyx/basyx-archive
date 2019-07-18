package org.eclipse.basyx.regression.processengineconnector.tests;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.aas.backend.http.tools.factory.DefaultTypeFactory;
import org.eclipse.basyx.components.processengine.connector.DeviceServiceDelegate;
import org.eclipse.basyx.regression.support.processengine.stubs.BPMNEngineStub;
import org.eclipse.basyx.regression.support.processengine.stubs.DeviceServiceExecutorStub;
import org.junit.Test;

/**
 * Test functionalities of the JavaDelegate invoked by the process eigne for services calls
 * 
 * @author zhangzai
 * */
public class TestJavaDelegate {
	GSONTools gson = new GSONTools(new DefaultTypeFactory());
	
	@Test
	public void testMoveToCall() {
		Object params[] = new Object[]{5}; 
		BPMNEngineStub bpmnstub = new BPMNEngineStub("moveTo","coilcar",gson.serialize(new ArrayList<Object>(Arrays.asList(params)))); 
		DeviceServiceDelegate.setDeviceServiceExecutor(new DeviceServiceExecutorStub());
		try {
			bpmnstub.callJavaDelegate();
			assertEquals("moveTo", DeviceServiceDelegate.getExecutor().getServiceName());
			assertEquals("coilcar", DeviceServiceDelegate.getExecutor().getServiceProvider());
			assertArrayEquals(new Object[]{5}, DeviceServiceDelegate.getExecutor().getParams().toArray());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLiftToCall() {
		Object params[] = new Object[]{123}; 
		BPMNEngineStub bpmnstub = new BPMNEngineStub("liftTo","coilcar",gson.serialize(new ArrayList<Object>(Arrays.asList(params)))); 
		DeviceServiceDelegate.setDeviceServiceExecutor(new DeviceServiceExecutorStub());
		try {
			bpmnstub.callJavaDelegate();
			assertEquals("liftTo", DeviceServiceDelegate.getExecutor().getServiceName());
			assertEquals("coilcar", DeviceServiceDelegate.getExecutor().getServiceProvider());
			assertArrayEquals(new Object[]{123}, DeviceServiceDelegate.getExecutor().getParams().toArray());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
