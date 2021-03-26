package org.eclipse.basyx.sandbox.components.xmlxqueryprovider;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import javax.xml.xquery.XQException;
import javax.xml.xquery.XQSequence;

import org.eclipse.basyx.components.provider.BaseConfiguredProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

//import com.saxonica.xqj.SaxonXQDataSource;



/**
 * Asset administration shell sub model provider that reads XML files via XQuery
 * 
 * @author kuhn
 *
 */
public class XMLXQuerySubModelProvider extends BaseConfiguredProvider {
	
	/**
	 * Initiates a logger using the current class
	 */
	private static final Logger logger = LoggerFactory.getLogger(XMLXQuerySubModelProvider.class);

	
	/**
	 * Working directory of this provider
	 */
	protected String workingDir = null;
	
	
	/**
	 * Base path that will be prepended to all paths
	 */
	protected String basePath = null;
	
	
	/**
	 * XML database file name
	 */
	protected String xmlDataBaseFileName = null;

	
	/**
	 * Properties
	 */
	protected Collection<String> properties = new LinkedList<String>();


	/**
	 * XQuery strings
	 */
	protected Map<String, String> xGetQueryStrings = new HashMap<>();

	

	
	
	
	/**
	 * Constructor
	 */
	public XMLXQuerySubModelProvider(Properties cfgValues) {
		// Invoke base constructor
		super(cfgValues);

		// Extract configuration properties
		workingDir          = cfgValues.getProperty("workingDir");
		basePath            = cfgValues.getProperty("basepath");
		xmlDataBaseFileName = cfgValues.getProperty("xmlFile");
				
		// Load and parse XML queries for properties
		properties.addAll(splitString(cfgValues.getProperty("properties")));

		// Add XQueries for properties
		for (String propertyName: properties) {
			// Try to parse parameter
			xGetQueryStrings.put(propertyName, cfgValues.getProperty(propertyName+"_get"));
		}
	}
	


	
	/**
	 * Split a whitespace delimited string
	 */
	@Override
	protected Collection<String> splitString(String input) {
		// Return value
		HashSet<String> result = new HashSet<>();
		
		// Split string into segments
		for (String inputStr: input.split(" ")) result.add(inputStr.trim());
		
		// Return result
		return result;
	}
	
	
	/**
	 * Build path to a file
	 */
	protected String buildPath(String filePath) {
		// Copy path variables
		String procBasePath = basePath;
		String procFilePath = filePath;
		
		// Make sure base path ends with a slash ('/') and file path does not start with one
		if (!procBasePath.endsWith("/")) procBasePath = procBasePath+"/";
		if (procFilePath.startsWith("/")) procFilePath = procFilePath.substring(1);
		
		// Add base path to file path
		return workingDir+"/"+procBasePath+procFilePath;
	}
	
	
	
	
	/**
	 * Process node result
	 */
	protected Map<String, Object> processNodeResult(org.w3c.dom.Node node) {
		// Result
		Map<String, Object> result = new HashMap<>();
		
		// Process node value
		if ((node.getNodeValue() != null) && (node.getNodeValue().trim().length()>0)) {
			result.put("#value", node.getNodeValue().trim());
		}
		
		// Process child nodes
		NodeList nodeList = node.getChildNodes();
		// - Process child nodes
		if (nodeList != null) {
			for (int i=0; i<nodeList.getLength(); i++) {
				// Get contained node
				org.w3c.dom.Node childNode = nodeList.item(i);
				
				// Process #text nodes that contain no children
				if (childNode.getNodeName().equals("#text")) {
					// Do not add anything if we run into a NullPointer exception
					try {
						// Trim and add node value
						result.put(childNode.getNodeName(), childNode.getNodeValue().trim());
					} catch (NullPointerException e) {}
				} else {
					// Process node contents
					result.put(childNode.getNodeName(), processNodeResult(childNode));
				}
			}
		}
		
		// Process named attributes
		NamedNodeMap nodeAttributes = node.getAttributes();
		// - Process node attributes
		if (nodeAttributes != null) {
			for (int i=0; i<nodeAttributes.getLength(); i++) {
				// Get node attributes
				org.w3c.dom.Node childNode = nodeAttributes.item(i);

				// Skip some predefined attributes
				if (childNode.getNodeName().equals("xmlns:xml")) continue;
				
				// Add attribute to result
				//result.put(childNode.getNodeName(), processNodeResult(childNode));
				result.put(childNode.getNodeName(), childNode.getNodeValue());
			}
		}
		
		// Return result
		return result;
	}
	
	
	/**
	 * Process result
	 */
	protected Object processXQueryResult(XQSequence result) {
		// We will return a collection for now. Maybe later, we need to support different return values?
		Collection<Object> returnValue = new LinkedList<>();
		
		// Process result
		try {
			while (result.next()) {
				// Get node from result
				org.w3c.dom.Node node = result.getNode();
				
				// Process node into a result
				returnValue.add(processNodeResult(node));
			}
		} catch (XQException e) {
        	e.printStackTrace();
        }

		// Return result
		return returnValue;
	}
	
	
	
