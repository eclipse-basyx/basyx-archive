/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
* 
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/

* 
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/

package org.eclipse.basyx.testsuite.regression.aas.metamodel.map.parts;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.map.parts.AssetInformation;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.IdentifierKeyValuePair;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.File;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests constructor, setter and getter of {@link AssetInformation} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestAssetInformation {
	private static final AssetKind KIND = AssetKind.INSTANCE; 
	private static final Reference REFERENCE = new Reference(new Identifier(IdentifierType.CUSTOM, "testValue"), KeyElements.ASSET);
	private AssetInformation assetInformation;
	
	@Before
	public void buildAssetInformation() {
		assetInformation = new AssetInformation(KIND);
	}
	
	@Test
	public void testConstructor() {
		assertEquals(KIND, assetInformation.getAssetKind());
	}

	@Test
	public void testSetAssetKind() {
		AssetKind newKind = AssetKind.TYPE;
		assetInformation.setAssetKind(newKind);
		assertEquals(newKind, assetInformation.getAssetKind());
	}
	
	@Test 
	public void testGlobalAssetId() {
		assetInformation.setGlobalAssetId(REFERENCE);
		assertEquals(REFERENCE, assetInformation.getGlobalAssetId());
	}
	
	@Test 
	public void testSpecificAssetId() {
		IdentifierKeyValuePair pair = new IdentifierKeyValuePair("key");
		assetInformation.setSpecificAssetId(Collections.singletonList(pair));
		assertEquals(Collections.singletonList(pair), assetInformation.getSpecificAssetId());
	}
	
	@Test
	public void testBillOfMaterial() {
		assetInformation.setBillOfMaterial(Collections.singletonList(REFERENCE));
		assertEquals(Collections.singletonList(REFERENCE), assetInformation.getBillOfMaterial());
	}
	
	@Test
	public void testDefaultThumbnail() {
		File file = new File("images/png");
		file.setIdShort("file01");
		assetInformation.setDefaultThumbnail(file);
		assertEquals(file, assetInformation.getDefaultThumbnail());
	}
}
