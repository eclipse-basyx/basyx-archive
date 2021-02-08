package org.eclipse.basyx.aas.restapi;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.restapi.api.IAASAPI;
import org.eclipse.basyx.aas.restapi.api.IAASAPIFactory;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;

/**
 * AAS API provider that provides the default VAB AAS API
 * 
 * @author espen
 */
public class VABAASAPIFactory implements IAASAPIFactory {
	@Override
	public IAASAPI getAASApi(AssetAdministrationShell aas) {
		return new VABAASAPI(new VABLambdaProvider(aas));
	}
}
