package org.eclipse.basyx.testsuite.regression.aas.backend.connected.property;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.backend.connected.property.ConnectedSingleProperty;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests if a ConnectedSingleProperty can be created and used correctly
 * 
 * @author schnicke
 *
 */
public class TestConnectedSingleProperty {

	ISingleProperty prop;
	private static final int VALUE = 10;

	@Before
	public void build() {
		MetaModelElementFactory factory = new MetaModelElementFactory();

		// Create PropertySingleValued containing the simple value
		Property propertyMeta = factory.create(new Property(), VALUE);
		prop = new ConnectedSingleProperty("", new VABConnectionManagerStub(new VABHashmapProvider(propertyMeta)).connectToVABElement(""));
	}

	/**
	 * Tests getting the value
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGet() throws Exception {
		int val = (int) prop.get();
		assertEquals(VALUE, val);
	}

	/**
	 * Tests setting the value
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSet() throws Exception {
		prop.set(123);
		int val = (int) prop.get();
		assertEquals(123, val);
	}
}
