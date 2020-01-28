package org.eclipse.basyx.testsuite.regression.submodel.model.connected;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.ISingleProperty;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.property.ConnectedSingleProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.SinglePropertyProvider;
import org.eclipse.basyx.testsuite.regression.vab.manager.VABConnectionManagerStub;
import org.eclipse.basyx.testsuite.regression.vab.protocol.TypeDestroyer;
import org.eclipse.basyx.vab.modelprovider.map.VABMapProvider;
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
		// Create PropertySingleValued containing the simple value
		Property propertyMeta = new Property(VALUE);
		Map<String, Object> destroyType = TypeDestroyer.destroyType(propertyMeta);
		prop = new ConnectedSingleProperty(new VABConnectionManagerStub(new SinglePropertyProvider(new VABMapProvider(destroyType))).connectToVABElement(""));
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
