package org.eclipse.basyx.aas.impl.provider;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.ICollectionProperty;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.IContainerProperty;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.IMapProperty;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.IProperty;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.ISingleProperty;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.ISubModel;
import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.provider.filesystem.TimeProvider;

/**
 * Wraps two model providers. The first is used to hold the live data, the
 * second one is used to save the historical data
 * 
 * @author schnicke
 *
 */
public class HistoryPreservingProvider implements IModelProvider {

	private IModelProvider mainProvider;
	private IModelProvider historyProvider;
	private TimeProvider timeProvider;

	public HistoryPreservingProvider(IModelProvider mainProvider, IModelProvider historyProvider, TimeProvider timeProvider) {
		super();
		this.mainProvider = mainProvider;
		this.historyProvider = historyProvider;
		this.timeProvider = timeProvider;
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		mainProvider.setModelPropertyValue(path, newValue);
		saveHistory(path, newValue);
	}

	@Override
	public void createValue(String address, Object newEntity) throws Exception {
		mainProvider.createValue(address, newEntity);
		saveHistory(address, newEntity);
	}

	private void saveHistory(String address, Object newEntity) throws Exception {
		saveRecursive(address, newEntity, timeProvider.getCurrentTime().toString().replace(":", "_").replace(".", "_"));
	}

	private void saveRecursive(String address, Object newEntity, String timeStamp) throws Exception {
		if (newEntity instanceof IProperty) {
			String separator;
			if (isPathNestedProperty(address)) {
				separator = ".";
			} else {
				separator = "/";
			}
			String newAddress = address + separator + ((IProperty) newEntity).getId();
			if (newEntity instanceof ISingleProperty) {
				saveRecursive(newAddress, ((ISingleProperty) newEntity).get(), timeStamp);
			} else if (newEntity instanceof IMapProperty) {
				// Copy map since there is no getter for the contained map
				Map<String, Object> map = new HashMap<>();
				IMapProperty mapProp = (IMapProperty) newEntity;
				for (String key : mapProp.getKeys()) {
					map.put(key, mapProp.getValue(key));
				}
				saveRecursive(newAddress, map, timeStamp);
			} else if (newEntity instanceof IContainerProperty) {
				IContainerProperty container = (IContainerProperty) newEntity;
				for (String key : container.getProperties().keySet()) {
					saveRecursive(newAddress, container.getProperties().get(key), timeStamp);
				}
			} else if (newEntity instanceof ICollectionProperty) {
				saveRecursive(newAddress, ((ICollectionProperty) newEntity).getElements(), timeStamp);
			} else {
				throw new RuntimeException("Unknown property " + newEntity);
			}
		} else if (newEntity instanceof ISubModel) {
			ISubModel submodel = ((ISubModel) newEntity);
			for (String p : submodel.getProperties().keySet()) {
				saveRecursive(address + "/" + submodel.getId() + "/properties", submodel.getProperties().get(p), timeStamp);
			}
		} else if (newEntity instanceof IAssetAdministrationShell) {
			IAssetAdministrationShell shell = (IAssetAdministrationShell) newEntity;
			for (String s : shell.getSubModels().keySet()) {
				saveRecursive(address + "/" + shell.getId() + "/aas/submodels", shell.getSubModels().get(s), timeStamp);
			}
		} else {
			historyProvider.createValue(address + "_" + timeStamp, newEntity);
		}
	}

	// TODO: Move this to BaSysID
	private boolean isPathNestedProperty(String address) {
		int index = address.indexOf("/properties");
		return address.length() > index + "/properties".length();
	}

	@Override
	public Object getModelPropertyValue(String path) throws Exception {
		return mainProvider.getModelPropertyValue(path);
	}

	@Override
	public void deleteValue(String path) throws Exception {
		mainProvider.deleteValue(path);
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		mainProvider.deleteValue(path);
	}

	@Override
	public Object invokeOperation(String path, Object... parameter) throws Exception {
		return mainProvider.invokeOperation(path, parameter);
	}

}
