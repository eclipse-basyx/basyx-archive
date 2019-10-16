package org.eclipse.basyx.regression.processengineconnector.tests;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.basyx.components.processengine.connector.DeviceServiceDelegate;
import org.eclipse.basyx.regression.support.processengine.stubs.BPMNEngineStub;
import org.eclipse.basyx.regression.support.processengine.stubs.DeviceServiceExecutorStub;
import org.eclipse.basyx.vab.coder.json.serialization.DefaultTypeFactory;
import org.eclipse.basyx.vab.coder.json.serialization.GSONTools;
import org.junit.Test;

/**
 * Test functionalities of the JavaDelegate invoked by the process engine for services calls
 * 
 * @author zhangzai
 * 
 * */
public class TestJavaDelegate {
	
	/**
	 * Create the serializer/deserializer
	 */
	GSONTools gson = new GSONTools(new DefaultTypeFactory());
	
	
	/**
	 * Test the invocation of service "moveTo" through the java-delegate
	 */
	@Test
	public void testMoveToCall() {
		// service parameter
		Object params[] = new Object[]{5};
		
		// Create stub for BPMN-Engine for test purpose
		BPMNEngineStub bpmnstub = new BPMNEngineStub("moveTo","coilcar",gson.serialize(new ArrayList<Object>(Arrays.asList(params))));
		
		// Set the service executor to the java-delegate
		DeviceServiceDelegate.setDeviceServiceExecutor(new DeviceServiceExecutorStub());
		try {
			// deliver the service information to the java-delegate
			bpmnstub.callJavaDelegate();
			
			// Asset the java-delegate gets the information from the Engine-stub
			assertEquals("moveTo", DeviceServiceDelegate.getExecutor().getServiceName());
			assertEquals("coilcar", DeviceServiceDelegate.getExecutor().getServiceProvider());
			assertArrayEquals(new Object[]{5}, DeviceServiceDelegate.getExecutor().getParams().toArray());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test the invocation of service "liftTo" through the java-delegate
	 */
	@Test
	public void testLiftToCall() {
		// service parameter
		Object params[] = new Object[]{123}; 
		
		// Create stub for BPMN-Engine for test purpose
		BPMNEngineStub bpmnstub = new BPMNEngineStub("liftTo","coilcar",gson.serialize(new ArrayList<Object>(Arrays.asList(params)))); 
		
		// Set the service executor to the java-delegate
		DeviceServiceDelegate.setDeviceServiceExecutor(new DeviceServiceExecutorStub());
		try {
			
			// deliver the service information to the java-delegate
			bpmnstub.callJavaDelegate();
			
			// Asset the java-delegate gets the information from the Engine-stub
			assertEquals("liftTo", DeviceServiceDelegate.getExecutor().getServiceName());
			assertEquals("coilcar", DeviceServiceDelegate.getExecutor().getServiceProvider());
			assertArrayEquals(new Object[]{123}, DeviceServiceDelegate.getExecutor().getParams().toArray());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
