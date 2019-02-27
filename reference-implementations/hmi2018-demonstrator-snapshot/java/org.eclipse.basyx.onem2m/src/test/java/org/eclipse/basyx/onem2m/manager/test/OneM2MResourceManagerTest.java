package org.eclipse.basyx.onem2m.manager.test;

import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.onem2m.clients.OneM2MHttpClient;
import org.eclipse.basyx.onem2m.clients.OneM2MHttpClientConfig;
import org.eclipse.basyx.onem2m.clients.OneM2MHttpClientConfig.Protocol;
import org.eclipse.basyx.onem2m.manager.DataResult;
import org.eclipse.basyx.onem2m.manager.OneM2MResourceManager;
import org.eclipse.basyx.onem2m.manager.ResourceResult;
import org.eclipse.basyx.onem2m.xml.protocols.Ae;
import org.eclipse.basyx.onem2m.xml.protocols.Cnt;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OneM2MResourceManagerTest {

	
	OneM2MHttpClient client = null;
	OneM2MResourceManager manager = null;
	
	@Before
	public void before() throws Exception {		
		client = new OneM2MHttpClient(new OneM2MHttpClientConfig(Protocol.HTTP, "localhost", 8080, "admin:admin"));
		client.start();
		manager = new OneM2MResourceManager(client);
	}
	
	@After
	public void after() throws Exception {		
		client.stop();
	}
	
	/*
	@Tests
	public void testCreateExample() throws Exception {
		Cnt cnt = new Cnt();
		cnt.setRn("test-java");
		this.manager.createResource("", false, cnt);
		assertTrue(true);
	}

	@Test
	public void testRetrieveExample() throws Exception {
		ResourceResult<Resource> rr = this.manager.retrieveResource("test-java", true);
		assertTrue(true);
	}
	*/

	@Test
	public void testMyTest() throws Exception {
		ResourceResult<Ae> rr = this.manager.createApplicationEntity01("", true);
		System.out.println(rr);
		
		Cnt cnt = new Cnt();
		cnt.setRn("test-java");
		this.manager.createResource("", false, cnt);
		for (Integer i = 0; i < 100; ++i) {
			this.manager.createContentInstance01("test-java", i.toString(), true);
		}
		Thread.sleep(1000);
		//DataResult<Boolean> dr = this.manager.deleteResource("test-java", true);
		assertTrue(true);
		
	}
	
}
