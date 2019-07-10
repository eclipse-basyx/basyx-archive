package org.eclipse.basyx.testsuite.regression.aas.backend.connected.facade;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.backend.connected.facades.ConnectedIdentifierFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;

public class TestConnectedIdentifierFacade {

	ConnectedIdentifierFacade remote;
	Identifier local;
	
	@Before
	public void build() {
		local = new Identifier("Test", "Test2");
		  
		// Create a dummy connection manager containing the created SubModel map
		VABConnectionManager manager = new VABConnectionManagerStub(new VABHashmapProvider(local));

		// Create the ConnectedSubModel based on the manager stub
		remote = new ConnectedIdentifierFacade("", manager.connectToVABElement(""));
	}
	
	@Test
	public void test() {
		assertEquals(local.getId(), remote.getId());
		assertEquals(local.getIdType(), remote.getIdType());
	}
}
