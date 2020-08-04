package org.eclipse.basyx.aas.registration.memory;

import java.util.HashMap;

/**
 * An implementation of the IAASRegistryService interface.
 * This registry can not store its entries permanently, because it is completely based on HashMaps.
 * 
 * @author espen
 *
 */
public class InMemoryRegistry extends AASRegistry {

	/**
	 * Default constructor based on HashMaps
	 */
	public InMemoryRegistry() {
		super(new MapRegistryHandler(new HashMap<>()));
	}
}
