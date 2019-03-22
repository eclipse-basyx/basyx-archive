package org.eclipse.basyx.testsuite.support.vab.stub;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.core.IDirectoryService;

/**
 * A simple Directory Service stub providing a mapping based on a Map
 * 
 * @author schnicke
 *
 */
public class DirectoryServiceStub implements IDirectoryService {
	private Map<String, String> addressMap = new HashMap<>();

	@Override
	public void removeMapping(String key) {
		addressMap.remove(key);
	}

	@Override
	public String lookup(String id) {
		return addressMap.get(id);
	}

	@Override
	public Map<String, String> getMappings() {
		return addressMap;
	}

	@Override
	public void addMapping(String key, String value) {
		addressMap.put(key, value);

	}
}
