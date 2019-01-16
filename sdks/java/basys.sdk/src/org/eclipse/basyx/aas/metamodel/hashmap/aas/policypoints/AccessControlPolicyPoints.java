package org.eclipse.basyx.aas.metamodel.hashmap.aas.policypoints;

import java.util.HashMap;




/**
 * Security class
 * 
 * @author elsheikh
 *
 */
public class AccessControlPolicyPoints extends HashMap<String, Object> {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Constructor
	 */
	public AccessControlPolicyPoints() {
		// Default values
		put("policyAdministrationPoint", null);
		put("policyDecisionPoint", null);
		put("policyEnforcementPoint", null);
		
		put("policyInformationPoints", null);
	}
}
