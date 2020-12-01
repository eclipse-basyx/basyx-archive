package org.eclipse.basyx.extensions.events.submodel.mqtt;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPI;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation variant for the SubmodelAPI that triggers MQTT events for
 * different CRUD operations on the submodel. Has to be based on a backend
 * implementation of the ISubmodelAPI to forward its method calls.
 * 
 * @author espen
 *
 */
public class MqttSubmodelAPI implements ISubmodelAPI {
	private static Logger logger = LoggerFactory.getLogger(MqttSubmodelAPI.class);

	// List of topics
	public static final String TOPIC_ADDSUBMODEL = "registeredSubmodel";
	public static final String TOPIC_ADDELEMENT = "addSubmodelElement";
	public static final String TOPIC_DELETEELEMENT = "removeSubmodelElement";
	public static final String TOPIC_UPDATEELEMENT = "updateSubmodelElement";

	// The underlying SubmodelAPI
	protected ISubmodelAPI observedAPI;

	// The MQTTClient
	protected MqttClient mqttClient;

	// Submodel Element whitelist for filtering
	protected boolean useWhitelist = false;
	protected Set<String> whitelist = new HashSet<>();

	// QoS for MQTT messages (1, 2 or 3).
	protected int qos = 1;

	/**
	 * Constructor for adding this MQTT extension on top of another SubmodelAPI
	 * 
	 * @param observedAPI The underlying submodelAPI
	 * @throws MqttException
	 */
	public MqttSubmodelAPI(ISubmodelAPI observedAPI, String serverEndpoint, String clientId) throws MqttException {
		logger.info("Create new MQTT submodel for endpoint " + serverEndpoint);
		this.observedAPI = observedAPI;
		this.mqttClient = new MqttClient(serverEndpoint, clientId, new MqttDefaultFilePersistence());
		mqttClient.connect();
		sendMqttMessage(TOPIC_ADDSUBMODEL, observedAPI.getSubmodel().getIdentification().getId());
	}

	/**
	 * Constructor for adding this MQTT extension on top of another SubmodelAPI
	 * 
	 * @param observedAPI The underlying submodelAPI
	 * @throws MqttException
	 */
	public MqttSubmodelAPI(ISubmodelAPI observedAPI, String serverEndpoint, String clientId, String user, char[] pw)
			throws MqttException {
		logger.info("Create new MQTT submodel for endpoint " + serverEndpoint);
		this.observedAPI = observedAPI;
		this.mqttClient = new MqttClient(serverEndpoint, clientId, new MqttDefaultFilePersistence());
		MqttConnectOptions options = new MqttConnectOptions();
		options.setUserName(user);
		options.setPassword(pw);
		mqttClient.connect(options);
		sendMqttMessage(TOPIC_ADDSUBMODEL, observedAPI.getSubmodel().getIdentification().getId());
	}

	/**
	 * Returns the connected mqttClient
	 * 
	 * @return
	 */
	public MqttClient getClient() {
		return mqttClient;
	}

	/**
	 * Constructor for adding this MQTT extension on top of another SubmodelAPI.
	 * 
	 * @param observedAPI The underlying submodelAPI
	 * @param client      An already connected mqtt client
	 */
	public MqttSubmodelAPI(ISubmodelAPI observedAPI, MqttClient client) {
		this.observedAPI = observedAPI;
		this.mqttClient = client;
		sendMqttMessage(TOPIC_ADDSUBMODEL, observedAPI.getSubmodel().getIdentification().getId());
	}

	/**
	 * Sets the QoS for MQTT messages
	 * 
	 * @param qos
	 */
	public void setQoS(int qos) {
		if (qos >= 0 && qos <= 3) {
			this.qos = qos;
		} else {
			throw new IllegalArgumentException("Invalid QoS: " + qos);
		}
	}

	/**
	 * Gets the QoS for MQTT messages
	 * 
	 * @param qos
	 */
	public int getQoS() {
		return this.qos;
	}

