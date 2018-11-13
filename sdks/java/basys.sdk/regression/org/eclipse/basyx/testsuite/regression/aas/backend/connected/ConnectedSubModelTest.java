package org.eclipse.basyx.testsuite.regression.aas.backend.connected;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.ISingleProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.backend.connected.ConnectedSubModel;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel_;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.atomicdataproperty.PropertySingleValued;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.operation.Operation;
import org.eclipse.basyx.testsuite.support.vab.stub.VABConnectionManagerStub;
import org.eclipse.basyx.vab.provider.hashmap.VABHashmapProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author schnicke
 *
 */
public class ConnectedSubModelTest {

	private final static String OP = "add";
	private final static String PROP = "prop1";
	private final static String ID = "TestId";

	ISubModel submodel;

	@Before
	public void build() {
		MetaModelElementFactory factory = new MetaModelElementFactory();
		PropertySingleValued propertyMeta = factory.create(new PropertySingleValued(), 100);
		propertyMeta.setId(PROP);

		Operation op = factory.createOperation(new Operation(), (Function<Object[], Object>) (obj) -> {
			return (int) obj[0] + (int) obj[1];
		});
		op.setId(OP);

		SubModel_ sm = factory.create(new SubModel_(), Collections.singletonList(propertyMeta),
				Collections.singletonList(op));

		sm.put("idShort", ID);

		submodel = new ConnectedSubModel("",
				new VABConnectionManagerStub(new VABHashmapProvider(sm)).connectToVABElement(""));
	}

	@Test
	public void getIdTest() {
		assertEquals(submodel.getId(), ID);
	}

	@Test
	public void propertiesTest() throws Exception {
		Map<String, IProperty> props = submodel.getProperties();
		assertEquals(1, props.size());

		ISingleProperty prop = (ISingleProperty) props.get(PROP);
		assertEquals(100, prop.get());
	}

	@Test
	public void operationsTest() throws Exception {
		Map<String, IOperation> ops = submodel.getOperations();
		assertEquals(1, ops.size());

		IOperation op = ops.get(OP);
		assertEquals(5, op.invoke(2, 3));
	}

}
