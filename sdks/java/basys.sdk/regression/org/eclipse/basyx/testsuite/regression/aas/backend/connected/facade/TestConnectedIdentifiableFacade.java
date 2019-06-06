package org.eclipse.basyx.testsuite.regression.aas.backend.connected.facade;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.backend.connected.facades.ConnectedIdentifiableFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;

public class TestConnectedIdentifiableFacade {

	ConnectedIdentifiableFacade remote;
	Identifiable local;
	
	@Before
	public void build() {
		local = new Identifiable("vesrion", "revision", "idshort", "category", "description", "idType", "id");
		  
		// Create a dummy connection manager containing the created SubModel map
		VABConnectionManager manager = new VABConnectionManagerStub(new VABHashmapProvider(local));

		// Create the ConnectedSubModel based on the manager stub
		remote = new ConnectedIdentifiableFacade("", manager.connectToVABElement(""));
	}
	
	@Test
	public void test() {
		//assertEquals(local.getAdministration(), remote.getAdministration());
		//assertEquals(local.getIdentification(), remote.getIdentification());
		assertEquals(local.getAdministration().getDataSpecificationReferences(), remote.getAdministration().getDataSpecificationReferences());
		assertEquals(local.getAdministration().getRevision(), remote.getAdministration().getRevision());
		assertEquals(local.getAdministration().getVersion(), remote.getAdministration().getVersion());
		assertEquals(local.getIdentification().getId(), remote.getIdentification().getId());
		assertEquals(local.getIdentification().getIdType(), remote.getIdentification().getIdType());
	}
}
