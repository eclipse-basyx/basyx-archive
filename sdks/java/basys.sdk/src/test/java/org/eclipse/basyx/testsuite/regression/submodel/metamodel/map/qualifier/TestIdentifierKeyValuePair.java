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
import org.eclipse.basyx.submodel.metamodel.map.qualifier.IdentifierKeyValuePair;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests constructor, setter and getter of {@link IdentifierKeyValuePair} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestIdentifierKeyValuePair {
	public static final String KEY = "testKey";
	
	private IdentifierKeyValuePair pair;
	
	@Before
	public void build() {
		pair = new IdentifierKeyValuePair(KEY);
	}
	
	@Test
	public void testConstructor() {
		assertEquals(KEY, pair.getKey());
	}
	
	@Test
	public void testSetKey() {
		String key = "newKey";
		pair.setKey(key);
		assertEquals(key, pair.getKey());
	}
	
	@Test
	public void testSetValue() {
		String value = "testValue";
		pair.setValue(value);
		assertEquals(value, pair.getValue());
	}
	
	@Test
	public void testSetExternalSubjectId() {
		Reference ref = new Reference(new Key(KeyElements.ASSETADMINISTRATIONSHELL, "testValue", IdentifierType.IRDI));
		pair.setExternalSubjectId(ref);
		assertEquals(ref, pair.getExternalSubjectId());
	}
}
