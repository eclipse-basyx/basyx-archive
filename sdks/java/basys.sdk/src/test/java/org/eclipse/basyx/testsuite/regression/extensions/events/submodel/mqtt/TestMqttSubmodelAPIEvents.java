package org.eclipse.basyx.testsuite.regression.extensions.events.submodel.mqtt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.eclipse.basyx.extensions.events.submodel.mqtt.MqttSubmodelAPI;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.vab.VABSubmodelAPI;
import org.eclipse.basyx.vab.modelprovider.map.VABMapProvider;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.moquette.broker.Server;
import io.moquette.broker.config.ClasspathResourceLoader;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.IResourceLoader;
import io.moquette.broker.config.ResourceLoaderConfig;

/**
 * Tests events emitting with the MqttSubmodelAPI
 * 
 * @author espen
 *
 */
public class TestMqttSubmodelAPIEvents {
	private static Server mqttBroker;
	private static MqttSubmodelAPI eventAPI;
	private MqttTestListener listener;

	/**
	 * Sets up the MQTT broker and submodelAPI for tests
	 */
	@BeforeClass
	public static void setUpClass() throws MqttException, IOException {
		// Start MQTT broker
		mqttBroker = new Server();
		IResourceLoader classpathLoader = new ClasspathResourceLoader();
		final IConfig classPathConfig = new ResourceLoaderConfig(classpathLoader);
		mqttBroker.startServer(classPathConfig);

		// Create submodel
		SubModel sm = new SubModel("testSM", new Identifier(IdentifierType.CUSTOM, "testSM"));
		VABSubmodelAPI vabAPI = new VABSubmodelAPI(new VABMapProvider(sm));
		eventAPI = new MqttSubmodelAPI(vabAPI, "tcp://localhost:1884", "testClient");
	}

	@AfterClass
	public static void tearDownClass() {
		mqttBroker.stopServer();
	}
	
	@Before
	public void setUp() {
		listener = new MqttTestListener();
		mqttBroker.addInterceptHandler(listener);
	}
	
	@After
	public void tearDown() {
		mqttBroker.removeInterceptHandler(listener);
	}

	@Test
	public void testAddSubmodelElement() throws InterruptedException {
		Property prop = new Property(true);
		prop.setIdShort("testAddProp");
		eventAPI.addSubmodelElement(prop);

		assertEquals("testAddProp", listener.lastPayload);
		assertEquals(MqttSubmodelAPI.TOPIC_ADDELEMENT, listener.lastTopic);
	}

	@Test
	public void testAddNestedSubmodelElement() {
		SubmodelElementCollection coll = new SubmodelElementCollection();
		coll.setIdShort("testColl");
		eventAPI.addSubmodelElement(coll);

		Property prop = new Property(true);
		prop.setIdShort("testAddProp");
		eventAPI.addSubmodelElement("/testColl/testAddProp/", prop);

		assertEquals("testColl/testAddProp", listener.lastPayload);
		assertEquals(MqttSubmodelAPI.TOPIC_ADDELEMENT, listener.lastTopic);
	}

	@Test
	public void testDeleteSubmodelElement() {
		Property prop = new Property(true);
		prop.setIdShort("testDeleteProp");
		eventAPI.addSubmodelElement(prop);
		eventAPI.deleteSubmodelElement("/testDeleteProp");

		assertEquals("testDeleteProp", listener.lastPayload);
		assertEquals(MqttSubmodelAPI.TOPIC_DELETEELEMENT, listener.lastTopic);
	}

	@Test
	public void testUpdateSubmodelElement() {
		Property prop = new Property(true);
		prop.setIdShort("testUpdateProp");
		eventAPI.addSubmodelElement(prop);
		eventAPI.updateSubmodelElement("testUpdateProp", false);

		assertFalse((boolean) eventAPI.getSubmodelElementValue("testUpdateProp"));
		assertEquals("testUpdateProp", listener.lastPayload);
		assertEquals(MqttSubmodelAPI.TOPIC_UPDATEELEMENT, listener.lastTopic);
	}
}
