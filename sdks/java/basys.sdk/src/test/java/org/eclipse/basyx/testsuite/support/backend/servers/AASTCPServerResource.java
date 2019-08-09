package org.eclipse.basyx.testsuite.support.backend.servers;

import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.testsuite.support.vab.stub.elements.SimpleVABElement;
import org.junit.rules.ExternalResource;

/**
 * This class initializes a TCP Server and adds a provided element to it. Note that this server can only provide one AAS
 * per port and new servers have to be started for each AAS.
 * The factory pattern makes sure the server is only started once and teared down after only after all tests have run.
 * 
 * @author pschorn
 *
 */
public class AASTCPServerResource extends ExternalResource {
	private static int refCount = 0;

	private static AASTCPServerResource currentInstance;

	private AASTCPServer server;

	public static AASTCPServerResource getTestResource() {
		if (refCount == 0) {
			currentInstance = new AASTCPServerResource();
		}
		return currentInstance;
	}

	protected void before() {
		try {
			if (refCount == 0) {
				System.out.println("Do actual TestResources init");

				VirtualPathModelProvider provider = new VirtualPathModelProvider(new SimpleVABElement());

				server = new AASTCPServer(provider);
				server.start();

			}
		} finally {
			refCount++;
		}
	}

	protected void after() {
		System.out.println("TestResources after");
		refCount--;
		if (refCount == 0) {
			System.out.println("Do actual TestResources destroy");

			server.stop();
		}
	}
}