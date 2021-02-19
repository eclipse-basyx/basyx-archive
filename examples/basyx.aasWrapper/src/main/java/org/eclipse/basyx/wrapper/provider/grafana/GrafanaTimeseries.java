package org.eclipse.basyx.wrapper.provider.grafana;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.wrapper.receiver.PropertyResult;

/**
 * Conversion class for Grafana timeseries response
 * 
 * @author espen
 *
 */
public class GrafanaTimeseries extends ArrayList<Object> {
	private static final long serialVersionUID = 1L;
	public GrafanaTimeseries() {
		super();
	}

	public void addTarget(String idShort, PropertyResult result) {
		Map<String, Object> targetData = new HashMap<>();
		
		List<Object> values = result.getData();
		List<String> timestamps = result.getTimestamps();
		List<List<Object>> dataPoints = new ArrayList<>(values.size());
		for ( int i = 0; i < values.size(); i++ ) {
			List<Object> entry = new ArrayList<>(2);
			entry.add(values.get(i));
			try {
				Date date = PropertyResult.DATE_FORMAT.parse(timestamps.get(i));
				long ms = date.getTime();
				entry.add(ms);
			} catch (ParseException e) {
				e.printStackTrace();
				break;
			}
			// entry.add(Integer.parseInt(timestamps.get(i))); // add string formatted timestamp
			dataPoints.add(entry);
		}
		targetData.put("target", idShort);
		targetData.put("datapoints", dataPoints);
		this.add(targetData);
	}
}
