package org.eclipse.basyx.aas.backend.modelprovider.http;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * HTTP Servelet superclass to enable HTTP Patch
 * @author pschorn
 *
 */
public abstract class BasysHTTPServelet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equalsIgnoreCase("PATCH")){
           doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }
     
    protected abstract void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
 

}
