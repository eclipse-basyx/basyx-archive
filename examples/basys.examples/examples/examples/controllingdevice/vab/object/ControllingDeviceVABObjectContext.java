package examples.controllingdevice.vab.object;

import org.eclipse.basyx.components.servlets.RawCFGSubModelProviderServlet;
import org.eclipse.basyx.components.servlets.SQLDirectoryServlet;

import examples.contexts.DefaultBaSyxExamplesContext;

/*
<servlet>
<servlet-name>DeviceStatusVABObject</servlet-name>
<servlet-class> examples.controllingdevice.vab.object.SimpleVABElementServlet </servlet-class>

<load-on-startup>5</load-on-startup>
</servlet>
<servlet-mapping>
<servlet-name>DeviceStatusVABObject</servlet-name>
<url-pattern>/Testsuite/components/BaSys/1.0/devicestatusVAB/*</url-pattern>
</servlet-mapping>


<servlet>
<servlet-name>DeviceStatusSubmodel</servlet-name>
<servlet-class> examples.controllingdevice.vab.object.SimpleSubmodelServlet </servlet-class>

<load-on-startup>5</load-on-startup>
</servlet>
<servlet-mapping>
<servlet-name>DeviceStatusSubmodel</servlet-name>
<url-pattern>/Testsuite/components/BaSys/1.0/devicestatusSM/*</url-pattern>
</servlet-mapping>

 */

/**
 * Tailored context for examples.controllingdevice.vab.object test cases
 * 
 * @author kuhn
 *
 */
public class ControllingDeviceVABObjectContext extends DefaultBaSyxExamplesContext {

	
	/**
	 * Version of serialized instance
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Constructor
	 */
	public ControllingDeviceVABObjectContext() {
		// Invoke base constructor to set up Tomcat server
		super();
		
		// Define test case specific Servlet infrastucture
		addServletMapping("/Testsuite/components/BaSys/1.0/devicestatusVAB/*", new SimpleVABElementServlet());
		addServletMapping("/Testsuite/components/BaSys/1.0/devicestatusSM/*",  new SimpleSubmodelServlet());
	}
}
