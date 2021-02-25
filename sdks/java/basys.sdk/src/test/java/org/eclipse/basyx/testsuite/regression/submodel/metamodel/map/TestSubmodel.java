/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.testsuite.regression.submodel.metamodel.TestSubmodelSuite;
import org.junit.Before;
import org.junit.Test;

/**
 * Ensures correct behavior of {@link SubModel}
 * 
 * @author haque
 *
 */
public class TestSubmodel extends TestSubmodelSuite {

	ISubModel submodel;

	@Before
	public void build() {
		submodel = getReferenceSubmodel();
	}

	@Test
	public void testParentAddSubmodelElement() {
		Property prop = new Property("propIdShort", PropertyValueTypeDef.String);
		IIdentifier identifier = new ModelUrn("testId");
		SubModel submodel = new SubModel("smIdShort", identifier);
		submodel.addSubModelElement(prop);
		
		// Create expected parent of the element for assertion
		Reference expectedParent = new Reference(new Key(KeyElements.SUBMODEL, true, identifier.getId(), identifier.getIdType()));
		assertEquals(expectedParent, prop.getParent());
	} 

	/**
	 * Tests if a SubModel containing a list for SUBMODELELEMENT is correctly
	 * handled by the facading submodel. This is necessary because the submodel
	 * serialization does specify SUBMODELELEMENT as list
	 */
	@Test
	public void testCreateAsFacadePropertyList() {
		// Create test property
		String propId = "testProp";
		
		Property expected = new Property(5);
		expected.setIdShort(propId);

		// Create test submodel and force key SUBMODELELEMENT to contain a list
		String id = "testIdShort";
		SubModel sm = new SubModel(id, new Identifier(IdentifierType.IRDI, id));
		sm.put(SubModel.SUBMODELELEMENT, Collections.singleton(expected));

		// Check if the facade converts the SUBMODELELEMENT value correctly
		SubModel facade = SubModel.createAsFacade(sm);
		assertTrue(facade.get(SubModel.SUBMODELELEMENT) instanceof Map<?, ?>);
		assertEquals(expected, facade.getSubmodelElements().get(propId));
	}

	@Override
	protected ISubModel getSubmodel() {
		return submodel;
	}
}
