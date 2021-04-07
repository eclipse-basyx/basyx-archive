/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.aas.metamodel.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.api.parts.IView;
import org.eclipse.basyx.aas.metamodel.api.security.ISecurity;
import org.eclipse.basyx.aas.metamodel.exception.MetamodelConstructionException;
import org.eclipse.basyx.aas.metamodel.map.parts.AssetInformation;
import org.eclipse.basyx.aas.metamodel.map.parts.View;
import org.eclipse.basyx.aas.metamodel.map.security.Security;
import org.eclipse.basyx.submodel.metamodel.api.ISubmodel;
import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IEmbeddedDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.AdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.reference.ReferenceHelper;
import org.eclipse.basyx.vab.exception.FeatureNotImplementedException;
import org.eclipse.basyx.vab.model.VABModelMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AssetAdministrationShell class <br>
 * Does not implement IAssetAdministrationShell since there are only references
 * stored in this map
 * 
 * @author kuhn, schnicke
 *
 */

public class AssetAdministrationShell extends VABModelMap<Object> implements IAssetAdministrationShell {
	private static Logger logger = LoggerFactory.getLogger(AssetAdministrationShell.class);

	public static final String SECURITY = "security";
	public static final String DERIVEDFROM = "derivedFrom";
	public static final String ASSETINFORMATION = "assetInformation";
	public static final String ASSETREF = "assetRef"; // Currently not standard conforming
	public static final String SUBMODELS = "submodels"; // Used for storing keys to conform to the standard
	public static final String VIEWS = "views";
	public static final String TYPE = "type";
	public static final String ADDRESS = "address";
	public static final String MODELTYPE = "AssetAdministrationShell";

	/**
	 * Constructor
	 */
	public AssetAdministrationShell() {
		this(null, null, new AssetInformation(), new HashSet<Submodel>(), new HashSet<IView>());
	}
	
	/**
	 * Constructor accepting only mandatory attributes
	 * @param idShort
	 * @param identification
	 * @param assetInformation
	 */
	public AssetAdministrationShell(String idShort, IIdentifier identification, AssetInformation assetInformation) {
		this(null, null, assetInformation, new HashSet<Submodel>(), new HashSet<IView>());
		setIdentification(identification);
		setIdShort(idShort);
	}

	public AssetAdministrationShell(Reference derivedFrom, Security security, AssetInformation assetInformation,
			Collection<Submodel> submodels, Collection<IView> views) {
		// Add model type
		putAll(new ModelType(MODELTYPE));
		
		// Add qualifiers
		putAll(new Identifiable());
		putAll(new HasDataSpecification());

		setSubmodelReferences(new HashSet<IReference>());

		// Add attributes
		setSecurity(security);
		setDerivedFrom(derivedFrom);
		setAssetInformation(assetInformation);
		setSubmodels(submodels);

		setViews(views);
	}

