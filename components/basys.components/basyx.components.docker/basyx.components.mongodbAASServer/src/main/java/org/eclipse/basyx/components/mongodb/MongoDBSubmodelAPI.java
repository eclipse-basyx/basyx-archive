package org.eclipse.basyx.components.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.basyx.components.configuration.BaSyxMongoDBConfiguration;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.facade.submodelelement.SubmodelElementFacadeFactory;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.restapi.SubmodelElementProvider;
import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPI;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.map.VABMapProvider;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/**
 * Implements the ISubmodelAPI for a mongoDB backend.
 * 
 * @author espen
 */
public class MongoDBSubmodelAPI implements ISubmodelAPI {
	private static final String DEFAULT_CONFIG_PATH = "mongodb.properties";
	private static final String SMIDPATH = Identifiable.IDENTIFICATION + "." + Identifier.ID;

	protected BaSyxMongoDBConfiguration config;
	protected MongoOperations mongoOps;
	protected String collection;
	protected String smId;

	/**
	 * Receives the path of the configuration.properties file in it's constructor.
	 * 
	 * @param configFilePath
	 */
	public MongoDBSubmodelAPI(BaSyxMongoDBConfiguration config, String smId) {
		this.setConfiguration(config);
		this.setSubmodelId(smId);
	}

	/**
	 * Receives the path of the .properties file in it's constructor from a resource.
	 */
	public MongoDBSubmodelAPI(String resourceConfigPath, String smId) {
		config = new BaSyxMongoDBConfiguration();
		config.loadFromResource(resourceConfigPath);
		this.setConfiguration(config);
		this.setSubmodelId(smId);
	}

	/**
	 * Constructor using default sql connections
	 */
	public MongoDBSubmodelAPI(String smId) {
		this(DEFAULT_CONFIG_PATH, smId);
	}

	/**
	 * Sets the db configuration for the submodel API.
	 * 
	 * @param config
	 */
	public void setConfiguration(BaSyxMongoDBConfiguration config) {
		this.config = config;
		MongoClient client = MongoClients.create(config.getConnectionUrl());
		this.mongoOps = new MongoTemplate(client, config.getDatabase());
		this.collection = config.getSubmodelCollection();
	}
	
	/**
	 * Sets the submodel id, so that this API points to the submodel with smId. Can be changed
	 * to point to a different submodel in the database.
	 * 
	 * @param smId
	 */
	public void setSubmodelId(String smId) {
		this.smId = smId;
	}

