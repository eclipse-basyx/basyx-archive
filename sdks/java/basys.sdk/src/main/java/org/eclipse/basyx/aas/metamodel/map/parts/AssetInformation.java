/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
* 
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/

* 
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/

package org.eclipse.basyx.aas.metamodel.map.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.api.parts.asset.AssetKind;
import org.eclipse.basyx.aas.metamodel.exception.MetamodelConstructionException;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IFile;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.IdentifierKeyValuePair;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.File;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * In AssetInformation identifying meta data of the asset that is represented by an AAS is defined.
 * The asset may either represent an asset type or an asset instance.
 * The asset has a globally unique identifier plus – if needed – additional domain specific (proprietary) identifiers. However, to support the corner case of very first phase of lifecycle
 * where a stabilized/constant global asset identifier does not already exist, the corresponding attribute “globalAssetId” is optional.
 * As defined in the DAAS document V3
 * 
 * @author haque
 *
 */
public class AssetInformation extends VABModelMap<Object> {
	public static String ASSETKIND = "assetKind";
	public static String GLOBALASSETID = "globalAssetId";
	public static final String SPECIFICASSETID = "specificAssetId";
	public static final String BILLOFMATERIAL = "billOfMaterial";
	public static final String DEFAULTTHUMBNAIL = "defaultThumbnail";
	
	public AssetInformation() {
		setAssetKind(AssetKind.INSTANCE);
	}
	
	/**
	 * Constructor with mandatory attribute
	 * @param kind
	 */
	public AssetInformation(AssetKind kind) {
		setAssetKind(kind);
	}
	
	/**
	 * Creates a AssetInformation object from a map
	 * 
	 * @param obj
	 *            a AssetInformation object as raw map
	 * @return a AssetInformation object, that behaves like a facade for the given map
	 */
	public static AssetInformation createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}
		
		if (!isValid(map)) {
			throw new MetamodelConstructionException(AssetInformation.class, map);
		}

		AssetInformation ret = new AssetInformation();
		ret.setMap(map);
		return ret;
	}
	
	/**
	 * Check whether all mandatory elements for the metamodel
	 * exist in a map
	 * @return true/false
	 */
	public static boolean isValid(Map<String, Object> map) {
		return map != null && createAsFacadeNonStrict(map).getAssetKind() != null;
	}
	
	/**
	 * Creates a AssetInformation object from a map without validation
	 * 
	 * @param obj
	 *            a AssetInformation object as raw map
	 * @return a AssetInformation object, that behaves like a facade for the given map
	 */
	private static AssetInformation createAsFacadeNonStrict(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		AssetInformation ret = new AssetInformation();
		ret.setMap(map);
		return ret;
	}
	
	/**
	 * sets asset kind
	 * Denotes whether the Asset is of kind “Type” or “Instance”.
	 * @param kind {@link AssetKind}
	 */
	public void setAssetKind(AssetKind kind) {
		put(ASSETKIND, kind.toString());
	}
	
	/**
	 * gets asset kind
	 * Denotes whether the Asset is of kind “Type” or “Instance”.
	 * @return {@link AssetKind}
	 */
	public AssetKind getAssetKind() {
		return AssetKind.fromString((String) get(ASSETKIND));
	}
	
	/**
	 * sets global asset id
	 * Reference to either an Asset object or a global reference to the asset the AAS is representing.
	 * This attribute is required as soon as the AAS is exchanged via partners in the life cycle of the asset. In a first phase of the life cycle the asset might not yet have a global id but already an internal identifier. The internal identifier would be modelled via “specificAssetId”.
	 * Constraint AASd-023: AssetInformation/globalAssetId either is a reference to an Asset object or a global reference.
	 * @param assetId {@link Reference}
	 */
	public void setGlobalAssetId(Reference assetId) {
		put(GLOBALASSETID, assetId);
	}
	
	/**
	 * gets global asset id
	 * Reference to either an Asset object or a global reference to the asset the AAS is representing.
	 * This attribute is required as soon as the AAS is exchanged via partners in the life cycle of the asset. In a first phase of the life cycle the asset might not yet have a global id but already an internal identifier. The internal identifier would be modelled via “specificAssetId”.
	 * Constraint AASd-023: AssetInformation/globalAssetId either is a reference to an Asset object or a global reference.
	 * @return {@link IReference}
	 */
	@SuppressWarnings("unchecked")
	public IReference getGlobalAssetId() {
		return Reference.createAsFacade((Map<String, Object>) get(GLOBALASSETID));
	}
	
	/**
	 * sets additional domain specific specific, typically proprietary Identifier for the asset like e.g. serial number etc.
	 * @param assetIds {@link List<IdentifierKeyValuePair>}
	 */
	public void setSpecificAssetId(List<IdentifierKeyValuePair> assetIds) {
		put(SPECIFICASSETID, assetIds);
	}
	
	/**
	 * gets additional domain specific specific, typically proprietary Identifier for the asset like e.g. serial number etc.
	 * @return {@link List<IdentifierKeyValuePair>}
	 */
	@SuppressWarnings("unchecked")
	public List<IdentifierKeyValuePair> getSpecificAssetId() {
		List<IdentifierKeyValuePair> ret = new ArrayList<IdentifierKeyValuePair>();
		List<Map<String, Object>> values = (List<Map<String, Object>>) get(SPECIFICASSETID);
		
		if (values != null && values.size() > 0) {
			for (Map<String, Object> value : values) {
				ret.add(IdentifierKeyValuePair.createAsFacade(value));
			}
		}
		
		return ret;
	}

	/**
	 * sets a reference to a Submodel that defines the bill of material of the asset represented by the AAS.
	 * This submodel contains a set of entities describing the material used to compose the composite I4.0 Component.
	 * @param reference {@link List<Reference>}
	 */
	public void setBillOfMaterial(List<Reference> reference) {
		put(BILLOFMATERIAL, reference);
	}
	
	/**
	 * gets a reference to a Submodel that defines the bill of material of the asset represented by the AAS.
	 * This submodel contains a set of entities describing the material used to compose the composite I4.0 Component.
	 * @return {@link IReference}
	 */
	@SuppressWarnings("unchecked")
	public List<IReference> getBillOfMaterial() {
		List<IReference> ret = new ArrayList<IReference>();
		List<Map<String, Object>> values = (List<Map<String, Object>>) get(BILLOFMATERIAL);
		
		if (values != null && values.size() > 0) {
			for (Map<String, Object> value : values) {
				ret.add(Reference.createAsFacade(value));
			}
		}
		
		return ret;
	}
	
	/**
	 * sets thumbnail of the asset represented by the asset administration shell. Used as default.
	 * @param file {@link File}
	 */
	public void setDefaultThumbnail(File file) {
		put(DEFAULTTHUMBNAIL, file);
	}
	
	/**
	 * gets thumbnail of the asset represented by the asset administration shell. Used as default.
	 * @return {@link IFile}
	 */
	@SuppressWarnings("unchecked")
	public IFile getDefaultThumbnail() {
		return File.createAsFacade((Map<String, Object>) get(DEFAULTTHUMBNAIL));
	}
}
