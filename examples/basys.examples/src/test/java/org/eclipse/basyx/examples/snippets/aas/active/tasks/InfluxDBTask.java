package org.eclipse.basyx.examples.snippets.aas.active.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.eclipse.basyx.tools.aas.active.VABModelTask;
import org.eclipse.basyx.vab.core.IModelProvider;

/**
 * Task for writing a value to an influxDB
 * 
 * @author espen
 *
 */
public class InfluxDBTask implements VABModelTask {
	private String modelPath;
	private String dbUrl;
	private String dbName;
	private String valueName;

	public InfluxDBTask(String modelPath, String dbUrl, String dbName, String valueName) {
		this.modelPath = modelPath;
		this.dbUrl = dbUrl;
		this.dbName = dbName;
		this.valueName = valueName;
		clearData();
	}

	@Override
	public void execute(IModelProvider model) throws Exception {
		try {
			Object value = model.getModelPropertyValue(modelPath);
			String result = value == null ? "null" : value.toString();
			writeData(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeData(String value) {
		System.out.println("Writing " + value + " to " + dbName + "." + valueName);
		try {
			postInfluxDBCommand("write", valueName + " value=" + value);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void clearData() {
		System.out.println("Clearing previous InfluxDB entries for value: " + valueName);
		try {
			postInfluxDBCommand("query", "q=DROP MEASUREMENT " + valueName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void postInfluxDBCommand(String queryType, String queryCommand) throws IOException {
		URL url = new URL(dbUrl + queryType + "?db=" + dbName);
		HttpURLConnection con = (HttpURLConnection) (url).openConnection();
		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.connect();
		con.getOutputStream().write((queryCommand + "\n").getBytes());

		InputStream is = con.getInputStream();
		byte[] b = new byte[1024];
		String buffer = "";
		while (is.read(b) != -1) {
			buffer.concat(new String(b));
		}
		con.disconnect();
	}

}
