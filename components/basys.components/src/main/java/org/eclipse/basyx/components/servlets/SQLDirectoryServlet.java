package org.eclipse.basyx.components.servlets;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.basyx.components.sqlprovider.driver.SQLDriver;
import org.eclipse.basyx.tools.aasdescriptor.AASDescriptor;
import org.eclipse.basyx.vab.backend.server.http.BasysHTTPServlet;




/**
 * SQL database based directory provider
 * 
 * This directory provider provides a static directory. It therefore only supports get() operations. 
 * Modification of the directory via PUT/POST/PATCH/DELETE operations is not supported.
 * 
 * @author kuhn
 *
 */
public class SQLDirectoryServlet extends BasysHTTPServlet {

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	

	/**
	 * Configuration properties (raw input from file)
	 */
	protected Properties properties = null;
	
	
	/**
	 * Uplink server
	 */
	protected String uplink = null;
	
	
	/**
	 * Downlink servers
	 */
	protected Map<String, String> downlinks = new HashMap<>();
	

	/**
	 * Path to database
	 */
	protected String path = null;
	
	
	/**
	 * SQL user name
	 */
	protected String user = null;
	
	
	/**
	 * SQL password
	 */
	protected String pass = null;
	
	
	/**
	 * SQL query prefix
	 */
	protected String qryPfx = null;
	
	
	/**
	 * SQL driver class name
	 */
	protected String qDrvCls = null;
	
	
	/**
	 * SQL driver instance
	 */
	protected SQLDriver sqlDriver = null;
	
	
	/**
	 * Constructor
	 */
	public SQLDirectoryServlet() {
		// Invoke base constructor
		super();
	}
	
	/**
	 * Adds init parameter to servlet
	 */
	@Override
	public String getInitParameter(String name) {

		if (name.equals("config")) return "/WebContent/WEB-INF/config/directory/sqldirectory/directory.properties";
		
		return null;
	}
	
	/**
	 * Load a property
	 */
	protected String extractProperty(Properties prop, String key) {
		// Check if properties contain value
		if (!prop.containsKey(key)) return null;
		
		// Extract and remove value
		String value = (String) prop.get(key);
		prop.remove(key);
		
		// Return value
		return value;
	}
	
	
	
	/**
	 * Extract property keys with prefix and suffix. Prefix and suffix is removed from key.
	 */
	protected Collection<String> getProperties(Properties prop, String prefix, String suffix) {
		// Store result
		HashSet<String> result = new HashSet<>();
		
		// Iterate keys
		for (String key: prop.stringPropertyNames()) {
			if (key.startsWith(prefix) && key.endsWith(suffix)) result.add(key.substring(prefix.length(), key.length()-suffix.length()));
		}
		
		// Return result
		return result;
	}
	
	
	
	/**
	 * Extract downlink servers
	 */
	protected Map<String, String> extractDownlinks(Properties prop) {
		// Return value
		Map<String, String> result = new HashMap<>();
		
		// Downlink server names
		Collection<String> downlinkServerNames = getProperties(prop, "cfg.downlink.", ".pattern");
		
		// Remove downlink pattern and server URL
		for (String name: downlinkServerNames) {
			// Get downlink pattern and server URL
			result.put(prop.getProperty("cfg.downlink."+name+".pattern"), prop.getProperty("cfg.downlink."+name+".directory"));
			// Remove pattern and directory properties
			prop.remove("cfg.downlink."+name+".pattern");
			prop.remove("cfg.downlink."+name+".directory");
		}
		
		// Return downlink server mappings
		return result;
	}
	
	
	
	/**
	 * Load properties from file
	 */
	protected void loadProperties(String cfgFilePath) {
		// Read property file
		try {
			// Open property file
			InputStream input = getServletContext().getResourceAsStream(cfgFilePath); 

			// Instantiate property structure
			properties = new Properties();
			properties.load(input);
			
			// Process properties
			// - Uplink server
			uplink = extractProperty(properties, "cfg.uplink");
			// - Downlink servers
			downlinks = extractDownlinks(properties);

			// SQL parameter
			path    = properties.getProperty("dburl");
			user    = properties.getProperty("dbuser");
			pass    = properties.getProperty("dbpass");
			qryPfx  = properties.getProperty("sqlPrefix");
			qDrvCls = properties.getProperty("sqlDriver");
			
		} catch (IOException e) {
			// Output exception
			e.printStackTrace();
		}		
	}



	/**
	 * Initialize servlet
	 * 
	 * @throws ServletException 
	 */
	@Override
	public void init() throws ServletException {
		// Call base implementation
		super.init();
		
		// Read configuration values
		String configFilePath = getInitParameter("config");
		// - Read property file
		loadProperties(configFilePath);
		
		
		// Create SQL driver instance
		sqlDriver = new SQLDriver(path, user, pass, qryPfx, qDrvCls);
	}
	
