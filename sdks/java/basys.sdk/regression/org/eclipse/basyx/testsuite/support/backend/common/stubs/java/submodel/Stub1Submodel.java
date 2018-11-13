package org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel_;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.Property;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.atomicdataproperty.PropertySingleValued;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.operation.Operation;

/**
 * Implement a submodel stub
 * 
 * @author kuhn
 *
 */
public class Stub1Submodel extends SubModel_ {
	private static final long serialVersionUID = 1L;

	/**
	 * Sub model operation
	 */
	public int sum(int a, int b) {
		return a + b;
	}

	/**
	 * Constructor
	 */
	public Stub1Submodel() {
		// Initialize this submodel
		this.setId("statusSM");

		// Initialize dummy AAS

		// Create and add properties

		MetaModelElementFactory factory = new MetaModelElementFactory();

		PropertySingleValued sampleProperty1 = factory.create(new PropertySingleValued(), 2);
		sampleProperty1.setId("sampleProperty1");

		PropertySingleValued sampleProperty2 = factory.create(new PropertySingleValued(), 3);
		sampleProperty2.setId("sampleProperty2");

		NestedPropertyType sampleProperty3 = new NestedPropertyType("sampleProperty3");

		List<Integer> integerList = new ArrayList<>();
		integerList.add(42); // add 42 to linkedList
		integerList.add(43); // add 42 to linkedList
		integerList.add(44); // add 42 to linkedList
		integerList.add(45); // add 42 to linkedList

		PropertySingleValued sampleProperty4 = factory.create(new PropertySingleValued(), integerList);
		sampleProperty4.setId("sampleProperty4");

		Map<String, Integer> map = new HashMap<>();
		map.put("magic", 42);
		PropertySingleValued sampleProperty6 = factory.create(new PropertySingleValued(), map);
		sampleProperty6.setId("sampleProperty6");

		// Create and add operation
		Operation op = factory.createOperation(new Operation(), (Function<Object[], Object>) (obj) -> {
			return sum((int) obj[0], (int) obj[1]);
		});

		List<Property> props = new ArrayList<>();
		props.add(sampleProperty1);
		props.add(sampleProperty2);
		props.add(sampleProperty3);
		props.add(sampleProperty4);
		props.add(sampleProperty6);

		putAll(factory.create(this, props, Collections.singletonList(op)));
	}

	/**
	 * Constructor
	 */
	public Stub1Submodel(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();

		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
