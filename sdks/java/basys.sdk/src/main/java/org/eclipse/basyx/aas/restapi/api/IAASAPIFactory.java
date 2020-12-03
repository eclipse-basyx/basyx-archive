package org.eclipse.basyx.aas.restapi.api;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Interface for providing an AAS API
 * 
 * @author espen
 *
 */
public interface IAASAPIFactory {
	/**
	 * Return a constructed AAS API based on a raw model provider
	 * 
	 * @return
	 */
	public IAASAPI getAASApi(IModelProvider aasProvider);
}
