package org.eclipse.basyx.submodel.restapi.api;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Interface for providing an Submodel API
 * 
 * @author espen
 *
 */
public interface ISubmodelAPIFactory {
	/**
	 * Return a constructed Submodel API
	 * 
	 * @return
	 */
	public ISubmodelAPI getSubmodelAPI(IModelProvider submodelProvider);
}
