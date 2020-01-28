package org.eclipse.basyx.submodel.metamodel.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.IElementContainer;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * A submodel defines a specific aspect of the asset represented by the AAS.
 * <br />
 * <br />
 * A submodel is used to structure the digital representation and technical
 * functionality of an Administration Shell into distinguishable parts. Each
 * submodel refers to a well-defined domain or subject matter. Submodels can
 * become standardized and thus become submodels types. Submodels can have
 * different life-cycles.
 * 
 * @author kuhn, schnicke
 *
 *
 */
public class SubModel extends VABModelMap<Object> implements IElementContainer, ISubModel {

	public static final String SUBMODELELEMENT = "submodelElement";
	public static final String PROPERTIES = "dataElements";
	public static final String OPERATIONS = "operations";
	public static final String MODELTYPE = "Submodel";

	/**
	 * Submodel properties
	 */
	protected Map<String, IDataElement> dataElements = new HashMap<>();

	/**
	 * Submodel operations
	 */
	protected Map<String, IOperation> operations = new HashMap<>();

	/**
	 * Submodel elements in general. Does also contain operations and properties
	 */
	protected Map<String, ISubmodelElement> elements = new HashMap<>();

	/**
	 * Constructor
	 */
	public SubModel() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

		// Add qualifiers
		putAll(new HasSemantics());
		putAll(new Identifiable());
		putAll(new Qualifiable());
		putAll(new HasDataSpecification());
		putAll(new HasKind());

		// Attributes
		put(SUBMODELELEMENT, elements);

		// Helper attributes
		put(PROPERTIES, dataElements);
		put(OPERATIONS, operations);
	}

	/**
	 * Constructor
	 */
	public SubModel(HasSemantics semantics, Identifiable identifiable, Qualifiable qualifiable,
			HasDataSpecification specification, HasKind hasKind) {
		// Add qualifiers
		putAll(semantics);
		putAll(identifiable);
		putAll(qualifiable);
		putAll(specification);
		putAll(hasKind);

		// Attributes
		put(SUBMODELELEMENT, elements);

		// Helper attributes
		put(PROPERTIES, dataElements);
		put(OPERATIONS, operations);
	}

	/**
	 * Constructor
	 */
	public SubModel(List<Property> properties, List<Operation> operations) {
		this();
		properties.forEach(this::addSubModelElement);
		operations.forEach(this::addSubModelElement);
	}


	public static SubModel createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		SubModel ret = new SubModel();
		ret.setMap(map);
		return ret;
	}

	@Override
	public IReference getSemanticId() {
		return HasSemantics.createAsFacade(this).getSemanticId();
	}

	public void setSemanticId(IReference ref) {
		HasSemantics.createAsFacade(this).setSemanticID(ref);
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

	public void setIdentification(IdentifierType idType, String id) {
		Identifiable.createAsFacade(this).setIdentification(idType, id);
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return HasDataSpecification.createAsFacade(this).getDataSpecificationReferences();
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
		HasDataSpecification.createAsFacade(this).setDataSpecificationReferences(ref);
	}

	@Override
	public ModelingKind getModelingKind() {
		return HasKind.createAsFacade(this).getModelingKind();
	}

	public void setModelingKind(ModelingKind kind) {
		HasKind.createAsFacade(this).setModelingKind(kind);
	}

	@Override
	public String getIdShort() {
		return Referable.createAsFacade(this).getIdShort();
	}

	public void setIdShort(String id) {
		Referable.createAsFacade(this).setIdShort(id);
	}

	public void setProperties(Map<String, IProperty> properties) {
		put(SubModel.PROPERTIES, properties);

	}

	public void setOperations(Map<String, IOperation> operations) {
		put(SubModel.OPERATIONS, operations);

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

	public void setCategory(String category) {
		Referable.createAsFacade(this).setCategory(category);
	}

	public void setDescription(LangStrings description) {
		Referable.createAsFacade(this).setDescription(description);
	}

	public void setParent(IReference obj) {
		Referable.createAsFacade(this).setParent(obj);
	}

	@Override
	public void addSubModelElement(ISubmodelElement element) {
		ElementContainer.createAsFacade(this).addSubModelElement(element);
	}

	@Override
	public Map<String, IDataElement> getDataElements() {
		return ElementContainer.createAsFacade(this).getDataElements();
	}

	@Override
	public Map<String, IOperation> getOperations() {
		return ElementContainer.createAsFacade(this).getOperations();
	}
	
	@Override
	public Map<String, ISubmodelElement> getSubmodelElements() {
		return ElementContainer.createAsFacade(this).getSubmodelElements();
	}
	@Override
	public Set<IConstraint> getQualifier() {
		return Qualifiable.createAsFacade(this).getQualifier();
	}
}
