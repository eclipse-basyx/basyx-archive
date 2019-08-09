package org.eclipse.basyx.testsuite.regression.aas.backend.connected.facade;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.backend.connected.TypeDestroyer.TypeDestroyer;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedQualifiableFacade;
import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable.Formula;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Key;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.junit.Before;
import org.junit.Test;

public class TestConnectedQualifiableFacade {
	Qualifiable local;
	ConnectedQualifiableFacade remote;


	@Before
	public void build() {
		Key keyobj = new Key("Type1", false, "Value1", "TypeID1");
		ArrayList<Key> keyArray = new ArrayList<Key>();
		keyArray.add(keyobj);

		Reference ref = new Reference(keyArray);

		Set<Reference> refSet = new HashSet<Reference>();
		refSet.add(ref);
		

		Formula formula = new Formula(refSet);

		local = new Qualifiable(formula);
	
		Map<String, Object> destroyType = TypeDestroyer.destroyType(local);
		// Create a dummy connection manager containing the created SubModel map
	
		VABConnectionManager manager = new VABConnectionManagerStub(new VirtualPathModelProvider(destroyType));

		remote = new ConnectedQualifiableFacade("", manager.connectToVABElement(""));
	}
	
	@Test
	public void test() {
	
		assertEquals(local.getQualifier(), remote.getQualifier());
	}
}
