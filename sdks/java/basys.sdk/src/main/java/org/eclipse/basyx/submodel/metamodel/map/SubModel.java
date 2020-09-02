package org.eclipse.basyx.submodel.metamodel.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.IElementContainer;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IEmbeddedDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.facade.submodelelement.SubmodelElementFacadeFactory;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.AdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
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

	public static final String SUBMODELELEMENT = "submodelElements";
	public static final String MODELTYPE = "Submodel";

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
		put(SUBMODELELEMENT, new HashMap<String, ISubmodelElement>());
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
		put(SUBMODELELEMENT, new HashMap<String, ISubmodelElement>());
	}

	/**
	 * Constructor
	 */
	public SubModel(List<Property> properties) {
		this();
		properties.forEach(this::addSubModelElement);
	}

	/**
	 * Constructor
	 */
	public SubModel(List<Property> properties, List<Operation> operations) {
		this();
		properties.forEach(this::addSubModelElement);
		operations.forEach(this::addSubModelElement);
	}


	@SuppressWarnings("unchecked")
	public static SubModel createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		SubModel ret = new SubModel();
		
		Map<String, Object> smElements = new HashMap<>();
		
		//SubmodelElemets can be given as Map, Set or List
		//If it is a Set or List, convert it to a Map first
		if(map.get(SUBMODELELEMENT) instanceof Collection<?>) {
			Collection<Object> smElementsSet = (Collection<Object>) map.get(SUBMODELELEMENT);
			for (Object o: smElementsSet) {
				Map<String, Object> smElement = (Map<String, Object>) o;
				String id = (String) smElement.get(Referable.IDSHORT);
				smElements.put(id, smElement);
			}
		} else {
			smElements = (Map<String, Object>) map.get(SUBMODELELEMENT);
		}
		
		// Transfer map and overwrite SUBMODELELEMENt to prepare it for manual setting
		ret.setMap(map);
		ret.put(SUBMODELELEMENT, new HashMap<String, Object>());

		//Iterate through all SubmodelELements and create Facades for them
		for(Entry<String, Object> smElement: smElements.entrySet()) {
			ret.getSubmodelElements().put(smElement.getKey(),
					SubmodelElementFacadeFactory.createSubmodelElement(
							(Map<String, Object>) smElement.getValue()));
		}

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
		return Identifiable.createAsFacade(this, getKeyElement()).getAdministration();
	}

	@Override
	public IIdentifier getIdentification() {
		return Identifiable.createAsFacade(this, getKeyElement()).getIdentification();
	}

	public void setAdministration(AdministrativeInformation information) {
		Identifiable.createAsFacade(this, getKeyElement()).setAdministration(information);
	}

	public void setIdentification(IdentifierType idType, String id) {
		Identifiable.createAsFacade(this, getKeyElement()).setIdentification(idType, id);
	}

	@Override
	public Collection<IReference> getDataSpecificationReferences() {
		return HasDataSpecification.createAsFacade(this).getDataSpecificationReferences();
	}

	public void setDataSpecificationReferences(Collection<IReference> ref) {
		HasDataSpecification.createAsFacade(this).setDataSpecificationReferences(ref);
	}

	@Override
	public Collection<IEmbeddedDataSpecification> getEmbeddedDataSpecifications() {
		return HasDataSpecification.createAsFacade(this).getEmbeddedDataSpecifications();
	}

	public void setEmbeddedDataSpecifications(Collection<IEmbeddedDataSpecification> embeddedDataSpecifications) {
		HasDataSpecification.createAsFacade(this).setEmbeddedDataSpecifications(embeddedDataSpecifications);
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
		return Referable.createAsFacade(this, getKeyElement()).getIdShort();
	}

	public void setIdShort(String id) {
		Referable.createAsFacade(this, getKeyElement()).setIdShort(id);
	}

	public void setProperties(Map<String, IProperty> properties) {
		// first, remove all properties
		Set<Entry<String, ISubmodelElement>> elementSet = getSubmodelElements().entrySet();
		for ( Iterator<Entry<String, ISubmodelElement>> iterator = elementSet.iterator(); iterator.hasNext(); ) {
			Entry<String, ISubmodelElement> entry = iterator.next();
			if (entry.getValue() instanceof IProperty) {
				iterator.remove();
			}
		}
		// then add all given data properties
		properties.values().forEach(this::addSubModelElement);
	}

	public void setOperations(Map<String, IOperation> operations) {
		// first, remove all operations
		Set<Entry<String, ISubmodelElement>> elementSet = getSubmodelElements().entrySet();
		for (Iterator<Entry<String, ISubmodelElement>> iterator = elementSet.iterator(); iterator.hasNext();) {
			Entry<String, ISubmodelElement> entry = iterator.next();
			if (entry.getValue() instanceof IOperation) {
				iterator.remove();
			}
		}
		// then add all given operations
		operations.values().forEach(this::addSubModelElement);
	}

	@Override
	public String getCategory() {
		return Referable.createAsFacade(this, getKeyElement()).getCategory();
	}

	@Override
	public LangStrings getDescription() {
		return Referable.createAsFacade(this, getKeyElement()).getDescription();
	}

	@Override
	public IReference getParent() {
		return Referable.createAsFacade(this, getKeyElement()).getParent();
	}

	public void setCategory(String category) {
		Referable.createAsFacade(this, getKeyElement()).setCategory(category);
	}

	public void setDescription(LangStrings description) {
		Referable.createAsFacade(this, getKeyElement()).setDescription(description);
	}

	public void setParent(IReference obj) {
		Referable.createAsFacade(this, getKeyElement()).setParent(obj);
	}

	private KeyElements getKeyElement() {
		return KeyElements.SUBMODEL;
	}

	@Override
	public void addSubModelElement(ISubmodelElement element) {
		if (element instanceof SubmodelElement) {
			((SubmodelElement) element).setParent(getReference());
		}
		getSubmodelElements().put(element.getIdShort(), element);
	}

	@Override
	public Map<String, IProperty> getProperties() {
		Map<String, IProperty> properties = new HashMap<>();
		getSubmodelElements().values().forEach(e -> {
			if (e instanceof IProperty) {
				properties.put(e.getIdShort(), (IProperty) e);
			}
		});
		return properties;
	}

	@Override
	public Map<String, IOperation> getOperations() {
		Map<String, IOperation> operations = new HashMap<>();
		getSubmodelElements().values().forEach(e -> {
			if (e instanceof IOperation) {
				operations.put(e.getIdShort(), (IOperation) e);
			}
		});
		return operations;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ISubmodelElement> getSubmodelElements() {
		return (Map<String, ISubmodelElement>) get(SUBMODELELEMENT);
	}
	@Override
	public Collection<IConstraint> getQualifiers() {
		return Qualifiable.createAsFacade(this).getQualifiers();
	}

	@Override
	public IReference getReference() {
		return Identifiable.createAsFacade(this, getKeyElement()).getReference();
	}
}
