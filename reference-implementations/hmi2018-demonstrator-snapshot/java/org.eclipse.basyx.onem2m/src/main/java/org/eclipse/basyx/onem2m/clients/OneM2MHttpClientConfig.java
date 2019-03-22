package org.eclipse.basyx.onem2m.clients;

public class OneM2MHttpClientConfig {

	public enum Protocol {
		HTTPS,
		HTTP
	}
	
	public Protocol protocol;
	public String host;
	public int port;
	public String from;
	
	public OneM2MHttpClientConfig(Protocol _protocol, String _host, int _port, String _from) {
		this.protocol = _protocol;
		this.host = _host;
		this.port = _port;
		this.from = _from;
	}
	
}
