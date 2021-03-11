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

import java.util.Collection;
import java.util.Collections;

import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IEmbeddedDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.dataspecification.EmbeddedDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.AdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests constructor, setter and getter of {@link Asset} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestAsset {
	private static final Reference REFERENCE = new Reference(new Identifier(IdentifierType.CUSTOM, "testValue"), KeyElements.ASSET);
	private static final String ID = "testId";
	private static final Identifier IDENTIFIER = new Identifier(IdentifierType.CUSTOM, ID);
	private Asset asset;
	
	@Before
	public void buildAsset() {
		asset = new Asset(ID, IDENTIFIER);
	}
	
	@Test
	public void testConstructor() {
		assertEquals(IDENTIFIER, asset.getIdentification());
	}
	
	@Test
	public void testSetDataSpecificationReferences() {
		Collection<IReference> refs = Collections.singleton(REFERENCE);
		asset.setDataSpecificationReferences(refs);
		assertEquals(refs, asset.getDataSpecificationReferences());
	}
	
	@Test
	public void testSetEmbeddedDataSpecifications() {
		EmbeddedDataSpecification embeddedDataSpecification = new EmbeddedDataSpecification();
		Collection<IEmbeddedDataSpecification> specifications = Collections.singleton(embeddedDataSpecification);
		asset.setEmbeddedDataSpecifications(specifications);
		assertEquals(specifications, asset.getEmbeddedDataSpecifications());
	}
	
	@Test
	public void testSetAdministration() {
		AdministrativeInformation information = new AdministrativeInformation("1.0", "5");
		asset.setAdministration(information);
		assertEquals(information, asset.getAdministration());
	} 
	
	@Test
	public void testSetIdentification() {
		IdentifierType idType = IdentifierType.IRI;
		String newIdString = "newId";
		asset.setIdentification(idType, newIdString);
		assertEquals(new Identifier(idType, newIdString), asset.getIdentification());
	}

	@Test
	public void testSetIdShort() {
		String newIdString = "newId";
		asset.setIdShort(newIdString);
		assertEquals(newIdString, asset.getIdShort());
	}
	
	@Test
	public void testSetCategory() {
		String newCategoryString = "newCategory";
		asset.setCategory(newCategoryString);
		assertEquals(newCategoryString, asset.getCategory());
	}
	
	@Test
	public void testSetDescription() {
		LangStrings newDescriptionString = new LangStrings("DE", "newTest");
		asset.setDescription(newDescriptionString);
		assertEquals(newDescriptionString, asset.getDescription());
	}
}
