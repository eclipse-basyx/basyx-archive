package org.eclipse.basyx.vab.coder.json.metaprotocol;


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
	public Object deserialize(String message) throws Exception;

}
