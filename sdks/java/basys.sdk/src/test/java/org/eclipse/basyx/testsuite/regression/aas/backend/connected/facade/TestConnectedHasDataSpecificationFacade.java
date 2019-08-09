package org.eclipse.basyx.testsuite.regression.aas.backend.connected.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.TypeDestroyer.TypeDestroyer;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasDataSpecificationFacade;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Key;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.Before;
import org.junit.Test;

public class TestConnectedHasDataSpecificationFacade {

	ConnectedHasDataSpecificationFacade remote;
	HasDataSpecification local;

	@Before
	public void build() {
		Key keyobj = new Key("Type1", false, "Value1", "TypeID1");
		ArrayList<Key> keyArray = new ArrayList<Key>();
		keyArray.add(keyobj);

		Reference ref = new Reference(keyArray);

		HashSet<IReference> refSet = new HashSet<IReference>();
		refSet.add(ref);

		local = new HasDataSpecification();
		local.setDataSpecificationReferences(refSet);
		Map<String, Object> destroyType = TypeDestroyer.destroyType(local);

		// Create a dummy connection manager containing the created SubModel map
		VABConnectionManager manager = new VABConnectionManagerStub(new VirtualPathModelProvider(destroyType));

		remote = new ConnectedHasDataSpecificationFacade("", manager.connectToVABElement(""));
	}

	@Test
	public void test() {
		// assertEquals(local.getAdministration(), remote.getAdministration());
		// assertEquals(local.getIdentification(), remote.getIdentification());
		assertEquals(local.getDataSpecificationReferences(), remote.getDataSpecificationReferences());

		for (IReference localReference : local.getDataSpecificationReferences()) {
			assertTrue(remote.getDataSpecificationReferences().contains(localReference));
		}
		// assertEquals(local.getAdministration().getVersion(), remote.getAdministration().getVersion());
		// assertEquals(local.getIdentification().getId(), remote.getIdentification().getId());
		// assertEquals(local.getIdentification().getIdType(), remote.getIdentification().getIdType());
	}
}
