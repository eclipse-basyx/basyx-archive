package org.eclipse.basyx.aas.impl.onem2m.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.basyx.aas.api.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.AssetKind;
import org.eclipse.basyx.aas.api.resources.basic.DataType;
import org.eclipse.basyx.aas.api.resources.basic.Property;
import org.eclipse.basyx.aas.api.resources.basic.SubModel;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedCollectionProperty;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedProperty;
import org.eclipse.basyx.aas.api.resources.connected.ConnectedSingleProperty;
import org.eclipse.basyx.aas.impl.onem2m.OneM2MAssetAdministrationShellManager;
import org.eclipse.basyx.onem2m.clients.OneM2MHttpClient;
import org.eclipse.basyx.onem2m.clients.OneM2MHttpClientConfig;
import org.eclipse.basyx.onem2m.clients.OneM2MHttpClientConfig.Protocol;
import org.eclipse.basyx.onem2m.manager.OneM2MResourceManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class SmartControlManagerTest {

	OneM2MHttpClient client = null;
	OneM2MResourceManager oneM2MresourceManager = null;
	OneM2MAssetAdministrationShellManager aasManager;
	
	@Before
	public void before() throws Exception {		
		// NOTE: test requires a running oneM2M-CSE -> 127.0.0.1:8080
		client = new OneM2MHttpClient(new OneM2MHttpClientConfig(Protocol.HTTP, "127.0.0.1", 8080, "admin:admin"));
		client.start();
		oneM2MresourceManager = new OneM2MResourceManager(client);
		aasManager = new OneM2MAssetAdministrationShellManager(oneM2MresourceManager, null);
	}
	
	@After
	public void after() throws Exception {		
		client.stop();
	}
		

	@Test
	public void testSingleProperty() throws Exception {
		AssetAdministrationShell aas = new AssetAdministrationShell();
	    aas.setId("unit_test_java");
	    aas.setAssetId("assetId");
	    aas.setAssetKind(AssetKind.INSTANCE);
	    aas.setAssetTypeDefinition("assetTypeDefinition");
	    aas.setDisplayName("displayName");
		
	    SubModel sm = new SubModel();
	    sm.setAssetKind(AssetKind.INSTANCE);
	    sm.setName("subModelExample");
	    sm.setTypeDefinition("smType");
	    aas.addSubModel(sm);
	    
	    Property prop = new Property();
	    prop.setName("propExample");
	    prop.setDataType(DataType.INTEGER);
	    prop.setEventable(true);
	    prop.setReadable(false);
	    prop.setWriteable(true);
	    prop.setCollection(false);
	    sm.addProperty(prop);
	    
	    ConnectedAssetAdministrationShell caas = this.aasManager.createAAS(aas);
	    ConnectedProperty cprop = (ConnectedProperty) caas.getSubModels().get(sm.getName()).getProperties().get(prop.getName());
	    assertTrue(cprop instanceof ConnectedSingleProperty);
	    ConnectedSingleProperty csprop = (ConnectedSingleProperty) cprop;
	    Integer valExpected = new Integer(5);
	    csprop.set(valExpected);
	    Integer valActual = (Integer) csprop.get();
	    assertEquals(valExpected, valActual);
	    this.aasManager.deleteAAS(aas.getId());
	}
	
	@Test
	public void testCollectionProperty() throws Exception {
		AssetAdministrationShell aas = new AssetAdministrationShell();
	    aas.setId("unit_test_java");
	    aas.setAssetId("assetId");
	    aas.setAssetKind(AssetKind.INSTANCE);
	    aas.setAssetTypeDefinition("assetTypeDefinition");
	    aas.setDisplayName("displayName");
		
	    SubModel sm = new SubModel();
	    sm.setAssetKind(AssetKind.INSTANCE);
	    sm.setName("subModelExample");
	    sm.setTypeDefinition("smType");
	    aas.addSubModel(sm);
	    
	    Property prop = new Property();
	    prop.setName("propExample");
	    prop.setDataType(DataType.INTEGER);
	    prop.setEventable(true);
	    prop.setReadable(false);
	    prop.setWriteable(true);
	    prop.setCollection(true);
	    sm.addProperty(prop);
	    
	    
	    ConnectedAssetAdministrationShell caas = this.aasManager.createAAS(aas);	    	

	    ConnectedProperty cprop = (ConnectedProperty) caas.getSubModels().get(sm.getName()).getProperties().get(prop.getName());
	    assertTrue(cprop instanceof ConnectedCollectionProperty);
	    ConnectedCollectionProperty csprop = (ConnectedCollectionProperty) cprop;
	    Integer valExpected1 = new Integer(5);
	    csprop.set("key1", valExpected1);
	    Integer valExpected2 = new Integer(10);
	    csprop.set("key2", valExpected2);
	    
	    Integer valActual1 = (Integer) csprop.get("key1");
	    Integer valActual2 = (Integer) csprop.get("key2");
	    assertEquals(valExpected1, valActual1);
	    assertEquals(valExpected2, valActual2);
	    csprop.remove("key1");
	    assertNull(csprop.get("key1"));	    
	    this.aasManager.deleteAAS(aas.getId());
	}	
}
