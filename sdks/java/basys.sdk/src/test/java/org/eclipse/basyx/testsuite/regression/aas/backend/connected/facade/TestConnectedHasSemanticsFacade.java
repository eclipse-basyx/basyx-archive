package org.eclipse.basyx.testsuite.regression.aas.backend.connected.facade;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.basyx.aas.backend.connected.TypeDestroyer.TypeDestroyer;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasSemanticsFacade;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Key;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.Before;
import org.junit.Test;

public class TestConnectedHasSemanticsFacade {
	HasSemantics local;
	ConnectedHasSemanticsFacade remote;

	@Before
	public void build() {
		Key keyobj = new Key("Type1", false, "Value1", "TypeID1");
		ArrayList<Key> keyArray = new ArrayList<Key>();
		keyArray.add(keyobj);
		Reference ref = new Reference(keyArray);

		local = new HasSemantics(ref);
		Map<String, Object> destroyType = TypeDestroyer.destroyType(local);

		// Create a dummy connection manager containing the created SubModel map
		VABConnectionManager manager = new VABConnectionManagerStub(new VirtualPathModelProvider(destroyType));
		remote = new ConnectedHasSemanticsFacade("", manager.connectToVABElement(""));
	}

	@Test
	public void test() {
		assertEquals(local.getSemanticId().getKeys(), remote.getSemanticId().getKeys());
	}

}
