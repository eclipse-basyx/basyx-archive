package org.eclipse.basyx.aas.aggregator.proxy;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.vab.coder.json.connector.JSONConnector;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.protocol.http.connector.HTTPConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AASAggregatorProxy implements IAASAggregator {
	private static Logger logger = LoggerFactory.getLogger(AASRegistryProxy.class);
	private IModelProvider provider;
	
	/**
	 * Constructor for an AAS aggregator proxy based on a HTTP connection
	 * 
	 * @param aasAggregatorURL
	 *            The endpoint of the aggregator with a HTTP-REST interface
	 */
	public AASAggregatorProxy(String aasAggregatorURL) {
		this(new JSONConnector(new HTTPConnector(aasAggregatorURL + "/" + AASAggregatorProvider.PREFIX)));
	}

	/**
	 * Constructor for an AAS aggregator proxy based on an arbitrary
	 * {@link IModelProvider}
	 * 
	 * @param provider
	 */
	public AASAggregatorProxy(IModelProvider provider) {
		this.provider = new VABElementProxy("/aasList", provider);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<IAssetAdministrationShell> getAASList() {
		Collection<Map<String, Object>> collection = (Collection<Map<String, Object>>) provider.getModelPropertyValue("");
		logger.debug("Getting all AAS");
		return collection.stream().map(m -> AssetAdministrationShell.createAsFacade(m)).collect(Collectors.toSet());
	}

	@SuppressWarnings("unchecked")
	@Override
	public IAssetAdministrationShell getAAS(IIdentifier aasId) {
		logger.debug("Getting AAS with id " + aasId);
		return AssetAdministrationShell.createAsFacade((Map<String, Object>) provider.getModelPropertyValue(getEncodedIdentifier(aasId)));
	}

	@Override
	public void createAAS(AssetAdministrationShell aas) {
		provider.createValue("", aas);
		logger.info("AAS with Id " + aas.getIdentification().getId() + " created");
	}

	@Override
	public void updateAAS(AssetAdministrationShell aas) {
		provider.setModelPropertyValue(getEncodedIdentifier(aas.getIdentification()), aas);
		logger.info("AAS with Id " + aas.getIdentification().getId() + " updated");
	}

	@Override
	public void deleteAAS(IIdentifier aasId) {
		provider.deleteValue(getEncodedIdentifier(aasId));
		logger.info("AAS with Id " + aasId.getId() + " created");
	}

	@Override
	public IModelProvider getAASProvider(IIdentifier aasId) {
		return new VABElementProxy(getEncodedIdentifier(aasId), provider);
	}

	private String getEncodedIdentifier(IIdentifier aasId) {
		return VABPathTools.encodePathElement(aasId.getId());
	}

}
