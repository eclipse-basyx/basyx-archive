package org.eclipse.basyx.testsuite.regression.aas.backend.connected.facade;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.TypeDestroyer.TypeDestroyer;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasDataSpecificationFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Key;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;

public class TestConnectedHasDataSpecificationFacade {
	
	ConnectedHasDataSpecificationFacade remote;
	HasDataSpecification local;
	
	@Before
	public void build() {
		Key keyobj=new Key("Type1", false, "Value1", "TypeID1");
		ArrayList<Key> keyArray=new ArrayList<Key>();
		keyArray.add(keyobj);
		
		Reference ref=new Reference(keyArray);
		
		HashSet<IReference> refSet=new HashSet<IReference>();
		refSet.add(ref);
		
		local=new HasDataSpecification();
		local.setDataSpecificationReferences(refSet);
		
		Map<String, Object> destroyType = TypeDestroyer.destroyType(local);
		
		// Create a dummy connection manager containing the created SubModel map
		VABConnectionManager manager = new VABConnectionManagerStub(new VABHashmapProvider(destroyType));
		
		remote = new ConnectedHasDataSpecificationFacade("", manager.connectToVABElement(""));
	}
	
	@Test
	public void test() {
		//assertEquals(local.getAdministration(), remote.getAdministration());
		//assertEquals(local.getIdentification(), remote.getIdentification());
		assertEquals(local.getDataSpecificationReferences(), remote.getDataSpecificationReferences());
		
		IReference localRef=null;
		IReference remoteRef=null;
		for(IReference aSiteId: local.getDataSpecificationReferences()) {
			localRef = aSiteId;
		    break;
		}
		for(IReference aSiteId: remote.getDataSpecificationReferences()) {
			remoteRef = aSiteId;
		    break;
		}
		assertEquals(localRef,remoteRef);
//		assertEquals(local.getAdministration().getVersion(), remote.getAdministration().getVersion());
//		assertEquals(local.getIdentification().getId(), remote.getIdentification().getId());
//		assertEquals(local.getIdentification().getIdType(), remote.getIdentification().getIdType());
	}

}
