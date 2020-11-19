package org.eclipse.basyx.sandbox.components.registry;


import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.basyx.sandbox.components.registry.exception.AASDirectoryProviderException;
import org.eclipse.basyx.vab.protocol.http.server.BasysHTTPServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * Static configuration file based directory provider
 * 
 * This directory provider provides a static directory. It therefore only supports get() operations. 
 * Modification of the directory via PUT/POST/PATCH/DELETE operations is not supported.
 * 
 * @author kuhn
 *
 */
public class StaticCFGDirectoryServlet extends BasysHTTPServlet {
	
	/**
	 * Initiates a logger using the current class
	 */
	private static final Logger logger = LoggerFactory.getLogger(StaticCFGDirectoryServlet.class);

	
	/**
	 * Version information to identify the version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	

	/**
	 * Configuration properties (raw input from file)
	 */
	protected Properties properties = null;
	
	
	/**
	 * Asset administration shells by ID
	 */
	protected Map<String, AASDirectoryEntry> aasByID = new HashMap<>();
	
	
	/**
	 * Asset administration shells by tag
	 */
	protected Map<String, Collection<AASDirectoryEntry>> aasByTag = new HashMap<>();
	
	
	/**
	 * Uplink server
	 */
	protected String uplink = null;
	
	
	/**
	 * Downlink servers
	 */
	protected Map<String, String> downlinks = new HashMap<>();
	
	
	
	
	
	/**
	 * Constructor
	 */
	public StaticCFGDirectoryServlet() {
		// Invoke base constructor
		super();
	}
	
	/**
	 * Adds init parameter to servlet
	 */
	@Override
	public String getInitParameter(String name) {

		if (name.equals("config")) return "/WebContent/WEB-INF/config/directory/cfgdirectory/directory.properties";
		
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
	 * Extract Asset Administration Shell definitions
	 */
	protected Map<String, AASDirectoryEntry> extractAAS(Properties prop) {
		// Return value
		Map<String, AASDirectoryEntry> result = new HashMap<>();
		
		// Get AAS IDs
		Collection<String> aasIDs = getProperties(prop, "", ".id");
		
		// Create AAS directory entries from properties
		for (String aasID : aasIDs) {
			// Create AAS directory entry
			AASDirectoryEntry entry = new AASDirectoryEntry(prop.getProperty(aasID+".id"), prop.getProperty(aasID+".aas"), prop.getProperty(aasID+".type"), prop.getProperty(aasID+".tags"));
			
			// Add AAS directory entry
			result.put(prop.getProperty(aasID+".id"), entry);
		}
		
		// Return ID to AAS mappings
		return result;
	}
	
	
	
	/**
	 * Map AAS tags to AAS
	 */
	protected Map<String, Collection<AASDirectoryEntry>> mapAASToTags(Map<String, AASDirectoryEntry> aasByID) {
		// Return value
		Map<String, Collection<AASDirectoryEntry>> result = new HashMap<>();
		
		// Iterate AAS directory entries
		for (AASDirectoryEntry dirEntry: aasByID.values()) {
			// Process tags
			for (String tag: dirEntry.getAASTags()) {
				// Create tag if necessary
				if (!result.containsKey(tag)) {result.put(tag, new HashSet<AASDirectoryEntry>());}
				
				// Add AAS to tag
				result.get(tag).add(dirEntry);
			}
		}
		
		// Return HashTag to AAS mappings
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
			
			logger.debug("properties:"+properties);
			logger.debug("properties (keys):"+properties.keySet());
			logger.debug("properties (cfg.downlink.is.pattern):"+properties.get("cfg.downlink.is.pattern"));
			
			// Process properties
			// - Uplink server
			uplink = extractProperty(properties, "cfg.uplink");
			// - Downlink servers
			downlinks = extractDownlinks(properties);
			// - AAS by ID
			aasByID = extractAAS(properties);
			// - AAS by tag
			aasByTag = mapAASToTags(aasByID);
			
			logger.debug("Downlink:"+downlinks);
			logger.debug("properties:"+properties);
			logger.debug("aasbyID:"+aasByID);
			
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
	}
	
	
	/**
	 * Get AAS content from AASDirectoryEntry
	 */
	protected String getAASContent(AASDirectoryEntry directoryEntry) {
		// Process directory entry
		switch (directoryEntry.getAASContentType()) {

			// Local content type
			case AASDirectoryEntry.AAS_CONTENTTYPE_LOCAL:
				return directoryEntry.getAASContent();

			// Remote content type
			case AASDirectoryEntry.AAS_CONTENTTYPE_REMOTE:
				throw new AASDirectoryProviderException("Unsupported AAS content type");

			// Proxy content type - content is ID of AAS that contains the information
			case AASDirectoryEntry.AAS_CONTENTTYPE_PROXY:
				return getAASContentByID(directoryEntry.getAASContent());

			// Unknown content type
			default:
				throw new AASDirectoryProviderException("Unknown AAS content type");
		}
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
	 * Get a specific AAS content with ID
	 */
	protected String getAASContentByID(String aasID) {
		// Extract requested AAS ID
		AASDirectoryEntry aas = aasByID.get(aasID);
		
		// Null pointer check
		if (aas == null)
			return null;
		
		// Return result
		return getAASContent(aas);
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
				
		// Extract action parameter
		Collection<String> alltags = getTagsAsCollection(req.getParameter("tags"));
		
		
		// Setup HTML response header
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		
		// Process get request
		// - Get all (local) AAS
		if (path.equals("api/v1/registry")) {
			// Extract AAS directory entries
			Collection<AASDirectoryEntry> entries = null;

			// Check if tags are to be processed
			if (alltags.isEmpty()) {
				// Get all tags
				entries = aasByID.values();
			} else {
				// HashSet that holds all elements
				Set<AASDirectoryEntry> taggedEntries = new HashSet<>();
				
				// Get tagged elements that have all requested tags
				// - Get first requested tag
				taggedEntries.addAll(aasByTag.get(alltags.iterator().next()));
				// - Remove all directory entries that do not have all tags
				for (String tag: alltags) taggedEntries.retainAll(aasByTag.get(tag));
				// - Place remaining elements into entries collection
				entries = taggedEntries;
			}
			
			// Build response string
			StringBuilder response = new StringBuilder();
			for (AASDirectoryEntry entry: entries) response.append(getAASContent(entry));

			// Write result
			sendResponse(response.toString(), resp.getWriter());
			// End processing
			return;
		}
		// Get a specific AAS
		else if (path.startsWith("api/v1/registry/")) {
			logger.debug("Getting:"+path);
			
			// Get requested AAS with ID
			String aas = getAASContentByID(path.substring(new String("api/v1/registry/").length()));
			
			// Write result
			sendResponse(aas, resp.getWriter());
			// End processing
			return;
		} else {
			// Send null response for unknown path
			sendResponse(null, resp.getWriter());
			return;
		}
	}

	
	
	/**
	 * Implement "Put" operation
	 */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Indicate an unsupported operation
		resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Request not implemented for this service");
	}


	
	/**
	 * <pre>
	 * Handle HTTP POST operation. Creates a new Property, Operation, Event, Submodel or AAS or invokes an operation.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Indicate an unsupported operation
		resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Request not implemented for this service");
	}


	
	/**
	 * Handle a HTTP PATCH operation. Updates a map or collection respective to action string.
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
		// Indicate an unsupported operation
		resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED, "Request not implemented for this service");
	}
}

