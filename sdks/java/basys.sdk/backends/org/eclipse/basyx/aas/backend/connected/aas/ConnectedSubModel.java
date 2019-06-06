package org.eclipse.basyx.aas.backend.connected.aas;

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
import org.eclipse.basyx.aas.backend.connected.ConnectedVABModelMap;
import org.eclipse.basyx.aas.backend.connected.aas.submodelelement.operation.ConnectedOperation;
import org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property.ConnectedPropertyFactory;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasDataSpecificationFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasKindFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasSemanticsFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedIdentifiableFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.VABElementContainer;
import org.eclipse.basyx.aas.metamodel.hashmap.VABModelMap;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.AdministrativeInformation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.DataElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of SubModel
 * @author rajashek
 *
 */
public class ConnectedSubModel extends ConnectedVABModelMap<Object> implements VABElementContainer, ISubModel {
	
	ConnectedPropertyFactory factory = new ConnectedPropertyFactory();
	
	public ConnectedSubModel(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	
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

	
	@Override
	public IReference getSemanticId() {
		return new ConnectedHasSemanticsFacade(getPath(),getProxy()).getSemanticId();
	}

	@Override
	public void setSemanticID(IReference ref) {
		 new ConnectedHasSemanticsFacade(getPath(),getProxy()).setSemanticID(ref);
		
	}
	
	@Override
	public IAdministrativeInformation getAdministration() {
		return new ConnectedIdentifiableFacade(getPath(),getProxy()).getAdministration();
	}

	@Override
	public IIdentifier getIdentification() {
		return new ConnectedIdentifiableFacade(getPath(),getProxy()).getIdentification();
	}

	@Override
	public void setAdministration(String version, String revision) {
		 new ConnectedIdentifiableFacade(getPath(),getProxy()).setAdministration(version, revision);;
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		 new ConnectedIdentifiableFacade(getPath(),getProxy()).setIdentification(idType, id);
		
	}
	
	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new ConnectedHasDataSpecificationFacade(getPath(),getProxy()).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new ConnectedHasDataSpecificationFacade(getPath(),getProxy()).setDataSpecificationReferences(ref);
		
	}
	
	@Override
	public String getHasKindReference() {
		return  new ConnectedHasKindFacade(getPath(),getProxy()).getHasKindReference();
	}

	@Override
	public void setHasKindReference(String kind) {
		new ConnectedHasKindFacade(getPath(),getProxy()).setHasKindReference(kind);
		
	}
	


	/**
	 * Update value of 'administration' property
	 */
	public void setAdministration(AdministrativeInformation newValue) {
		getElements().put("administration", newValue);
	}


	
	@Override
	public String getId() {
	return (String)getProxy().readElementValue(constructPath(SubModel.IDSHORT));
	}

	@Override
	public void setId(String id) {
		getProxy().updateElementValue(constructPath(SubModel.IDSHORT), id);
		
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
	public void setProperties(Map<String, IProperty> properties) {
		getProxy().updateElementValue(constructPath(SubModel.PROPERTIES),properties);
		
	}

	@Override
	public void setOperations(Map<String, IOperation> operations) {
		getProxy().updateElementValue(constructPath(SubModel.OPERATIONS),operations);
		
	}

	public SubModel getSubModel() {
		// Assume that VAB HashMap provider carries a sub model
		return (SubModel) getElements();
	}

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

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IProperty> getProperties() {
		Map<String, Object> props = (Map<String, Object>) getProxy().readElementValue(constructPath("properties"));

		Map<String, IProperty> ret = new HashMap<>();
		for (String s : props.keySet()) {
			ret.put(s, factory.createProperty(constructPath("properties/" + s), getProxy()));
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IOperation> getOperations() {
		Map<String, Object> ops = (Map<String, Object>) getProxy().readElementValue(constructPath("operations"));

		Map<String, IOperation> ret = new HashMap<>();
		for (String s : ops.keySet()) {
			ret.put(s, new ConnectedOperation(constructPath("operations/" + s), getProxy()));
		}
		return ret;
	}

	@Override
	public Map<String, Object> getElements() {
		// TODO Auto-generated method stub 
		return null;
	}

}
