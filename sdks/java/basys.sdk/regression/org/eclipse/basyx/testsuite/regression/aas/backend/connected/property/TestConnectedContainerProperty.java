package org.eclipse.basyx.testsuite.regression.aas.backend.connected.property;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.ICollectionProperty;
import org.eclipse.basyx.aas.api.resources.IContainerProperty;
import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.backend.connected.property.ConnectedContainerProperty;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.ComplexDataProperty;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.atomicdataproperty.PropertySingleValued;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.operation.Operation;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;

public class TestConnectedContainerProperty {
	IContainerProperty prop;
	Collection<Integer> collection;

	public int add(int a, int b) {
		return a + b;
	}

	@Before
	public void build() {
		MetaModelElementFactory factory = new MetaModelElementFactory();
		collection = new ArrayList<>();
		collection.add(1);
		collection.add(2);
		PropertySingleValued propertyMeta = factory.create(new PropertySingleValued(), collection);
		propertyMeta.setId("coll");
		Operation operation = factory.createOperation(new Operation(), (arr) -> {
			return add((int) arr[0], (int) arr[1]);
		});
		operation.setId("sum");

		ComplexDataProperty complex = factory.createContainer(new ComplexDataProperty(),
				Collections.singletonList(propertyMeta), Collections.singletonList(operation));
		complex.setId("propComplex");
		prop = new ConnectedContainerProperty("",
				new VABConnectionManagerStub(new VABHashmapProvider(complex)).connectToVABElement(""));
	}

	@Test
	public void testProperty() {
		Map<String, IProperty> props = prop.getProperties();
		assertEquals(1, props.size());
		ICollectionProperty collProp = (ICollectionProperty) props.get("coll");
		assertEquals(collection, collProp.getElements());
	}

	@Test
	public void testOperation() throws Exception {
		Map<String, IOperation> ops = prop.getOperations();
		assertEquals(1, ops.size());
		IOperation sum = ops.get("sum");
		assertEquals(5, sum.invoke(2, 3));
	}
}
