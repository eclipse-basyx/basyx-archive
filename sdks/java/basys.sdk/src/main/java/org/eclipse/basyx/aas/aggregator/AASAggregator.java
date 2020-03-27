package org.eclipse.basyx.aas.aggregator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;

/**
 * An implementation of the IAASAggregator interface using maps internally
 * 
 * @author conradi
 *
 */
public class AASAggregator implements IAASAggregator {

	protected Map<String, AASModelProvider> aasProviderMap = new HashMap<>();

	@SuppressWarnings("unchecked")
	@Override
	public Collection<IAssetAdministrationShell> getAASList() {
		return aasProviderMap.values().stream().map(p -> {
			try {
				return p.getModelPropertyValue("");
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
		AASModelProvider provider = aasProviderMap.get(aasId.getId());

		AssetAdministrationShell aas = null;

		try {
			// get all Elements from provider
			Map<String, Object> aasMap = (Map<String, Object>) provider.getModelPropertyValue("");
			aas = AssetAdministrationShell.createAsFacade(aasMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return aas;
	}

	@Override
	public void createAAS(AssetAdministrationShell aas) {
		aasProviderMap.put(aas.getIdentification().getId(), new AASModelProvider(aas));
	}

	@Override
	public void updateAAS(AssetAdministrationShell aas) {
		aasProviderMap.put(aas.getIdentification().getId(), new AASModelProvider(aas));
	}

	@Override
	public void deleteAAS(IIdentifier aasId) {
		aasProviderMap.remove(aasId.getId());
	}
}