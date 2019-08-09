package org.eclipse.basyx.testsuite.regression.aas.backend.connected.facade;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.aas.backend.connected.TypeDestroyer.TypeDestroyer;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedIdentifierFacade;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.Before;
import org.junit.Test;

public class TestConnectedIdentifierFacade {

	ConnectedIdentifierFacade remote;
	Identifier local;
	
	@Before
	public void build() {
		local = new Identifier("Test", "Test2");
		Map<String, Object> destroyType = TypeDestroyer.destroyType(local);
		// Create a dummy connection manager containing the created SubModel map
		VABConnectionManager manager = new VABConnectionManagerStub(new VirtualPathModelProvider(destroyType));

		// Create the ConnectedSubModel based on the manager stub
		remote = new ConnectedIdentifierFacade("", manager.connectToVABElement(""));
	}

	@Test
	public void test() {
		assertEquals(local.getId(), remote.getId());
		assertEquals(local.getIdType(), remote.getIdType());
	}
}
