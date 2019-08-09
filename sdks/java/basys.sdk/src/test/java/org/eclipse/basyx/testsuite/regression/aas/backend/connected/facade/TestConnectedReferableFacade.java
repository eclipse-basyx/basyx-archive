package org.eclipse.basyx.testsuite.regression.aas.backend.connected.facade;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.aas.backend.connected.TypeDestroyer.TypeDestroyer;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedReferableFacade;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.Before;
import org.junit.Test;

public class TestConnectedReferableFacade {

	Referable local;
	ConnectedReferableFacade remote;

	@Before
	public void build() {
		local = new Referable("idShort", "category", "description");
		Map<String, Object> destroyType = TypeDestroyer.destroyType(local);
		// Create a dummy connection manager containing the created SubModel map
		VABConnectionManager manager = new VABConnectionManagerStub(new VirtualPathModelProvider(destroyType));

		// Create the ConnectedSubModel based on the manager stub
		remote = new ConnectedReferableFacade("", manager.connectToVABElement(""));
	}

	@Test
	public void test() {

		assertEquals(local.getCategory(), remote.getCategory());
		assertEquals(local.getIdshort(), remote.getIdshort());
		assertEquals(local.getDescription(), remote.getDescription());
		// TODO: getParent() has to be tested in future
	}
}
