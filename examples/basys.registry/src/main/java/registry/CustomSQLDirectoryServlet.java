package registry;


import org.eclipse.basyx.components.servlets.SQLDirectoryServlet;


/**
 * SQL database based directory provider
 * 
 * This directory provider provides a static directory. It therefore only
 * supports get() operations. Modification of the directory via
 * PUT/POST/PATCH/DELETE operations is not supported.
 * 
 * @author kuhn, pschorn
 *
 */
public class CustomSQLDirectoryServlet extends SQLDirectoryServlet {

	
	private static final long serialVersionUID = 1L;

	/**
	 * Path to the directory.properties file, that contains config data for the SQL
	 * connection
	 */
	private static String configFilePath = "/basys/directory.properties";


	/**
	 * Provide HTTP interface with JSONProvider to handle serialization and
	 * SQLDirectoryProvider as backend
	 */
	public CustomSQLDirectoryServlet() {
		super(configFilePath);

	}

	
}

