package org.eclipse.basyx.components.aas.mqtt;

import java.util.Set;

import org.eclipse.basyx.components.configuration.BaSyxMqttConfiguration;
import org.eclipse.basyx.extensions.submodel.mqtt.MqttSubmodelAPI;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPI;
import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPIFactory;
import org.eclipse.basyx.submodel.restapi.vab.VABSubmodelAPI;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Api provider for constructing a new Submodel API that emits MQTT events
 * 
 * @author espen
 */
public class MqttSubmodelAPIFactory implements ISubmodelAPIFactory {
	private static Logger logger = LoggerFactory.getLogger(MqttSubmodelAPIFactory.class);

	private BaSyxMqttConfiguration config;

	/**
	 * Constructor with MQTT configuration for providing submodel APIs
	 * 
	 * @param config
	 */
	public MqttSubmodelAPIFactory(BaSyxMqttConfiguration config) {
		this.config = config;
	}

	@Override
	public ISubmodelAPI getSubmodelAPI(IModelProvider smProvider) {
		// Get the submodel's id from the given provider
		String idPath = VABPathTools.concatenatePaths(Identifiable.IDENTIFICATION, Identifier.ID);
		String smId = (String) smProvider.getModelPropertyValue(idPath);
		
		// Create the API
		VABSubmodelAPI observedApi = new VABSubmodelAPI(smProvider);

		// Configure the API according to the given configs
		String brokerEndpoint = config.getServer();
		String clientId = smId;
		MqttSubmodelAPI api;
		try {
			if (config.getUser() != null) {
				String user = config.getUser();
				String pass = config.getPass();
				api = new MqttSubmodelAPI(observedApi, brokerEndpoint, clientId, user, pass.toCharArray());
			} else {
				api = new MqttSubmodelAPI(observedApi, brokerEndpoint, clientId);
			}
			setWhitelist(api, smId);
		} catch (MqttException e) {
			logger.error("Could not create MqttSubmodelApi", e);
			return observedApi;
		}
		return api;
	}

	private void setWhitelist(MqttSubmodelAPI api, String smId) {
		if (!config.isWhitelistEnabled(smId)) {
			// Do not use the whitelist if it has been disabled
			api.disableWhitelist();
			return;
		}

		// Read whitelist from configuration
		Set<String> whitelist = config.getWhitelist(smId);

		logger.info("Set MQTT whitelist for " + smId + " with " + whitelist.size() + " entries");
		api.setWhitelist(whitelist);
		api.enableWhitelist();
	}
}
