package org.eclipse.basyx.submodel.restapi.vab;

import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPI;
import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPIFactory;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Submodel API provider that provides the default VAB Submodel API
 * 
 * @author espen
 *
 */
public class VABSubmodelAPIFactory implements ISubmodelAPIFactory {
	@Override
	public ISubmodelAPI getSubmodelAPI(IModelProvider submodelProvider) {
		return new VABSubmodelAPI(submodelProvider);
	}
}
