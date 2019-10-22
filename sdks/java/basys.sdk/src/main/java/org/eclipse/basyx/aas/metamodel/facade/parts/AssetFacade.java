package org.eclipse.basyx.aas.metamodel.facade.parts;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.api.parts.IAsset;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.identifier.IdentifierFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.AdministrativeInformationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.ReferableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.reference.ReferenceHelper;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.AdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Description;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;

/**
 * Facade providing access to a map containing the Asset structure
 * 
 * @author rajashek
 *
 */

public class AssetFacade implements IAsset {

	private Map<String, Object> map;

	public AssetFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<IReference> getDataSpecificationReferences() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) map.get(HasDataSpecification.HASDATASPECIFICATION);
		return ReferenceHelper.transform(set);
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
		map.put(HasDataSpecification.HASDATASPECIFICATION, ref);
	}

	@Override
	public String getHasKindReference() {
		return (String) map.get(HasKind.KIND);
	}

	public void setHasKindReference(String kind) {
		map.put(HasKind.KIND, kind);

	}

	@SuppressWarnings("unchecked")
	@Override
	public IAdministrativeInformation getAdministration() {
		return new AdministrativeInformationFacade((Map<String, Object>) map.get(Identifiable.ADMINISTRATION));
	}

	@SuppressWarnings("unchecked")
	@Override
	public IIdentifier getIdentification() {
		return new IdentifierFacade((Map<String, Object>) map.get(Identifiable.IDENTIFICATION));
	}

	public void setAdministration(String version, String revision) {
		map.put(Identifiable.ADMINISTRATION, new AdministrativeInformation(version, revision));

	}

	public void setIdentification(String idType, String id) {
		map.put(Identifiable.IDENTIFICATION, new Identifier(idType, id));

	}

	@Override
	public IReference getAssetIdentificationModel() {
		return (IReference) map.get(Asset.ASSETIDENTIFICATIONMODEL);
	}

	public void setAssetIdentificationModel(IReference submodel) {
		map.put(Asset.ASSETIDENTIFICATIONMODEL, submodel);

	}

	public void setId(String string) {
		map.put(Referable.IDSHORT, string);

	}

	@Override
	public String getIdShort() {
		return (String) map.get(Referable.IDSHORT);
	}

	@Override
	public String getCategory() {
		return (String) map.get(Referable.CATEGORY);
	}

	@Override
	public Description getDescription() {
		return new ReferableFacade(map).getDescription();
	}

	@Override
	public IReference getParent() {
		return (IReference) map.get(Referable.PARENT);
	}

	public void setIdShort(String idShort) {
		map.put(Referable.IDSHORT, idShort);

	}

	public void setCategory(String category) {
		map.put(Referable.CATEGORY, category);

	}

	public void setDescription(String description) {
		map.put(Referable.DESCRIPTION, description);

	}

	public void setParent(IReference obj) {
		map.put(Referable.PARENT, obj);

	}

}
