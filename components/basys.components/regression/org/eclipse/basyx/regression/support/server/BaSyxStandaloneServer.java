package org.eclipse.basyx.regression.support.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;




/**
 * Stand alone server implementation
 * 
 * @author kuhn
 *
 */
public class BaSyxStandaloneServer {
	
	
    /**
     * Main method if you would like to start the Server manually. Stop the server by typing 'Please stop tomcat' (without quotation marks)
     * @param args
     * @throws IOException 
     */
    public static void main(String args[]) throws IOException {
    	
    	// Start server and allocate resource
    	AASHTTPServerResource resource = AASHTTPServerResource.getTestResource(new BaSyxContext("", ""));
    	resource.before();
    	
    	// Prompt for user input
    	InputStreamReader in = new InputStreamReader(System.in);
    	BufferedReader keyboard = new BufferedReader(in);

    	// Wait for end command
        System.out.println("Enter 'Please stop tomcat' to stop the server.");
        while (true) {
        	String command = keyboard.readLine();
        	if (command.equals("Please stop tomcat")) {
        		resource.after();
        		break;
        	} else {
        		System.out.println("Command " + command + " not recognized!");
        	}
        }
    }
}

