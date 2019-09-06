package org.eclipse.basyx.aas.backend.connected.aas;

import java.util.Collection;
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
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.AdministrativeInformation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.DataElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.operation.Operation;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
import org.eclipse.basyx.vab.core.tools.VABPathTools;
/**
 * "Connected" implementation of SubModel
 * @author rajashek
 *
 */
public class ConnectedSubModel extends ConnectedVABModelMap<Object> implements VABElementContainer, ISubModel {

	ConnectedPropertyFactory factory = new ConnectedPropertyFactory();

	public ConnectedSubModel(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public IReference getSemanticId() {
		return new ConnectedHasSemanticsFacade(getProxy()).getSemanticId();
	}

	@Override
	public void setSemanticID(IReference ref) {
		new ConnectedHasSemanticsFacade(getProxy()).setSemanticID(ref);
	}

	@Override
	public IAdministrativeInformation getAdministration() {
		return new ConnectedIdentifiableFacade(getProxy()).getAdministration();
	}

	@Override
	public IIdentifier getIdentification() {
		return new ConnectedIdentifiableFacade(getProxy()).getIdentification();
	}

	@Override
	public void setAdministration(String version, String revision) {
		new ConnectedIdentifiableFacade(getProxy()).setAdministration(version, revision);

	}

	@Override
	public void setIdentification(String idType, String id) {
		new ConnectedIdentifiableFacade(getProxy()).setIdentification(idType, id);

	}

	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new ConnectedHasDataSpecificationFacade(getProxy()).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new ConnectedHasDataSpecificationFacade(getProxy()).setDataSpecificationReferences(ref);

	}

	@Override
	public String getHasKindReference() {
		return new ConnectedHasKindFacade(getProxy()).getHasKindReference();
	}

	@Override
	public void setHasKindReference(String kind) {
		new ConnectedHasKindFacade(getProxy()).setHasKindReference(kind);
	}



	/**
	 * Update value of 'administration' property
	 */
	public void setAdministration(AdministrativeInformation newValue) {
		new ConnectedIdentifiableFacade(getProxy()).setAdministration(newValue.getVersion(), newValue.getRevision());
	}



	@Override
	public String getId() {
		return (String) getProxy().getModelPropertyValue(Referable.IDSHORT);
	}

	@Override
	public void setId(String id) {
		getProxy().setModelPropertyValue(Referable.IDSHORT, id);
	}

	@Override
	public void addEvent(Object event) {
		throw new FeatureNotImplementedException();
	}

	@Override
	public void addElementCollection(SubmodelElementCollection collection) {
		getProxy().createValue(SubModel.SUBMODELELEMENT, collection);
		if (collection instanceof IProperty) {
			getProperties().put(collection.getId(), collection);
		}
	}

	@Override
	public void setProperties(Map<String, IProperty> properties) {
		getProxy().setModelPropertyValue(SubModel.PROPERTIES, properties);
	}

	@Override
	public void setOperations(Map<String, IOperation> operations) {
		getProxy().setModelPropertyValue(SubModel.OPERATIONS, operations);
	}

	@Override
	public void addDataElement(DataElement value) {
	
		String id = value.getId();
		if (value instanceof IProperty) {
			System.out.println("adding Property " + id);
			getProxy().createValue(VABPathTools.concatenatePaths(SubModel.PROPERTIES, id), value);
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
			getProxy().createValue(VABPathTools.concatenatePaths(SubModel.OPERATIONS, id), operation);
		} else {
			throw new RuntimeException("Tried to add Operation with id " + id + " which is does not implement IOperation");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<String, IProperty> getProperties() {
		// Store operations as map
		Map<String, Object> des = new HashMap<>();

		// Create return value
		Map<String, IProperty> ret = new HashMap<>();

		// Sub model operation list
		Object smDeList = getProxy().getModelPropertyValue(SubModel.PROPERTIES);

		// RTTI check
		if (smDeList instanceof HashSet) {
			// Read  values
			Collection<Map<String, Object>> dataElemNodes = (Collection<Map<String, Object>>) smDeList;

			// Convert to IOperation
			for (Map<String, Object> deNode: dataElemNodes) {
				String id = (String)  deNode.get(Referable.IDSHORT);
				ret.put(id, factory.createProperty(getProxy().getDeepProxy(VABPathTools.concatenatePaths(SubModel.PROPERTIES, id))));
			}
		} else {
			// Properties already arrive as Map
			des = (Map<String, Object>) smDeList;

			for (String s : des.keySet()) {
				ret.put(s, factory.createProperty(getProxy().getDeepProxy(VABPathTools.concatenatePaths(SubModel.PROPERTIES, s))));
			}
		}

		// Return result
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, IOperation> getOperations() {
		// Store operations as map
		Map<String, Object> ops = new HashMap<>();

		// Create return value
		Map<String, IOperation> ret = new HashMap<>();

		// Sub model operation list
		Object smOpList = getProxy().getModelPropertyValue(SubModel.OPERATIONS);

		// RTTI check (c# specific)
		if (smOpList instanceof HashSet) {
			// Read  values
			Collection<Map<String, Object>> operationNodes = (Collection<Map<String, Object>>) smOpList;

			// Convert to IOperation
			for (Map<String, Object> opNode: operationNodes) {
				String id = (String) opNode.get(Referable.IDSHORT);
				
				ConnectedOperation conOp = new ConnectedOperation(getProxy().getDeepProxy(VABPathTools.concatenatePaths(SubModel.OPERATIONS, id)));
				// Cache operation properties
				conOp.putAllLocal(opNode);
				ret.put(id, conOp);
			}
		} else {
			// Operations already arrive as Map (java specific)
			ops = (Map<String, Object>) smOpList;

			for (String s : ops.keySet()) {
				ret.put(s, new ConnectedOperation(getProxy().getDeepProxy(VABPathTools.concatenatePaths(SubModel.OPERATIONS, s))));
			}
		}

		// Return result
		return ret;
	}

	@Override
	public String getIdshort() {
		return (String) getProxy().getModelPropertyValue(Referable.IDSHORT);
	}

	@Override
	public String getCategory() {
		return (String) getProxy().getModelPropertyValue(Referable.CATEGORY);
	}

	@Override
	public String getDescription() {
		return (String) getProxy().getModelPropertyValue(Referable.DESCRIPTION);
	}

	@Override
	public IReference  getParent() {
		return (IReference) getProxy().getModelPropertyValue(Referable.PARENT);
	}

	@Override
	public void setIdshort(String idShort) {
		getProxy().setModelPropertyValue(Referable.IDSHORT, idShort);
		
	}

	@Override
	public void setCategory(String category) {
		getProxy().setModelPropertyValue(Referable.CATEGORY, category);
		
	}

	@Override
	public void setDescription(String description) {
		getProxy().setModelPropertyValue(Referable.DESCRIPTION, description);
		
	}

	@Override
	public void setParent(IReference  obj) {
		getProxy().setModelPropertyValue(Referable.PARENT, obj);
		
	}

}
