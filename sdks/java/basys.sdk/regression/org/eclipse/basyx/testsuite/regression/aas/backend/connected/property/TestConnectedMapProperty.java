package org.eclipse.basyx.testsuite.regression.aas.backend.connected.property;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.aas.api.exception.TypeMismatchException;
import org.eclipse.basyx.aas.api.resources.IMapProperty;
import org.eclipse.basyx.aas.backend.connected.property.ConnectedMapProperty;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.atomicdataproperty.PropertySingleValued;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * @author schnicke
 *
 */
public class TestConnectedMapProperty {
	IMapProperty prop;

	@Before
	public void build() {
		MetaModelElementFactory factory = new MetaModelElementFactory();
		Map<String, Object> map = new HashMap<>();
		map.put("test", 123);
		map.put("test2", "value");
		PropertySingleValued propertyMeta = factory.create(new PropertySingleValued(), map);
		prop = new ConnectedMapProperty("",
				new VABConnectionManagerStub(new VABHashmapProvider(propertyMeta)).connectToVABElement(""));
	}

	@Test
	public void testGetValue() throws Exception {
		assertEquals(123, prop.getValue("test"));
		assertEquals("value", prop.getValue("test2"));
	}

	@Test
	public void testPut() throws ServerException, TypeMismatchException {
		prop.put("test3", 5);
		assertEquals(5, prop.getValue("test3"));
	}

	@Test
	public void testSet() throws Exception {
		prop.set(Collections.singletonMap("success", 10));
		assertEquals(1, (int) prop.getEntryCount());
		assertEquals(10, prop.getValue("success"));
	}

	@Test
	public void testGetKeys() throws ServerException, TypeMismatchException {
		Collection<String> keys = prop.getKeys();
		assertEquals(2, keys.size());
		assertTrue(keys.contains("test"));
		assertTrue(keys.contains("test2"));
	}

	@Test
	public void testGetEntryCount() throws ServerException, TypeMismatchException {
		assertEquals(2, (int) prop.getEntryCount());
	}

	@Test
	public void testRemove() throws ServerException, TypeMismatchException {
		prop.remove("test");
		Collection<String> keys = prop.getKeys();
		assertEquals(1, keys.size());
		assertTrue(keys.contains("test2"));
	}

}
