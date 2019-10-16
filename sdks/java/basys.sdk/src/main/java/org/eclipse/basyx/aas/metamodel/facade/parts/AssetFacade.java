package org.eclipse.basyx.aas.metamodel.facade.parts;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.api.parts.IAsset;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.AdministrativeInformation;
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
		return (Set<IReference>) map.get(HasDataSpecification.HASDATASPECIFICATION);
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

	@Override
	public IAdministrativeInformation getAdministration() {
		return (IAdministrativeInformation) map.get(Identifiable.ADMINISTRATION);
	}

	@Override
	public Identifier getIdentification() {
		return (Identifier) map.get(Identifiable.IDENTIFICATION);
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
	public String getIdshort() {
		return (String) map.get(Referable.IDSHORT);
	}

	@Override
	public String getCategory() {
		return (String) map.get(Referable.CATEGORY);
	}

	@Override
	public String getDescription() {
		return (String) map.get(Referable.DESCRIPTION);
	}

	@Override
	public IReference getParent() {
		return (IReference) map.get(Referable.PARENT);
	}

	public void setIdshort(String idShort) {
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
