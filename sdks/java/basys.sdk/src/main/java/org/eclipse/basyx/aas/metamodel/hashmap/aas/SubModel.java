package org.eclipse.basyx.aas.metamodel.hashmap.aas;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.metamodel.facades.HasDataSpecificationFacade;
import org.eclipse.basyx.aas.metamodel.facades.HasKindFacade;
import org.eclipse.basyx.aas.metamodel.facades.HasSemanticsFacade;
import org.eclipse.basyx.aas.metamodel.facades.IdentifiableFacade;
import org.eclipse.basyx.aas.metamodel.facades.ReferableFacade;
import org.eclipse.basyx.aas.metamodel.facades.SubmodelFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.VABElementContainer;
import org.eclipse.basyx.aas.metamodel.hashmap.VABModelMap;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind.HasKind;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable.Qualifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.DataElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;

/**
 * Submodel class
 * 
 * @author kuhn, schnicke
 *
 *
 */
public class SubModel extends VABModelMap<Object> implements VABElementContainer, ISubModel {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String SUBMODELELEMENT ="submodelElement";
	public static final String PROPERTIES="dataElements";
	public static final String OPERATIONS="operations";

	
	/**
	 * Submodel properties
	 */
	protected Map<String, IProperty> properties = new VABModelMap<>();

	
	/**
	 * Submodel operations
	 */
	protected Map<String, IOperation> operations = new VABModelMap<>();

	
	/**
	 * Submodel elements in general. Does also contain operations and properties
	 */
	protected Map<String, SubmodelElement> elements = new HashMap<String, SubmodelElement>();

	
	
	/**
	 * Constructor
	 */
	public SubModel() {
		// Add qualifiers
		putAll(new HasSemantics());
		putAll(new Identifiable());
		putAll(new Qualifiable());
		putAll(new HasDataSpecification());
		putAll(new HasKind());

		// Attributes
		put(SUBMODELELEMENT, elements);

		// Helper attributes
		put(PROPERTIES, properties);
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
		put(PROPERTIES, properties);
		put(OPERATIONS, operations);
	}


	/**
	 * Add property
	 */
	@Override
	public void addDataElement(DataElement value) {

		String id = value.getId();
		if (value instanceof IProperty) {
			System.out.println("adding Property " + id);
			properties.put(id, (IProperty) value);
			elements.put(id, value);
		} else {
			throw new RuntimeException("Tried to add DataElement with id " + id + " which is does not implement IProperty");
		}
	}

	/**
	 * Add operation
	 */
	@Override
	public void addOperation(Operation operation) {

		String id = operation.getId();
		if (operation instanceof IOperation) {

			System.out.println("adding Operation " + id);

			// Add single operation
			operations.put(id, operation);
			elements.put(id, operation);
		} else {
			throw new RuntimeException("Tried to add Operation with id " + id + " which is does not implement IOperation");
		}
	}
	
	@Override
	public IReference getSemanticId() {
		return new HasSemanticsFacade(this).getSemanticId();
	}

	@Override
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

	@Override
	public void setAdministration(String version, String revision) {
		new IdentifiableFacade(this).setAdministration(version, revision);
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		new IdentifiableFacade(this).setIdentification(idType, id);
		
	}
	
	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(this).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new HasDataSpecificationFacade(this).setDataSpecificationReferences(ref);
		
	}
	
	@Override
	public String getHasKindReference() {
      return new HasKindFacade(this).getHasKindReference();
	}

	@Override
	public void setHasKindReference(String kind) {
		new HasKindFacade(this).setHasKindReference(kind);
		
	}

	@Override
	public String getId() {
		return new SubmodelFacade(this).getId();
	}

	@Override
	public void setId(String id) {
		new SubmodelFacade(this).setId(id);
		
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
	public Map<String, Object> getElements() {
		return new SubmodelFacade(this).getElements();
	}

	@Override
	public void addEvent(Object event) {
		throw new FeatureNotImplementedException();
	}

	@Override
	public void addElementCollection(SubmodelElementCollection collection) {
		getElements().put(collection.getId(), collection);
		if (collection instanceof IProperty) {
			getProperties().put(collection.getId(), collection);
		}
	}

	@Override
	public VABModelMap<IProperty> getProperties() {
		return (VABModelMap<IProperty>) new SubmodelFacade(this).getProperties();
	}

	@Override
	public Map<String, IOperation> getOperations() {
		return new SubmodelFacade(this).getOperations();
	}
	
	@Override
	public String getIdshort() {
	return new ReferableFacade(this).getIdshort();
	}

	@Override
	public String getCategory() {
		return new ReferableFacade(this).getCategory();
	}

	@Override
	public String getDescription() {
		return new ReferableFacade(this).getDescription();
	}

	@Override
	public IReference  getParent() {
		return new ReferableFacade(this).getParent();
	}

	@Override
	public void setIdshort(String idShort) {
		new ReferableFacade(this).setIdshort(idShort);
		
	}

	@Override
	public void setCategory(String category) {
		new ReferableFacade(this).setCategory(category);
		
	}

	@Override
	public void setDescription(String description) {
		new ReferableFacade(this).setDescription(description);
		
	}

	@Override
	public void setParent(IReference  obj) {
		new ReferableFacade(this).setParent(obj);
		
	}
}

