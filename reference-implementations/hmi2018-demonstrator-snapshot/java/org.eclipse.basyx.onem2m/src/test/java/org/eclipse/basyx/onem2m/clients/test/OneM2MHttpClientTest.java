package org.eclipse.basyx.onem2m.clients.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.eclipse.basyx.onem2m.clients.OneM2MHttpClient;
import org.eclipse.basyx.onem2m.clients.OneM2MHttpClientConfig;
import org.eclipse.basyx.onem2m.clients.OneM2MHttpClientConfig.Protocol;
import org.eclipse.basyx.onem2m.xml.protocols.Cb;
import org.eclipse.basyx.onem2m.xml.protocols.Rqp;
import org.eclipse.basyx.onem2m.xml.protocols.Rsp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OneM2MHttpClientTest {
	
	OneM2MHttpClient client = null;
	
	@Before
	public void before() throws Exception {		
		client = new OneM2MHttpClient(new OneM2MHttpClientConfig(Protocol.HTTP, "localhost", 8080, "admin:admin"));
		client.start();
	}
	
	@After
	public void after() throws Exception {
		client.stop();
	}
	
	@Test
	public void testRetrieveCb() throws InterruptedException, TimeoutException, ExecutionException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Rqp rqp = this.client.createDefaultRequest("", false);
		rqp.setOp(BigInteger.valueOf(2));
		rqp.setRqi("1337");
		Rsp rsp = this.client.send(rqp);
		assertEquals("1337", rsp.getRqi());
		assertEquals(1, rsp.getPc().getAnyOrAny().size());
		Object cbObj = rsp.getPc().getAnyOrAny().iterator().next();
		assertTrue(cbObj instanceof Cb);
	}
	


}
