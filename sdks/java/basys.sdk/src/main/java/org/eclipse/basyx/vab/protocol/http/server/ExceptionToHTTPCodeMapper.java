package org.eclipse.basyx.vab.protocol.http.server;

import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.exception.provider.ResourceAlreadyExistsException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;

/**
 * Maps Exceptions from providers to HTTP-Codes
 * 
 * @author conradi
 *
 */
public class ExceptionToHTTPCodeMapper {

	
	/**
	 * Maps ProviderExceptions to HTTP-Codes
	 * 
	 * @param e The thrown ProviderException
	 * @return HTTP-Code
	 */
	public static int mapException(ProviderException e) {

		if(e instanceof MalformedRequestException) {
			return 400;
		} else if(e instanceof ResourceAlreadyExistsException) {
			return 422;
		} else if(e instanceof ResourceNotFoundException) {
			return 404;
		}
		return 500;
		
	}
	
}
