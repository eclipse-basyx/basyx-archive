package org.eclipse.basyx.wrapper.provider;

/**
 * A simple, generic HTTP-REST model provider
 * 
 * @author espen
 *
 */
public interface HTTPModelProvider {
	public Object get(String path);

	public Object delete(String path);

	public Object put(String path, Object data);

	public Object post(String path, Object data);

	public Object patch(String path, Object data);
}
