package org.eclipse.basyx.onem2m.clients;

import org.eclipse.basyx.onem2m.xml.protocols.Rqp;
import org.eclipse.basyx.onem2m.xml.protocols.Rsp;

public interface IOneM2MClient {
	public OneM2MHttpClientConfig getConfig();
	public void start() throws Exception;
	public void start(boolean waitForCSE) throws Exception;
	public void stop() throws Exception;
	public Rqp createDefaultRequest(String path, boolean hierarchical);
	public Rsp send(Rqp rqp) throws Exception;
}