	/**
	 * Execute an XQuery
	 */
	protected Object runXQuery(String queryFile) {
		// Store XQuery result
		Object returnValue = null;
		
		// Try to execute query
	//	try {
			// XQuery data source
//			XQDataSource ds  = new SaxonXQDataSource();
//			XQConnection con = ds.getConnection();
//
//			// Build path
//			String xqueryFilePath = buildPath(queryFile);
//
//			// Access file
//			File query = new File(xqueryFilePath);
//
//			// Relative URIs are resolved against the base URI before invoking the entity resolver.
//			// The relative URI used in the query will be resolved against this URI.
//			XQStaticContext ctx = con.getStaticContext();
//			ctx.setBaseURI(query.toURI().toString());
//
//			// Create input stream, prepare and run query
//			FileInputStream      queryInput = new FileInputStream(query);
//			XQPreparedExpression expr       = con.prepareExpression(queryInput, ctx);
//			XQSequence           result     = expr.executeQuery();
//
//			// Process result
//			returnValue = processXQueryResult(result);
//
//			// Close streams
//			result.close();
//			expr.close();
//			con.close();
//		} catch (XQException | FileNotFoundException e) {
//        	e.printStackTrace();
//        }

		// Return XQuery result
		return returnValue;
	}
	


	
	/**
	 * Create (insert) a value into the SQL table
	 */
	@Override
	public void createValue(String propertyName, Object arg1) {
		// Indicate exception
		throw new OperationNotImplementedException();
	}


	
	/**
	 * Delete a value from the SQL table
	 */
	@Override
	public void deleteValue(String arg0) {
		// Indicate exception
		throw new OperationNotImplementedException();
	}


	
	/**
	 * Delete a value from the SQL table
	 */
	@Override
	public void deleteValue(String propertyName, Object arg1) {
		// Indicate exception
		throw new OperationNotImplementedException();
	}

	

	public String getElementScope(String arg0) {
		logger.debug("GetScope:"+arg0);
		// TODO Auto-generated method stub
		return null;
	}


	
	/**
	 * Query the SQL database
	 */
	@Override
	public Object getModelPropertyValue(String propertyName) {
		// Run query and return result
		return runXQuery(xGetQueryStrings.get(propertyName));
	}


	
	/**
	 * Invoke operation with given parameter list
	 */
	@Override
	public Object invokeOperation(String propertyName, Object... parameter) {
		// Indicate exception
		throw new OperationNotImplementedException();
	}


	
	/**
	 * Invoke set operation with given parameter
	 */
	@Override
	public void setModelPropertyValue(String propertyName, Object arg1) {
		// Indicate exception
		throw new OperationNotImplementedException();
	}


	
	/**
	 * Invoke set operation with given parameter list
	 */
	public void setModelPropertyValue(String propertyName, Object... parameter) {
		// Indicate exception
		throw new OperationNotImplementedException();
	}
}
