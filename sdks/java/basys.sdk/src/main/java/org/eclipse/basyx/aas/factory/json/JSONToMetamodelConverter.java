/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.aas.factory.json;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.parts.ConceptDescription;
import org.eclipse.basyx.vab.coder.json.serialization.DefaultTypeFactory;
import org.eclipse.basyx.vab.coder.json.serialization.GSONTools;

/**
 * This class can be used to parse JSON to Metamodel Objects
 * 
 * @author conradi
 *
 */
public class JSONToMetamodelConverter {
	
	private Map<String, Object> root;

	@SuppressWarnings("unchecked")
	public JSONToMetamodelConverter(String jsonContent) {
		root = (Map<String, Object>) new GSONTools(new DefaultTypeFactory()).deserialize(jsonContent);
	}
	
	/**
	 * Parses the AASs from the JSON
	 * 
	 * @return the AASs parsed from the JSON
	 */
	@SuppressWarnings("unchecked")
	public List<AssetAdministrationShell> parseAAS() {
		return ((List<Object>) root.get(MetamodelToJSONConverter.ASSET_ADMINISTRATION_SHELLS)).stream()
				.map(this::parseAssetAdministrationShell).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	private AssetAdministrationShell parseAssetAdministrationShell(Object mapObject) {
		AssetAdministrationShell parsedAAS = AssetAdministrationShell.createAsFacade((Map<String, Object>) mapObject);
		return parsedAAS;
	}
	
	/**
	 * Parses the Submodels from the JSON
	 * 
	 * @return the Submodels parsed from the JSON
	 */
	@SuppressWarnings("unchecked")
	public List<Submodel> parseSubmodels() {
		return ((List<Object>) root.get(MetamodelToJSONConverter.SUBMODELS)).stream()
				.map(i -> Submodel.createAsFacade((Map<String, Object>) i)).collect(Collectors.toList());
	}
	
	/**
	 * Parses the Assets from the JSON
	 * 
	 * @return the Assets parsed from the JSON
	 */
	@SuppressWarnings("unchecked")
	public List<Asset> parseAssets() {
		return ((List<Object>) root.get(MetamodelToJSONConverter.ASSETS)).stream()
				.map(i -> Asset.createAsFacade((Map<String, Object>) i)).collect(Collectors.toList());
	}

	/**
	 * Parses the ConceptDescriptions from the JSON
	 * 
	 * @return the ConceptDescriptions parsed from the JSON
	 */
	@SuppressWarnings("unchecked")
	public List<ConceptDescription> parseConceptDescriptions() {
		return ((List<Object>) root.get(MetamodelToJSONConverter.CONCEPT_DESCRIPTIONS)).stream()
				.map(i -> ConceptDescription.createAsFacade((Map<String, Object>) i)).collect(Collectors.toList());
	}
}
