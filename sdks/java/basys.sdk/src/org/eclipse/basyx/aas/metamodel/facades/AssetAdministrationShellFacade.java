package org.eclipse.basyx.aas.metamodel.facades;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.api.metamodel.aas.parts.IConceptDictionary;
import org.eclipse.basyx.aas.api.metamodel.aas.parts.IView;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.security.ISecurity;
import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.AdministrativeInformation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;
/**
 * Facade providing access to a map containing the AssetAdministrationShell structure
 * @author rajashek
 *
 */
public class AssetAdministrationShellFacade implements IAssetAdministrationShell {

	private Map<String, Object> map;
	public AssetAdministrationShellFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public void setSecurity(ISecurity security) {
		map.put(AssetAdministrationShell.SECURITY,security );
		
	}

	@Override
	public ISecurity getSecurity() {
		return (ISecurity)map.get(AssetAdministrationShell.SECURITY);
	}

	@Override
	public void setDerivedFrom(IReference derivedFrom) {
		map.put(AssetAdministrationShell.DERIVEDFROM,derivedFrom );
		
	}

	@Override
	public IReference getDerivedFrom() {
		return (IReference)map.get(AssetAdministrationShell.DERIVEDFROM);
	}

	@Override
	public void setAsset(IReference asset) {
		map.put(AssetAdministrationShell.ASSET,asset );
		
	}

	@Override
	public IReference getAsset() {
		return (IReference)map.get(AssetAdministrationShell.ASSET);
	}

	@Override
	public void setSubModel(Set<IReference> submodels) {
		map.put(AssetAdministrationShell.SUBMODEL,submodels );
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IReference> getSubModel() {
		return (Set<IReference>)map.get(AssetAdministrationShell.SUBMODEL);
	}

	@Override
	public void setViews(Set<IView> views) {
		map.put(AssetAdministrationShell.VIEWS,views);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IView> getViews() {
		return (Set<IView>)map.get(AssetAdministrationShell.VIEWS);
	}

	@Override
	public void setConceptDictionary(Set<IConceptDictionary> dictionaries) {
		map.put(AssetAdministrationShell.CONCEPTDICTIONARY, dictionaries);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IConceptDictionary> getConceptDictionary() {
		return (Set<IConceptDictionary>)map.get(AssetAdministrationShell.CONCEPTDICTIONARY);
	}
	
	@Override
	public AdministrativeInformation getAdministration() {
		return (AdministrativeInformation)map.get(Identifiable.ADMINISTRATION);
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
	public String getId() {
	return (String)map.get(AssetAdministrationShell.IDSHORT);
	}

	@Override
	public void setId(String id) {
		map.put(AssetAdministrationShell.IDSHORT, id);
		
	}

	@Override
	public Map<String, ISubModel> getSubModels() {
		throw new RuntimeException("getSubModels on local copy is not supported");
	}

	@Override
	public void addSubModel(ISubModel subModel) {
		throw new RuntimeException("addSubModel on local copy is not supported");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSubModelIds() {
		Set<Reference> refs = (Set<Reference>) map.get(AssetAdministrationShell.SUBMODEL);

		// Map a list of references to a list of Strings
		return refs.stream().map(this::getIdFromReference).collect(Collectors.toList());
	}
	
	/**
	 * Joins a list of keys using / as separator. <br />
	 * E.g. <i>{ aas, submodels, testId }</i> gets joined to
	 * <i>aas/submodels/testId</i>
	 * 
	 * @param ref
	 * @return
	 */
	private String getIdFromReference(Reference ref) {
		return ref.getKeys().stream().map(k -> k.getValue()).collect(Collectors.joining("/"));
	}

}
