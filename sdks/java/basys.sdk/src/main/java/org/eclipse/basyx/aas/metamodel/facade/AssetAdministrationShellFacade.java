package org.eclipse.basyx.aas.metamodel.facade;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.api.parts.IAsset;
import org.eclipse.basyx.aas.metamodel.api.parts.IConceptDictionary;
import org.eclipse.basyx.aas.metamodel.api.parts.IView;
import org.eclipse.basyx.aas.metamodel.api.security.ISecurity;
import org.eclipse.basyx.aas.metamodel.facade.parts.AssetFacade;
import org.eclipse.basyx.aas.metamodel.facade.parts.ConceptDictionaryFacade;
import org.eclipse.basyx.aas.metamodel.facade.parts.ViewFacade;
import org.eclipse.basyx.aas.metamodel.facade.security.SecurityFacade;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.identifier.IdentifierFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.AdministrativeInformationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.ReferableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.reference.ReferenceFacade;
import org.eclipse.basyx.submodel.metamodel.facade.reference.ReferenceHelper;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.AdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Description;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Facade providing access to a map containing the AssetAdministrationShell
 * structure
 * 
 * @author rajashek
 *
 */
public class AssetAdministrationShellFacade implements IAssetAdministrationShell {

	private static Logger logger = LoggerFactory.getLogger(AssetAdministrationShellFacade.class);

	private Map<String, Object> map;


	public AssetAdministrationShellFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	public void setSecurity(ISecurity security) {
		map.put(AssetAdministrationShell.SECURITY, security);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ISecurity getSecurity() {
		return new SecurityFacade((Map<String, Object>) map.get(AssetAdministrationShell.SECURITY));
	}

	public void setDerivedFrom(IReference derivedFrom) {
		map.put(AssetAdministrationShell.DERIVEDFROM, derivedFrom);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getDerivedFrom() {
		return new ReferenceFacade((Map<String, Object>) map.get(AssetAdministrationShell.DERIVEDFROM));
	}

	public void setAsset(IReference asset) {
		map.put(AssetAdministrationShell.ASSET, asset);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IAsset getAsset() {
		return new AssetFacade((Map<String, Object>) map.get(AssetAdministrationShell.ASSET));
	}

	@Override
	public void setSubModels(Set<SubmodelDescriptor> submodels) {
		map.put(AssetAdministrationShell.SUBMODELS, submodels);

	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<SubmodelDescriptor> getSubModelDescriptors() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) map.get(AssetAdministrationShell.SUBMODELS);
		return set.stream().map(m -> new SubmodelDescriptor(m)).collect(Collectors.toSet());
	}

	public void setViews(Set<IView> views) {
		map.put(AssetAdministrationShell.VIEWS, views);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<IView> getViews() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) map.get(AssetAdministrationShell.VIEWS);

		return set.stream().map(m -> new ViewFacade(m)).collect(Collectors.toSet());
	}

	public void setConceptDictionary(Set<IConceptDictionary> dictionaries) {
		map.put(AssetAdministrationShell.CONCEPTDICTIONARY, dictionaries);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<IConceptDictionary> getConceptDictionary() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) map.get(AssetAdministrationShell.CONCEPTDICTIONARY);

		return set.stream().map(m -> new ConceptDictionaryFacade(m)).collect(Collectors.toSet());
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
	@SuppressWarnings("unchecked")
	public Set<IReference> getDataSpecificationReferences() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) map.get(HasDataSpecification.HASDATASPECIFICATION);
		return ReferenceHelper.transform(set);
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
		map.put(HasDataSpecification.HASDATASPECIFICATION, ref);
	}

	@Override
	public String getIdShort() {
		return (String) map.get(Referable.IDSHORT);
	}

	@Override
	public Map<String, ISubModel> getSubModels() {
		throw new RuntimeException("getSubModels on local copy is not supported");
	}

	@SuppressWarnings("unchecked")
	public Set<ISubModel> getSubModelsHack() {
		// FIXME: Replace this with correct implementation
		return (Set<ISubModel>) map.get(AssetAdministrationShell.SUBMODELS);
	}

	@Override
	public String getCategory() {
		return (String) map.get(Referable.CATEGORY);
	}

	@Override
	public Description getDescription() {
		return new ReferableFacade(map).getDescription();
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getParent() {
		return new ReferenceFacade((Map<String, Object>) map.get(Referable.PARENT));
	}

	@Override
	public void setIdShort(String idShort) {
		map.put(Referable.IDSHORT, idShort);
	}

	public void setCategory(String category) {
		map.put(Referable.CATEGORY, category);
	}

	public void setDescription(Description description) {
		map.put(Referable.DESCRIPTION, description);
	}

	public void setParent(IReference obj) {
		map.put(Referable.PARENT, obj);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addSubModel(SubmodelDescriptor descriptor) {
		logger.trace("adding Submodel", descriptor.getIdentifier().getId());
		((Set<SubmodelDescriptor>) map.get(AssetAdministrationShell.SUBMODELS)).add(descriptor);
	}

}
