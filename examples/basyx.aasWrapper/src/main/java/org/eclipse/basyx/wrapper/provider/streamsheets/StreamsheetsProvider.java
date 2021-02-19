package org.eclipse.basyx.wrapper.provider.streamsheets;

import org.eclipse.basyx.wrapper.provider.GenericWrapperProvider;

/**
 * Streamsheets-specific proxy provider
 * 
 * @author espen
 */
public class StreamsheetsProvider extends GenericWrapperProvider {
	public static final String TYPE = "STREAMSHEETS";

	public StreamsheetsProvider() {
		this.providerPath = "/streamsheets";
	}
}