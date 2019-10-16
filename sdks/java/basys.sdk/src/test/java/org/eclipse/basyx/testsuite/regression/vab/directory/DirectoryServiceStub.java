package org.eclipse.basyx.testsuite.regression.vab.directory;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.directory.api.IVABDirectoryService;

/**
 * A simple Directory Service stub providing a mapping based on a Map
 * 
 * @author schnicke
 *
 */
public class DirectoryServiceStub implements IVABDirectoryService {
	private Map<String, Object> addressMap = new HashMap<>();

	@Override
	public void removeMapping(String key) {
		addressMap.remove(key);
	}

	@Override
	public String lookup(String id) {
		return (String) addressMap.get(id);
	}

	@Override
	public IVABDirectoryService addMapping(String key, String value) {
		addressMap.put(key, value);
		
		return this;
	}
}
