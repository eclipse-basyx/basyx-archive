package org.eclipse.basyx.aas.metamodel.hashmap.aas.parts;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.parts.IAsset;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.metamodel.facades.AssetFacade;
import org.eclipse.basyx.aas.metamodel.facades.HasDataSpecificationFacade;
import org.eclipse.basyx.aas.metamodel.facades.HasKindFacade;
import org.eclipse.basyx.aas.metamodel.facades.IdentifiableFacade;
import org.eclipse.basyx.aas.metamodel.facades.ReferableFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind.HasKind;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

/**
 * Asset class as described in DAAS document<br/>
 * An Asset describes meta data of an asset that is represented by an AAS. <br/>
 * The asset may either represent an asset type or an asset instance.<br/>
 * The asset has a globally unique identifier plus � if needed � additional
 * domain specific (proprietary) identifiers.
 * 
 * @author kuhn, elsheikh, schnicke
 *
 */
public class Asset extends HashMap<String, Object> implements IAsset {
	
	public static String ASSETIDENTIFICATIONMODEL="assetIdentificationModel";

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public Asset() {
		// Add qualifiers
		putAll(new HasDataSpecification());
		putAll(new HasKind());
		putAll(new Identifiable());

		// Default values
		put(ASSETIDENTIFICATIONMODEL, null);
	}

	/**
	 * 
	 * @param submodel A reference to a Submodel that defines the handling of
	 *                 additional domain specific (proprietary) Identifiers for the
	 *                 asset like e.g. serial number etc.
	 */
	public Asset(Reference submodel) {
		this();
		put(ASSETIDENTIFICATIONMODEL, submodel);	
	}
	

	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(this).getDataSpecificationReferences();
	}
	
	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new HasDataSpecificationFacade(this).setDataSpecificationReferences(ref);
		
	}

	@Override
	public String getHasKindReference() {
		return new HasKindFacade(this).getHasKindReference();
	}
	
	@Override
	public void setHasKindReference(String kind) {
		new HasKindFacade(this).setHasKindReference(kind);
		
	}

	@Override
	public IAdministrativeInformation getAdministration() {
	return new IdentifiableFacade(this).getAdministration();
	}

	@Override
	public IIdentifier getIdentification() {
		return new IdentifiableFacade(this).getIdentification();
	}

	@Override
	public void setAdministration(String version, String revision) {
		new IdentifiableFacade(this).setAdministration(version, revision);
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		new IdentifiableFacade(this).setIdentification(idType, id);
		
	}

	@Override
	public IReference getAssetIdentificationModel() {
		 return new AssetFacade(this).getAssetIdentificationModel();
	}

	@Override
	public void setAssetIdentificationModel(IReference submodel) {
		new AssetFacade(this).setAssetIdentificationModel(submodel);
		
	}

	public void setId(String string) {
		new AssetFacade(this).setId(string);
		
	}
	
	@Override
	public String getIdshort() {
	return new ReferableFacade(this).getIdshort();
	}

	@Override
	public String getCategory() {
		return new ReferableFacade(this).getCategory();
	}

	@Override
	public String getDescription() {
		return new ReferableFacade(this).getDescription();
	}

	@Override
	public IReference  getParent() {
		return new ReferableFacade(this).getParent();
	}

	@Override
	public void setIdshort(String idShort) {
		new ReferableFacade(this).setIdshort(idShort);
		
	}

	@Override
	public void setCategory(String category) {
		new ReferableFacade(this).setCategory(category);
		
	}

	@Override
	public void setDescription(String description) {
		new ReferableFacade(this).setDescription(description);
		
	}

	@Override
	public void setParent(IReference  obj) {
		new ReferableFacade(this).setParent(obj);
		
	}



}
