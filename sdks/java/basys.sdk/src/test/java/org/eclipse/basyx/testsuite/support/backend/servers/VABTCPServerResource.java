package org.eclipse.basyx.testsuite.support.backend.servers;

import org.eclipse.basyx.testsuite.support.vab.stub.elements.SimpleVABElement;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.rules.ExternalResource;

/**
 * This class initializes a TCP Server and adds a provided element to it. Note that this server can only provide one
 * model per port and new servers have to be started for each model.
 * The factory pattern makes sure the server is only started once and teared down after only after all tests have run.
 *
 * @author pschorn
 *
 */
public class VABTCPServerResource extends ExternalResource {
	private static int refCount = 0;

	private static VABTCPServerResource currentInstance;

	private AASTCPServer server;

	public static VABTCPServerResource getTestResource() {
		if (refCount == 0) {
			currentInstance = new VABTCPServerResource();
		}
		return currentInstance;
	}

	@Override
	protected void before() {
		try {
			if (refCount == 0) {
				System.out.println("Do actual TestResources init");

				VABHashmapProvider provider = new VABHashmapProvider(new SimpleVABElement());

				server = new AASTCPServer(provider);
				server.start();

			}
		} finally {
			refCount++;
		}
	}

	@Override
	protected void after() {
		System.out.println("TestResources after");
		refCount--;
		if (refCount == 0) {
			System.out.println("Do actual TestResources destroy");

			server.stop();
		}
	}
}