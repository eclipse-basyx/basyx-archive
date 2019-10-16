package org.eclipse.basyx.testsuite.regression.submodel.model.connected;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.IMapProperty;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.property.ConnectedMapProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.submodel.restapi.SinglePropertyProvider;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.testsuite.regression.vab.protocol.TypeDestroyer;
import org.eclipse.basyx.vab.exception.ServerException;
import org.eclipse.basyx.vab.exception.TypeMismatchException;
import org.eclipse.basyx.vab.manager.VABConnectionManager;
import org.eclipse.basyx.vab.modelprovider.map.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a ConnectedMapProperty can be created and used correctly
 *
 * @author schnicke
 *
 */
public class TestConnectedMapProperty {
	// String constants used in this test case
	private static final String MAP_1_KEY = "test";
	private static final int MAP_1_VAL = 123;
	private static final String MAP_2_KEY = "test2";
	private static final String MAP_2_VAL = "value";

	IMapProperty prop;

	@Before
	public void build() {
		// Create and fill map
		Map<String, Object> map = new HashMap<>();
		map.put(MAP_1_KEY, MAP_1_VAL);
		map.put(MAP_2_KEY, MAP_2_VAL);

		// Create PropertySingleValued containing the map
		SingleProperty propertyMeta = new SingleProperty(map);
		Map<String, Object> destroyType = TypeDestroyer.destroyType(propertyMeta);
		// Create dummy connection manager containing the
		// created PropertySingleValued map
		VABConnectionManager manager = new VABConnectionManagerStub(
				new SinglePropertyProvider(new VABHashmapProvider(destroyType)));
		// Create ConnectedMapProperty
		prop = new ConnectedMapProperty(manager.connectToVABElement(""));
	}
	/**
	 * Tests getting a value of the contained map
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetValue() throws Exception {
		// Check contained values
		assertEquals(MAP_1_VAL, prop.getValue(MAP_1_KEY));
		assertEquals(MAP_2_VAL, prop.getValue(MAP_2_KEY));
	}

	/**
	 * Tests adding a value to the contained map
	 * 
	 * @throws ServerException
	 * @throws TypeMismatchException
	 */
	@Test
	public void testPut() throws ServerException, TypeMismatchException {
		// Put new value
		prop.put("test3", 5);

		// Check that new value is contained in map
		assertEquals(5, prop.getValue("test3"));
	}

	/**
	 * Tests overwriting the complete contained map
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSet() throws Exception {
		// Set the contained map to {"success": 10 }
		HashMap<String, Object> map = new HashMap<>();
		map.put("success", 10);
		prop.set(map);

		// Check number of elements
		assertEquals(1, (int) prop.getEntryCount());

		// Check that new value is contained in map
		assertEquals(10, prop.getValue("success"));
	}

	/**
	 * Tests getting keys of the contained map
	 * 
	 * @throws ServerException
	 * @throws TypeMismatchException
	 */
	@Test
	public void testGetKeys() throws ServerException, TypeMismatchException {
		// Get keys
		Collection<String> keys = prop.getKeys();

		// Check number of keys
		assertEquals(2, keys.size());

		// Check the contained keys
		assertTrue(keys.contains(MAP_1_KEY));
		assertTrue(keys.contains(MAP_2_KEY));
	}

	/**
	 * Tests retrieving the entry count
	 * 
	 * @throws ServerException
	 * @throws TypeMismatchException
	 */
	@Test
	public void testGetEntryCount() throws ServerException, TypeMismatchException {
		assertEquals(2, (int) prop.getEntryCount());
	}

	/**
	 * Tests removing an element from the contained map
	 * 
	 * @throws ServerException
	 * @throws TypeMismatchException
	 */
	@Test
	public void testRemove() throws ServerException, TypeMismatchException {
		// Remove element
		prop.remove(MAP_1_KEY);

		// Get keys
		Collection<String> keys = prop.getKeys();

		// Check number of keys
		assertEquals(1, keys.size());

		// Check contained key
		assertTrue(keys.contains(MAP_2_KEY));
	}

}
