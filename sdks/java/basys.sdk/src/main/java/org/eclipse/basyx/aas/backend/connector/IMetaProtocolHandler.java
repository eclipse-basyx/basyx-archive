package org.eclipse.basyx.aas.backend.connector;


/**
 * 
 * @author zhangzai
 *
 */

public interface IMetaProtocolHandler {
	
	/**
	 * Deserialize the returned JSON String, handle meta-information of the protocol and return response object
	 * 
	 * @param message 
	 * 				serialized JSON String
	 * @return
	 * 				response object with handled meta-information
	 */
	public Object verify(String message) throws Exception;

}
