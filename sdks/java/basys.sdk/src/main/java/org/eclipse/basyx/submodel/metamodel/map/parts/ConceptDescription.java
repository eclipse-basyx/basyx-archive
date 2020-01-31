package org.eclipse.basyx.submodel.metamodel.map.parts;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IDataSpecificationIEC61360;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.parts.IConceptDescription;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.dataspecification.DataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.reference.ReferenceHelper;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * ConceptDescription class as described in DAAS document
 * 
 * @author schnicke
 *
 */
public class ConceptDescription extends VABModelMap<Object> implements IConceptDescription {
	public static final String ISCASEOF = "isCaseOf";
	public static final String MODELTYPE = "ConceptDescription";

	// Addition to meta model
	public static final String DATASPECIFICATIONS = "dataSpecifications";

	public ConceptDescription() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

		// Add qualifiers
		putAll(new HasDataSpecification());
		putAll(new Identifiable());

		// Add attributes
		put(ISCASEOF, new HashSet<Reference>());
		put(DATASPECIFICATIONS, new HashSet<IDataSpecificationIEC61360>());
	}

	/**
	 * Creates a DataSpecificationIEC61360 object from a map
	 * 
	 * @param obj
	 *            a DataSpecificationIEC61360 object as raw map
	 * @return a DataSpecificationIEC61360 object, that behaves like a facade for
	 *         the given map
	 */
	public static ConceptDescription createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		ConceptDescription ret = new ConceptDescription();
		ret.setMap(map);
		return ret;
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return HasDataSpecification.createAsFacade(this).getDataSpecificationReferences();
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
		HasDataSpecification.createAsFacade(this).setDataSpecificationReferences(ref);
	}

	@Override
	public IAdministrativeInformation getAdministration() {
		return Identifiable.createAsFacade(this).getAdministration();
	}

	@Override
	public IIdentifier getIdentification() {
		return Identifiable.createAsFacade(this).getIdentification();
	}

	public void setAdministration(String version, String revision) {
		Identifiable.createAsFacade(this).setAdministration(version, revision);
	}

	public void setIdentification(String idType, String id) {
		Identifiable.createAsFacade(this).setIdentification(idType, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<IReference> getIsCaseOf() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) get(ConceptDescription.ISCASEOF);
		return ReferenceHelper.transform(set);
	}

	public void setIsCaseOf(Set<Reference> ref) {
		put(ConceptDescription.ISCASEOF, ref);
	}

	@Override
	public String getIdShort() {
		return Referable.createAsFacade(this).getIdShort();
	}

	@Override
	public String getCategory() {
		return Referable.createAsFacade(this).getCategory();
	}

	@Override
	public LangStrings getDescription() {
		return Referable.createAsFacade(this).getDescription();
	}
	@Override
	public IReference getParent() {
		return Referable.createAsFacade(this).getParent();
	}

	public void setIdShort(String idShort) {
		Referable.createAsFacade(this).setIdShort(idShort);

	}

	public void setCategory(String category) {
		Referable.createAsFacade(this).setCategory(category);

	}

	public void setDescription(LangStrings description) {
		Referable.createAsFacade(this).setDescription(description);
	}

	public void setParent(IReference obj) {
		Referable.createAsFacade(this).setParent(obj);
	}

	@SuppressWarnings("unchecked")
	public Set<IDataSpecification> getDataSpecifications() {
		return (Set<IDataSpecification>) get(DATASPECIFICATIONS);
	}

	public void addDataSpecification(IDataSpecificationIEC61360 spec) {
		getDataSpecifications().add(new DataSpecification(spec));
	}


}