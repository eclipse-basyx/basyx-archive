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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.basyx.submodel.metamodel.map.qualifier.Extension;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasExtensions;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests constructor, setter and getter of {@link HasExtensions} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestHasExtension {
	public static List<Extension> extensions = new ArrayList<Extension>();
	
	private HasExtensions hasExtensions;
	
	@Before
	public void build() {
		extensions.add(new Extension("testName"));
		hasExtensions = new HasExtensions(extensions);
	}
	
	@Test
	public void testConstructor() {
		assertEquals(extensions, hasExtensions.getExtensions());
	}
	
	@Test
	public void testSetExtensions() {
		Extension newExtension = new Extension("anotherExt");
		extensions.add(newExtension);
		hasExtensions.setExtension(extensions);
		assertEquals(extensions, hasExtensions.getExtensions());
	}
}
