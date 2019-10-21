package org.eclipse.basyx.submodel.metamodel.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IDataElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.IProperty;
import org.eclipse.basyx.submodel.metamodel.facade.SubmodelFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasDataSpecificationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasSemanticsFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.IdentifiableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.ReferableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.haskind.HasKindFacade;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Description;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * Submodel class
 * 
 * @author kuhn, schnicke
 *
 *
 */
public class SubModel extends VABModelMap<Object> implements IVABElementContainer, ISubModel {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

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
	public SubModel(HasSemantics semantics, Identifiable identifiable, Qualifiable qualifiable, HasDataSpecification specification, HasKind hasKind) {
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

	@Override
	public IReference getSemanticId() {
		return new HasSemanticsFacade(this).getSemanticId();
	}

	public void setSemanticID(IReference ref) {
		new HasSemanticsFacade(this).setSemanticID(ref);
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
	public String getIdShort() {
		return new SubmodelFacade(this).getIdShort();
	}

	@Override
	public void setIdShort(String id) {
		new SubmodelFacade(this).setIdShort(id);
	}

	@Override
	public void setProperties(Map<String, IProperty> properties) {
		new SubmodelFacade(this).setProperties(properties);
	}

	@Override
	public void setOperations(Map<String, IOperation> operations) {
		new SubmodelFacade(this).setOperations(operations);
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

	public void setCategory(String category) {
		new ReferableFacade(this).setCategory(category);
	}

	public void setDescription(Description description) {
		new ReferableFacade(this).setDescription(description);
	}

	public void setParent(IReference obj) {
		new ReferableFacade(this).setParent(obj);
	}

	@Override
	public Map<String, IDataElement> getDataElements() {
		return new SubmodelFacade(this).getDataElements();
	}

	@Override
	public Map<String, IOperation> getOperations() {
		return new SubmodelFacade(this).getOperations();
	}

	@Override
	public void addSubModelElement(ISubmodelElement element) {
		new SubmodelFacade(this).addSubModelElement(element);
	}
}
