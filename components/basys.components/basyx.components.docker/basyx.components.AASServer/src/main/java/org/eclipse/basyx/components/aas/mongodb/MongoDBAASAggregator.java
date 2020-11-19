package org.eclipse.basyx.components.aas.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.aggregator.AASAggregator;
import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.aas.restapi.api.IAASAPI;
import org.eclipse.basyx.components.configuration.BaSyxMongoDBConfiguration;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPI;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnectorProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

/**
 * An IAASAggregator for persistent storage in a MongoDB.
 * 
 * @see AASAggregator AASAggregator for the "InMemory"-variant
 * 
 * @author espen
 *
 */
public class MongoDBAASAggregator implements IAASAggregator {
	private static Logger logger = LoggerFactory.getLogger(MongoDBAASAggregator.class);

	private static final String DEFAULT_CONFIG_PATH = "mongodb.properties";
	private static final String IDSHORTPATH = Referable.IDSHORT;
	private static final String IDPATH = Identifiable.IDENTIFICATION + "." + Identifier.ID;

	protected Map<String, VABMultiSubmodelProvider> aasProviderMap = new HashMap<>();
	protected BaSyxMongoDBConfiguration config;
	protected MongoOperations mongoOps;
	protected String aasCollection;
	protected String smCollection;

	private IAASRegistryService registry;

	/**
	 * Receives the path of the configuration.properties file in it's constructor.
	 * 
	 * @param configFilePath
	 */
	public MongoDBAASAggregator(BaSyxMongoDBConfiguration config) {
		this.setConfiguration(config);
		init();
	}

	public void setRegistry(IAASRegistryService registry) {
		this.registry = registry;
	}

	/**
	 * Receives the path of the .properties file in it's constructor from a resource.
	 */
	public MongoDBAASAggregator(String resourceConfigPath) {
		config = new BaSyxMongoDBConfiguration();
		config.loadFromResource(resourceConfigPath);
		this.setConfiguration(config);
		init();
	}

	/**
	 * Constructor using default connections
	 */
	public MongoDBAASAggregator() {
		this(DEFAULT_CONFIG_PATH);
	}

	/**
	 * Sets the db configuration for this Aggregator.
	 * 
	 * @param config
	 */
	public void setConfiguration(BaSyxMongoDBConfiguration config) {
		this.config = config;
		MongoClient client = MongoClients.create(config.getConnectionUrl());
		this.mongoOps = new MongoTemplate(client, config.getDatabase());
		this.aasCollection = config.getAASCollection();
		this.smCollection = config.getSubmodelCollection();
	}

	/**
	 * Removes all persistent AAS and submodels
	 */
	public void reset() {
		mongoOps.dropCollection(aasCollection);
		mongoOps.dropCollection(smCollection);
		aasProviderMap.clear();
	}

	private void init() {
		List<AssetAdministrationShell> data = mongoOps.findAll(AssetAdministrationShell.class, aasCollection);
		for (AssetAdministrationShell aas : data) {
			logger.info("Adding AAS from DB: " + aas.getIdentification().getId());
			VABMultiSubmodelProvider provider = createMultiSubmodelProvider(aas);
			aasProviderMap.put(aas.getIdentification().getId(), provider);
		}
	}

	private VABMultiSubmodelProvider createMultiSubmodelProvider(AssetAdministrationShell aas) {
		IAASAPI aasApi = new MongoDBAASAPI(config, aas.getIdentification().getId());
		AASModelProvider aasProvider = new AASModelProvider(aasApi);
		VABMultiSubmodelProvider provider = new VABMultiSubmodelProvider(aasProvider, registry, new HTTPConnectorProvider());

		// Get ids and idShorts from aas
		Collection<IReference> submodelRefs = aas.getSubmodelReferences();
		List<String> smIds = new ArrayList<>();
		List<String> smIdShorts = new ArrayList<>();
		for (IReference ref : submodelRefs) {
			List<IKey> keys = ref.getKeys();
			IKey lastKey = keys.get(keys.size() - 1);
			if (lastKey.getIdType() == KeyType.IDSHORT) {
				smIdShorts.add(lastKey.getValue());
			} else {
				smIds.add(lastKey.getValue());
			}
		}

		// Add submodel ids by id shorts
		for (String idShort : smIdShorts) {
			String id = getSubmodelId(idShort);
			if (id != null) {
				smIds.add(id);
			}
		}

		// Create a provider for each submodel
		for (String id : smIds) {
			logger.info("Adding Submodel from DB: " + id);
			addSubmodelProvidersById(id, provider);
		}

		return provider;
	}

	private String getSubmodelId(String idShort) {
		SubModel sm = mongoOps.findOne(query(where(IDSHORTPATH).is(idShort)), SubModel.class);
		if ( sm != null ) {
			return sm.getIdentification().getId();
		}
		return null;
	}

	private void addSubmodelProvidersById(String smId, VABMultiSubmodelProvider provider) {
		ISubmodelAPI smApi = new MongoDBSubmodelAPI(smId);
		SubModelProvider smProvider = new SubModelProvider(smApi);
		provider.addSubmodel(smProvider);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<IAssetAdministrationShell> getAASList() {
		return aasProviderMap.values().stream().map(p -> {
			try {
				return p.getModelPropertyValue("/aas");
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new RuntimeException();
			}
		}).map(m -> {
			AssetAdministrationShell aas = new AssetAdministrationShell();
			aas.putAll((Map<? extends String, ? extends Object>) m);
			return aas;
		}).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public IAssetAdministrationShell getAAS(IIdentifier aasId) {
		IModelProvider aasProvider = getAASProvider(aasId);

		// get all Elements from provider
		Map<String, Object> aasMap = (Map<String, Object>) aasProvider.getModelPropertyValue("/aas");
		return AssetAdministrationShell.createAsFacade(aasMap);
	}

	@Override
	public void createAAS(AssetAdministrationShell aas) {
		MongoDBAASAPI aasApi = new MongoDBAASAPI(config, aas.getIdentification().getId());
		aasApi.setAAS(aas);
		AASModelProvider provider = new AASModelProvider(aasApi);
		aasProviderMap.put(aas.getIdentification().getId(), new VABMultiSubmodelProvider(provider));
	}

	@Override
	public void updateAAS(AssetAdministrationShell aas) {
		createAAS(aas);
	}

	@Override
	public void deleteAAS(IIdentifier aasId) {
		Query hasId = query(where(IDPATH).is(aasId));
		mongoOps.remove(hasId, aasCollection);
		aasProviderMap.remove(aasId.getId());
	}

	public VABMultiSubmodelProvider getProviderForAASId(String aasId) {
		return aasProviderMap.get(aasId);
	}

	@Override
	public IModelProvider getAASProvider(IIdentifier aasId) {
		VABMultiSubmodelProvider provider = aasProviderMap.get(aasId.getId());

		if (provider == null) {
			throw new ResourceNotFoundException("AAS with Id " + aasId.getId() + " does not exist");
		}

		return provider;
	}
}
