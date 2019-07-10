package org.eclipse.basyx.aas.metamodel.facades;

import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.parts.IAsset;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.parts.Asset;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.AdministrativeInformation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind.HasKind;

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

	@SuppressWarnings("unchecked")
	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return (HashSet<IReference>) map.get(HasDataSpecification.HASDATASPECIFICATION);
	}
	
	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		map.put(HasDataSpecification.HASDATASPECIFICATION, ref);
		
	}
	
	@Override
	public String getHasKindReference() {
		return (String) map.get(HasKind.KIND);
	}

	@Override
	public void setHasKindReference(String kind) {
		map.put(HasKind.KIND, kind);
		
	}
	
	@Override
	public IAdministrativeInformation getAdministration() {
		return (IAdministrativeInformation)map.get(Identifiable.ADMINISTRATION);
	}

	@Override
	public Identifier getIdentification() {
		return (Identifier)map.get(Identifiable.IDENTIFICATION);
	}

	@Override
	public void setAdministration(String version, String revision) {
		map.put(Identifiable.ADMINISTRATION, new AdministrativeInformation(version, revision));
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		map.put(Identifiable.IDENTIFICATION, new Identifier(idType, id));
		
	}

	@Override
	public IReference getAssetIdentificationModel() {
		return (IReference)map.get(Asset.ASSETIDENTIFICATIONMODEL);
	}

	@Override
	public void setAssetIdentificationModel(IReference submodel) {
		map.put(Asset.ASSETIDENTIFICATIONMODEL, submodel);
		
	}

	public void setId(String string) {
		map.put(Identifiable.IDSHORT, string);
		
	}

}