	/**
	 * Creates a AssetAdministrationShell object from a map
	 * 
	 * @param map
	 *            a AssetAdministrationShell object as raw map
	 * @return a AssetAdministrationShell object, that behaves like a facade for the
	 *         given map
	 */
	public static AssetAdministrationShell createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}
		
		if (!isValid(map)) {
			throw new MetamodelConstructionException(AssetAdministrationShell.class, map);
		}
					
		if (!map.containsKey(SUBMODELS)) {
			map.put(SUBMODELS, new ArrayList<>());
		}

		AssetAdministrationShell ret = new AssetAdministrationShell();
		ret.setMap(map);
		return ret;	
	}
	
	/**
	 * Check whether all mandatory elements for the metamodel
	 * exist in a map
	 * @return true/false
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValid(Map<String, Object> map) {
		return Identifiable.isValid(map) &&
				map.containsKey(AssetAdministrationShell.ASSETINFORMATION) &&
				AssetInformation.isValid((Map<String, Object>)map.get(AssetAdministrationShell.ASSETINFORMATION));
	}

	@Override
	public IAdministrativeInformation getAdministration() {
		return Identifiable.createAsFacade(this, getKeyElement()).getAdministration();
	}

	@Override
	public IIdentifier getIdentification() {
		return Identifiable.createAsFacade(this, getKeyElement()).getIdentification();
	}

	public void setAdministration(AdministrativeInformation information) {
		Identifiable.createAsFacade(this, getKeyElement()).setAdministration(information);
	}

	public void setIdentification(IIdentifier id) {
		setIdentification(id.getIdType(), id.getId());
	}

	public void setIdentification(IdentifierType idType, String id) {
		Identifiable.createAsFacadeNonStrict(this, getKeyElement()).setIdentification(idType, id);
	}

	@Override
	public Collection<IReference> getDataSpecificationReferences() {
		return HasDataSpecification.createAsFacade(this).getDataSpecificationReferences();
	}

	public void setDataSpecificationReferences(Collection<IReference> ref) {
		HasDataSpecification.createAsFacade(this).setDataSpecificationReferences(ref);
	}

	@Override
	public Collection<IEmbeddedDataSpecification> getEmbeddedDataSpecifications() {
		return HasDataSpecification.createAsFacade(this).getEmbeddedDataSpecifications();
	}

	public void setEmbeddedDataSpecifications(Collection<IEmbeddedDataSpecification> embeddedDataSpecifications) {
		HasDataSpecification.createAsFacade(this).setEmbeddedDataSpecifications(embeddedDataSpecifications);
	}

	public void setIdShort(String id) {
		Referable.createAsFacadeNonStrict(this, getKeyElement()).setIdShort(id);
	}

	public void setSecurity(ISecurity security) {
		put(AssetAdministrationShell.SECURITY, security);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ISecurity getSecurity() {
		return Security.createAsFacade((Map<String, Object>) get(AssetAdministrationShell.SECURITY));
	}

	public void setDerivedFrom(IReference derivedFrom) {
		put(AssetAdministrationShell.DERIVEDFROM, derivedFrom);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getDerivedFrom() {
		return Reference.createAsFacade((Map<String, Object>) get(AssetAdministrationShell.DERIVEDFROM));
	}

	public void setAssetInformation(AssetInformation assetInformation) {
		put(AssetAdministrationShell.ASSETINFORMATION, assetInformation);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AssetInformation getAssetInformation() {
		return AssetInformation.createAsFacade((Map<String, Object>) get(AssetAdministrationShell.ASSETINFORMATION));
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getAssetReference() {
		return Reference.createAsFacade((Map<String, Object>) get(ASSETREF));
	}

	public void setAssetReference(Reference ref) {
		put(ASSETREF, ref);
	}

	@SuppressWarnings("unchecked")
	public void setSubmodels(Collection<Submodel> submodels) {
		// Clear submodel references and add new keys
		((Collection<Reference>) get(SUBMODELS)).clear();
		submodels.stream().forEach(this::addSubmodelReferences);
	}

	public void setViews(Collection<IView> views) {
		put(AssetAdministrationShell.VIEWS, views);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<IView> getViews() {
		Collection<Map<String, Object>> coll = (Collection<Map<String, Object>>) get(AssetAdministrationShell.VIEWS);
		return coll.stream().map(View::createAsFacade).collect(Collectors.toSet());
	}

	@Override
	public Map<String, ISubmodel> getSubmodels() {
		throw new RuntimeException("getSubmodels on local copy is not supported");
	}

	@Override
	public String getIdShort() {
		return Referable.createAsFacade(this, getKeyElement()).getIdShort();
	}

	@Override
	public String getCategory() {
		return Referable.createAsFacade(this, getKeyElement()).getCategory();
	}

	@Override
	public LangStrings getDescription() {
		return Referable.createAsFacade(this, getKeyElement()).getDescription();
	}

	public void setCategory(String category) {
		Referable.createAsFacade(this, getKeyElement()).setCategory(category);
	}

	public void setDescription(LangStrings description) {
		Referable.createAsFacade(this, getKeyElement()).setDescription(description);
	}

	@Override
	public void addSubmodel(Submodel submodel) {
		logger.trace("adding Submodel", submodel.getIdentification().getId());
		addSubmodelReferences(submodel);
	}


	@Override
	public void removeSubmodel(IIdentifier id) {
		// Currently not implemented since future of Submodel References in AAS is not clear
		throw new FeatureNotImplementedException();
	}

	@Override
	public Collection<IReference> getSubmodelReferences() {
		return ReferenceHelper.transform(get(SUBMODELS));
	}

	public void setSubmodelReferences(Collection<IReference> references) {
		put(SUBMODELS, references);
	}

	@SuppressWarnings("unchecked")
	public void addSubmodelReference(IReference reference) {
		Collection<Object> smReferences = (Collection<Object>) get(SUBMODELS);
		smReferences.add(reference);
	}

	private void addSubmodelReferences(Submodel submodel) {
		addSubmodelReference(submodel.getReference());
	}

	private KeyElements getKeyElement() {
		return KeyElements.ASSETADMINISTRATIONSHELL;
	}
	
	@Override
	public IReference getReference() {
		return Identifiable.createAsFacade(this, getKeyElement()).getReference();
	}

	@Override
	public ISubmodel getSubmodel(IIdentifier id) {
		throw new RuntimeException("getSubmodel on local copy is not supported");
	}

}
