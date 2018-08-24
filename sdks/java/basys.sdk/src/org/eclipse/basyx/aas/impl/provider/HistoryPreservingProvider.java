package org.eclipse.basyx.aas.impl.provider;

import java.util.Map;

import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.services.IModelProvider;
import org.eclipse.basyx.aas.impl.provider.filesystem.TimeProvider;

/**
 * Wraps two model providers. 
 * The first is used to hold the live data, the second one is used to save the historical data
 * @author schnicke
 *
 */
public class HistoryPreservingProvider implements IModelProvider {

	private IModelProvider mainProvider;
	private IModelProvider historyProvider;
	private TimeProvider timeProvider;

	public HistoryPreservingProvider(IModelProvider mainProvider, IModelProvider historyProvider,
			TimeProvider timeProvider) {
		super();
		this.mainProvider = mainProvider;
		this.historyProvider = historyProvider;
		this.timeProvider = timeProvider;
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		mainProvider.setModelPropertyValue(path, newValue);
		saveHistory(path);
	}

	@Override
	public void setModelPropertyValue(String address, Object... newEntry) throws Exception {
		mainProvider.setModelPropertyValue(address, newEntry);
		saveHistory(address);
	}

	@Override
	public void createValue(String address, Object newEntity) throws Exception {
		mainProvider.createValue(address, newEntity);
		saveHistory(address);
	}

	private void saveHistory(String address) throws Exception {
		Object o = mainProvider.getModelPropertyValue(address);
		historyProvider.createValue(address +"_" +  timeProvider.getCurrentTime().toString().replace(":", "_"), o);
	}

	@Override
	public String getElementScope(String elementPath) {
		return mainProvider.getElementScope(elementPath);
	}

	@Override
	public Object getModelPropertyValue(String path) {
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
	public Object invokeOperation(String path, Object[] parameter) throws Exception {
		return mainProvider.invokeOperation(path, parameter);
	}

	@Override
	public Map<String, IElementReference> getContainedElements(String path) {
		return mainProvider.getContainedElements(path);
	}

}
