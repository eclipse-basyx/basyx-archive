package org.eclipse.basyx.aas.metamodel.map.parts;

import java.util.HashMap;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.api.parts.IAsset;
import org.eclipse.basyx.aas.metamodel.facade.parts.AssetFacade;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasDataSpecificationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.IdentifiableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.ReferableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.haskind.HasKindFacade;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Description;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;

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
	public static String MODELTYPE = "Asset";

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public Asset() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

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
	public Set<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(this).getDataSpecificationReferences();
	}
	
	public void setDataSpecificationReferences(Set<IReference> ref) {
		new HasDataSpecificationFacade(this).setDataSpecificationReferences(ref);
	}

	@Override
	public String getHasKindReference() {
		return new HasKindFacade(this).getHasKindReference();
	}
	
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

	public void setAdministration(String version, String revision) {
		new IdentifiableFacade(this).setAdministration(version, revision);
	}

	public void setIdentification(String idType, String id) {
		new IdentifiableFacade(this).setIdentification(idType, id);
	}

	@Override
	public IReference getAssetIdentificationModel() {
		 return new AssetFacade(this).getAssetIdentificationModel();
	}

	public void setAssetIdentificationModel(IReference submodel) {
		new AssetFacade(this).setAssetIdentificationModel(submodel);
	}

	public void setId(String string) {
		new AssetFacade(this).setId(string);
	}
	
	@Override
	public String getIdShort() {
		return new ReferableFacade(this).getIdShort();
	}

	@Override
	public String getCategory() {
		return new ReferableFacade(this).getCategory();
	}

	@Override
	public Description getDescription() {
		return new ReferableFacade(this).getDescription();
	}

	@Override
	public IReference getParent() {
		return new ReferableFacade(this).getParent();
	}

	public void setIdShort(String idShort) {
		new ReferableFacade(this).setIdShort(idShort);
		
	}

	public void setCategory(String category) {
		new ReferableFacade(this).setCategory(category);
		
	}

	public void setDescription(Description description) {
		new ReferableFacade(this).setDescription(description);
		
	}

	public void setParent(IReference obj) {
		new ReferableFacade(this).setParent(obj);
		
	}
}
