package org.eclipse.basyx.testsuite.regression.aas.backend.connected.facade;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.aas.backend.connected.TypeDestroyer.TypeDestroyer;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasKindFacade;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind.HasKind;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.Before;
import org.junit.Test;

public class TestConnectedHasKindFacade {

	HasKind local;
	ConnectedHasKindFacade remote;

	@Before
	public void build() {
		local = new HasKind("Kind");
	
		Map<String, Object> destroyType = TypeDestroyer.destroyType(local);

		// Create a dummy connection manager containing the created SubModel map
		VABConnectionManager manager = new VABConnectionManagerStub(new VirtualPathModelProvider(destroyType));

		// Create the ConnectedSubModel based on the manager stub
		remote = new ConnectedHasKindFacade("", manager.connectToVABElement(""));
	}
	
	@Test
	public void test() {
	
		assertEquals(local.getHasKindReference(), remote.getHasKindReference());
	}

}
