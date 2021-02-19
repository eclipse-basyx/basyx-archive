package org.eclipse.basyx.wrapper.receiver;

import java.util.Date;

/**
 * Represents a single datapoint for a property
 * 
 * @author espen
 *
 */
public class DataPoint {
    private Date timestamp;
    private Object value;

	public DataPoint(Object value) {
		this.timestamp = new Date();
		this.value = value;
	}

    public DataPoint(Date timestamp, Object value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}