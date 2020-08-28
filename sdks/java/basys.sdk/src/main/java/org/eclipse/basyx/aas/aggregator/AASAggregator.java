package org.eclipse.basyx.aas.aggregator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * An implementation of the IAASAggregator interface using maps internally
 * 
 * @author conradi, schnicke
 *
 */
public class AASAggregator implements IAASAggregator {

	protected Map<String, VABMultiSubmodelProvider> aasProviderMap = new HashMap<>();

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
		IAssetAdministrationShell aas = AssetAdministrationShell.createAsFacade(aasMap);

		return aas;
	}

	@Override
	public void createAAS(AssetAdministrationShell aas) {
		aasProviderMap.put(aas.getIdentification().getId(), new VABMultiSubmodelProvider(new AASModelProvider(aas)));
	}

	@Override
	public void updateAAS(AssetAdministrationShell aas) {
		aasProviderMap.put(aas.getIdentification().getId(), new VABMultiSubmodelProvider(new AASModelProvider(aas)));
	}

	@Override
	public void deleteAAS(IIdentifier aasId) {
		aasProviderMap.remove(aasId.getId());
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