	/**
	 * Adds a submodel element to the filter whitelist. Can also be a path for nested submodel elements.
	 * 
	 * @param element
	 */
	public void observeSubmodelElement(String shortId) {
		whitelist.add(VABPathTools.stripSlashes(shortId));
	}

	/**
	 * Sets a new filter whitelist.
	 * 
	 * @param element
	 */
	public void setWhitelist(Set<String> shortIds) {
		this.whitelist.clear();
		for (String entry : shortIds) {
			this.whitelist.add(VABPathTools.stripSlashes(entry));
		}
	}

	/**
	 * Disables the submodel element filter whitelist
	 * 
	 * @param element
	 */
	public void disableWhitelist() {
		useWhitelist = false;
	}

	/**
	 * Enables the submodel element filter whitelist
	 * 
	 * @param element
	 */
	public void enableWhitelist() {
		useWhitelist = true;
	}

	@Override
	public ISubModel getSubmodel() {
		return observedAPI.getSubmodel();
	}

	@Override
	public void addSubmodelElement(ISubmodelElement elem) {
		observedAPI.addSubmodelElement(elem);
		if (filter(elem.getIdShort())) {
			sendMqttMessage(TOPIC_ADDELEMENT, elem.getIdShort());
		}
	}

	@Override
	public void addSubmodelElement(String idShortPath, ISubmodelElement elem) {
		observedAPI.addSubmodelElement(idShortPath, elem);
		idShortPath = VABPathTools.stripSlashes(idShortPath);
		if (filter(idShortPath)) {
			sendMqttMessage(TOPIC_ADDELEMENT, idShortPath);
		}
	}

	@Override
	public ISubmodelElement getSubmodelElement(String idShortPath) {
		return observedAPI.getSubmodelElement(idShortPath);
	}

	@Override
	public void deleteSubmodelElement(String idShortPath) {
		observedAPI.deleteSubmodelElement(idShortPath);
		idShortPath = VABPathTools.stripSlashes(idShortPath);
		if (filter(idShortPath)) {
			sendMqttMessage(TOPIC_DELETEELEMENT, idShortPath);
		}
	}

	@Override
	public Collection<IOperation> getOperations() {
		return observedAPI.getOperations();
	}

	@Override
	public Collection<ISubmodelElement> getSubmodelElements() {
		return observedAPI.getSubmodelElements();
	}

	@Override
	public void updateSubmodelElement(String idShortPath, Object newValue) {
		observedAPI.updateSubmodelElement(idShortPath, newValue);
		idShortPath = VABPathTools.stripSlashes(idShortPath);
		if (filter(idShortPath)) {
			sendMqttMessage(TOPIC_UPDATEELEMENT, idShortPath);
		}
	}

	@Override
	public Object getSubmodelElementValue(String idShortPath) {
		return observedAPI.getSubmodelElementValue(idShortPath);
	}

	@Override
	public Object invokeOperation(String idShortPath, Object... params) {
		return observedAPI.invokeOperation(idShortPath, params);
	}

	@Override
	public Object invokeAsync(String idShortPath, Object... params) {
		return observedAPI.invokeAsync(idShortPath, params);
	}

	@Override
	public Object getOperationResult(String idShort, String requestId) {
		return observedAPI.getOperationResult(idShort, requestId);
	}

	private void sendMqttMessage(String topic, String payload) {
		MqttMessage msg = new MqttMessage(payload.getBytes());
		if (this.qos != 1) {
			msg.setQos(this.qos);
		}
		try {
			logger.debug("Send MQTT message to " + topic + ": " + payload);
			mqttClient.publish(topic, msg);
		} catch (MqttPersistenceException e) {
			logger.error("Could not persist mqtt message", e);
		} catch (MqttException e) {
			logger.error("Could not send mqtt message", e);
		}
	}

	private boolean filter(String idShort) {
		return !useWhitelist || whitelist.contains(idShort);
	}
}
