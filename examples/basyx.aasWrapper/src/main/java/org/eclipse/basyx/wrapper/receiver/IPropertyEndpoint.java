package org.eclipse.basyx.wrapper.receiver;

/**
 * Represents a single property endpoint
 * 
 * @author espen
 *
 */
public interface IPropertyEndpoint {
	public DataPoint retrieveValue();

	public void setValue(Object newValue);
}