	/**
	 * Get requested tags as collection
	 */
	protected Collection<String> getTagsAsCollection(String tags) {
		// Collection stores AAS tags
		Collection<String> alltags = new HashSet<>();
		
		// Catch null pointer exceptions
		try {
			// Get AAS tags
			String[] splitTags  = tags.split(",");

			// Only add non-empty tags
			for (String tag: splitTags) if (tag.trim().length() > 0) alltags.add(tag.trim());
		} catch (NullPointerException e) {}

		// Return all tags
		return alltags;
	}
	
	
	
	
	/**
	 * Implement "Get" operation 
	 * 
	 * Process HTTP get request - get sub model property value
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Process request depending on the path and on parameter
		String uri 			= req.getRequestURI();
		String contextPath  = req.getContextPath();
		String path 		= URLDecoder.decode(uri.substring(contextPath.length()+1).substring(req.getServletPath().length()), "UTF-8"); // plus 1 for "/"
				
		// Setup HTML response header
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		
		// Process get request
		// - Get all (local) AAS
		if (path.equals("api/v1/registry")) {
			System.out.println("Getting all");

			// Query database
			ResultSet resultSet = sqlDriver.sqlQuery("SELECT * FROM directory.directory");
			
			// Write result
			StringBuilder result = new StringBuilder();
			
			// Create result
			try {
				// Get results
				while (resultSet.next()) result.append(resultSet.getString("ElementRef")); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			// Send result back
			sendResponse(result.toString(), resp.getWriter());
			// End processing
			return;
		}
		
		// Get a specific AAS
		if (path.startsWith("api/v1/registry/")) {
			System.out.println("Getting:"+path.substring(new String("api/v1/registry/").length()));
			
			// Run query
			ResultSet resultSet = sqlDriver.sqlQuery("SELECT * FROM directory.directory WHERE \"ElementID\" = '"+path.substring(new String("api/v1/registry/").length())+"'");

			// Write result
			StringBuilder result = new StringBuilder();
			
			// Create result
			try {
				// Get results
				while (resultSet.next()) result.append(resultSet.getString("ElementRef")); 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			// Send result back
			sendResponse(serializer.deserialize(result.toString()), resp.getWriter());
			
			// End processing
			return;
		}
	}

	
	
	/**
	 * Implement "Put" operation. Updates a directory entry.
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Process request depending on the path and on parameter
		String uri 			= req.getRequestURI();
		String contextPath  = req.getContextPath();
		String path 		= URLDecoder.decode(uri.substring(contextPath.length()+1).substring(req.getServletPath().length()), "UTF-8"); // plus 1 for "/"

	 	// Read request body
		InputStreamReader reader    = new InputStreamReader(req.getInputStream());
		BufferedReader    bufReader = new BufferedReader(reader);
		StringBuilder     aasValue  = new StringBuilder(); 
		while (bufReader.ready()) aasValue.append(bufReader.readLine());

		// Check path
		System.out.println("Putting:"+path);
		if (!path.startsWith("api/v1/registry/")) {
			System.out.println("Exception");
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong request path"); return;
		}

		// Extract AAS ID
		String aasID = path.substring(new String("api/v1/registry/").length());

		// Update AAS registry
		sqlDriver.sqlUpdate("UPDATE directory.directory SET \"ElementRef\" = '"+aasValue.toString()+"' WHERE \"ElementID\"='"+aasID+"';");
	}


	
	/**
	 * <pre>
	 * Handle HTTP POST operation. Creates a new Property, Operation, Event, Submodel or AAS or invokes an operation.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	 	// Read request body
		InputStreamReader reader    = new InputStreamReader(req.getInputStream());
		BufferedReader    bufReader = new BufferedReader(reader);
		StringBuilder     aasValue  = new StringBuilder(); 
		while (bufReader.ready()) aasValue.append(bufReader.readLine());
		
		// Deserialize AAS value into HashMap
		Map<String, Object> values = (Map<String, Object>) serializer.deserialize(aasValue.toString());
		AASDescriptor       aasDescriptor = new AASDescriptor(values);

		// Extract AAS ID
		String aasID = aasDescriptor.getId();

		// Update AAS registry
		sqlDriver.sqlUpdate("INSERT INTO directory.directory (\"ElementRef\", \"ElementID\") VALUES ('"+aasValue.toString()+"', '"+aasID+"');");
	}


	
	/**
	 * Handle a HTTP PATCH operation. 
	 * 
	 */
	@Override
	protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Indicate an unsupported operation
		resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Request not implemented for this service");
	}
	 

	
	 /**
	 * Implement "Delete" operation.  Deletes any resource under the given path.
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Process request depending on the path and on parameter
		String uri 			= req.getRequestURI();
		String contextPath  = req.getContextPath();
		String path 		= URLDecoder.decode(uri.substring(contextPath.length()+1).substring(req.getServletPath().length()), "UTF-8"); // plus 1 for "/"

		// Check path
		System.out.println("Deleting:"+path);
		if (!path.startsWith("api/v1/registry/")) {
			System.out.println("Exception");
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong request path"); return;
		}

		// Extract AAS ID
		String aasID = path.substring(new String("api/v1/registry/").length());

		// Delete element
		// - Delete element from table "directory"
		sqlDriver.sqlUpdate("DELETE FROM directory.directory WHERE \"ElementID\"='"+aasID+"';");
	}
}