	/**
	 * Depending on whether the model is already in the db, this method inserts or replaces the existing data.
	 * The new submodel id for this API is taken from the given submodel.
	 * 
	 * @param sm
	 */
	public void setSubModel(SubModel sm) {
		String id = sm.getIdentification().getId();
		this.setSubmodelId(id);

		Query hasId = query(where(SMIDPATH).is(smId));
		Object replaced = mongoOps.findAndReplace(hasId, sm, collection);
		if (replaced == null) {
			mongoOps.insert(sm, collection);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ISubModel getSubmodel() {
		// Query SubModel from MongoDB
		Query hasId = query(where(SMIDPATH).is(smId));
		SubModel result = mongoOps.findOne(hasId, SubModel.class, collection);
		if (result == null) {
			throw new ResourceNotFoundException("The submodel " + smId + " could not be found in the database.");
		}

		// Remove mongoDB-specific map attribute from AASDescriptor
		result.remove("_id");

		// Cast all SubmodelElement maps to ISubmodelElements before returning the submodel
		Map<String, ISubmodelElement> elements = new HashMap<>();
		Map<String, Map<String, Object>> elemMaps = (Map<String, Map<String, Object>>) result
				.get(SubModel.SUBMODELELEMENT);
		for (Entry<String, Map<String, Object>> entry : elemMaps.entrySet()) {
			String shortId = entry.getKey();
			Map<String, Object> elemMap = entry.getValue();
			ISubmodelElement element = SubmodelElementFacadeFactory.createSubmodelElement(elemMap);
			elements.put(shortId, element);
		}
		// Replace the element map in the submodel
		result.put(SubModel.SUBMODELELEMENT, elements);
		// Return the "fixed" submodel
		return result;
	}

	@Override
	public Collection<ISubmodelElement> getElements() {
		ISubModel sm = getSubmodel();
		return sm.getSubmodelElements().values();
	}

	@Override
	public void addSubmodelElement(ISubmodelElement elem) {
		// Get sm from db
		SubModel sm = (SubModel) getSubmodel();
		// Add element
		sm.addSubModelElement(elem);
		// Replace db entry
		Query hasId = query(where(SMIDPATH).is(smId));
		mongoOps.findAndReplace(hasId, sm, collection);
	}

	@Override
	public ISubmodelElement getSubmodelElement(String idShort) {
		SubModel sm = (SubModel) getSubmodel();
		Map<String, ISubmodelElement> submodelElements = sm.getSubmodelElements();
		ISubmodelElement element = submodelElements.get(idShort);
		if (element == null) {
			throw new ResourceNotFoundException("The element \"" + idShort + "\" could not be found");
		}
		return element;
	}

	@Override
	public void deleteSubmodelElement(String idShort) {
		// Get sm from db
		SubModel sm = (SubModel) getSubmodel();
		// Remove element
		sm.getSubmodelElements().remove(idShort);
		// Replace db entry
		Query hasId = query(where(SMIDPATH).is(smId));
		mongoOps.findAndReplace(hasId, sm, collection);
	}

	@Override
	public Collection<IOperation> getOperations() {
		SubModel sm = (SubModel) getSubmodel();
		return sm.getOperations().values();
	}

	@Override
	public Collection<IProperty> getProperties() {
		SubModel sm = (SubModel) getSubmodel();
		return sm.getProperties().values();
	}

	@Override
	public void updateProperty(String idShort, Object newValue) {
		// Get sm from db
		SubModel sm = (SubModel) getSubmodel();
		// Unwrap value
		newValue = unwrapParameter(newValue);
		// Get and update property value
		getElementProvider(sm, idShort).setModelPropertyValue(Property.VALUE, newValue);
		// Replace db entry
		Query hasId = query(where(SMIDPATH).is(smId));
		mongoOps.findAndReplace(hasId, sm, collection);
	}

	@SuppressWarnings("unchecked")
	protected Object unwrapParameter(Object parameter) {
		if (parameter instanceof Map<?, ?>) {
			Map<String, Object> map = (Map<String, Object>) parameter;
			// Parameters have a strictly defined order and may not be omitted at all.
			// Enforcing the structure with valueType is ok, but we should unwrap null values, too.
			if (map.get("valueType") != null && map.containsKey("value")) {
				return map.get("value");
			}
		}
		return parameter;
	}

	@Override
	public Object getPropertyValue(String idShort) {
		SubModel sm = (SubModel) getSubmodel();
		return getElementProvider(sm, idShort).getModelPropertyValue("/value");
	}

	@SuppressWarnings("unchecked")
	private IModelProvider getElementProvider(SubModel sm, String idShort) {
		// Remove element
		ISubmodelElement elem = sm.getSubmodelElements().get(idShort);
		IModelProvider mapProvider = new VABMapProvider((Map<String, Object>) elem);
		return SubmodelElementProvider.getElementProvider((Map<String, Object>) elem, mapProvider);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getNestedPropertyValue(List<String> idShorts) {
		ISubmodelElement lastElement = getNestedSubmodelElement(idShorts);
		IModelProvider mapProvider = new VABMapProvider((Map<String, Object>) lastElement);
		return SubmodelElementProvider.getElementProvider((Map<String, Object>) lastElement, mapProvider)
				.getModelPropertyValue("/value");
	}

	@Override
	public ISubmodelElement getNestedSubmodelElement(List<String> idShorts) {
		// Get sm from db
		SubModel sm = (SubModel) getSubmodel();
		Map<String, ISubmodelElement> elemMap = sm.getSubmodelElements();
		// Get last nested submodel element
		for (int i = 0; i < idShorts.size() - 1; i++) {
			String idShort = idShorts.get(i);
			ISubmodelElement elem = elemMap.get(idShort);
			if (elem instanceof SubmodelElementCollection) {
				elemMap = ((SubmodelElementCollection) elem).getSubmodelElements();
			} else {
				throw new ResourceNotFoundException(
						idShort + " in the nested submodel element path could not be resolved.");
			}
		}
		String lastIdShort = idShorts.get(idShorts.size() - 1);
		return elemMap.get(lastIdShort);
	}

	@Override
	public Object invokeOperation(String idShort, Object... params) {
		// not possible to invoke operations on a submodel that is stored in a db
		throw new MalformedRequestException("Invoke not supported by filesystem");
	}
}
