package org.eclipse.basyx.testsuite.regression.aas.backend.connected;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.aas.backend.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell_;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel_;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.core.ref.VABElementRef;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author schnicke
 *
 */
public class ConnectedAssetAdministrationShellTest {
	private static final String smId = "smId";
	private static final String aasId = "aasId";

	VABConnectionManagerStub connectionStub;
	ConnectedAssetAdministrationShell connectedAAS;

	@Before
	public void build() {
		MetaModelElementFactory factory = new MetaModelElementFactory();

		SubModel_ sm = factory.create(new SubModel_(), new ArrayList<>(), new ArrayList<>());

		sm.setId(smId);
		connectionStub = new VABConnectionManagerStub();
		connectionStub.addProvider(smId, new VABHashmapProvider(sm));

		Set<VABElementRef> refs = new HashSet<>();
		refs.add(new VABElementRef(smId));

		AssetAdministrationShell_ aas = factory.create(new AssetAdministrationShell_(), refs);
		aas.put("idShort", aasId);
		connectionStub.addProvider(aasId, new VABHashmapProvider(aas));

		VABElementProxy aasProxy = connectionStub.connectToVABElement(aasId);

		connectedAAS = new ConnectedAssetAdministrationShell("", aasProxy, connectionStub);
	}

	@Test
	public void testGetId() {
		assertEquals(aasId, connectedAAS.getId());
	}

	@Test
	public void testGetRef() {
		assertEquals(1, connectedAAS.getSubModels().size());
		assertTrue(connectedAAS.getSubModels().containsKey(smId));
	}
}
