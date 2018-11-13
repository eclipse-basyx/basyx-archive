package org.eclipse.basyx.testsuite.regression.aas.backend.connected.property;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.backend.connected.property.ConnectedSingleProperty;
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
public class TestConnectedSingleProperty {

	ISingleProperty prop;

	@Before
	public void build() {
		MetaModelElementFactory factory = new MetaModelElementFactory();

		PropertySingleValued propertyMeta = factory.create(new PropertySingleValued(), 10);
		prop = new ConnectedSingleProperty("",
				new VABConnectionManagerStub(new VABHashmapProvider(propertyMeta)).connectToVABElement(""));
	}

	@Test
	public void testGet() throws Exception {
		int val = (int) prop.get();
		assertEquals(10, val);
	}

	@Test
	public void testSet() throws Exception {
		prop.set(123);
		int val = (int) prop.get();
		assertEquals(123, val);
	}
}
