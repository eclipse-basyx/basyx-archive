package org.eclipse.basyx.onem2m.manager.test;

import org.eclipse.basyx.onem2m.clients.OneM2MHttpClient;
import org.eclipse.basyx.onem2m.clients.OneM2MHttpClientConfig;
import org.eclipse.basyx.onem2m.clients.OneM2MHttpClientConfig.Protocol;
import org.eclipse.basyx.onem2m.manager.OneM2MResourceExplorer;
import org.eclipse.basyx.onem2m.manager.ResourceResults;
import org.eclipse.basyx.onem2m.xml.protocols.Resource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OneM2MResourceExplorerTest {

	OneM2MHttpClient client = null;
	OneM2MResourceExplorer explorer = null;
	
	@Before
	public void before() throws Exception {		
		client = new OneM2MHttpClient(new OneM2MHttpClientConfig(Protocol.HTTP, "localhost", 8080, "admin:admin"));
		client.start();
		explorer = new OneM2MResourceExplorer(client);
	}
	
	@After
	public void after() throws Exception {		
		client.stop();
	}
	
	@Test
	public void testDefault() throws Exception {		
		//DataResult<List<String>> dr = explorer.findResourcesWithLabels01("", new String[] { "basys-ty:aas-def" }, false);
		ResourceResults<Resource> rr1 = explorer.retrieveResourceWithChildrenRecursive("cnt-729664194", false);
		//System.out.println(dr.getData().size());
		System.out.println(rr1);
		ResourceResults<Resource> rr2 = explorer.retrieveResourceWithChildrenRecursive("acp-730351743", false);
		//System.out.println(dr.getData().size());
		System.out.println(rr2);

	}
}
