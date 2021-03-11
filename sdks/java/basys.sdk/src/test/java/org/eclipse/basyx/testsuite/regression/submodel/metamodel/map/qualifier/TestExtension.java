/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
* 
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/

* 
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/

package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.qualifier;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Extension;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetype.ValueType;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests constructor, setter and getter of {@link Extension} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestExtension {
	public static final String VALUE = "testValue";
	
	private Extension extension;
	
	@Before
	public void build() {
		extension = new Extension(VALUE);
	}
	
	@Test
	public void testConstructor() {
		assertEquals(VALUE, extension.getName());
		assertEquals(ValueType.String, extension.getValueType());
	}
	
	@Test
	public void testSetName() {
		String name = "newName";
		extension.setName(name);
		assertEquals(name, extension.getName());
	}
	
	@Test
	public void testSetValue() {
		extension.setValue(VALUE);
		assertEquals(VALUE, extension.getValue());
	}
	
	@Test
	public void testSetRefersTo() {
		Reference ref = new Reference(new Key(KeyElements.ASSETADMINISTRATIONSHELL, "testValue", IdentifierType.IRDI));
		extension.setRefersTo(ref);
		assertEquals(ref, extension.getRefersTo());
	}
	
	@Test
	public void testSetValueType() {
		ValueType type = ValueType.AnySimpleType;
		extension.setValueType(type);
		assertEquals(type, extension.getValueType());
	}
}
