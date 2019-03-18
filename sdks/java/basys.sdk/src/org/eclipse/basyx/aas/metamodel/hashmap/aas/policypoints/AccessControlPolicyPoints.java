package org.eclipse.basyx.aas.metamodel.hashmap.aas.policypoints;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.metamodel.aas.policypoints.IAccessControlPolicyPoints;
import org.eclipse.basyx.aas.metamodel.facades.AccessControlPolicyPointsFacade;




/**
 * Security class
 * 
 * @author elsheikh
 *
 */
public class AccessControlPolicyPoints extends HashMap<String, Object> implements IAccessControlPolicyPoints {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String POLICYADMINISTRATIONPOINT= "policyAdministrationPoint";
	public static final String POLICYDECISIONPOINT="policyDecisionPoint";
	public static final String POLICYENFORECEMENTPOINT="policyEnforcementPoint";
	public static final String POLICYINFORMATIONPOINTS="policyInformationPoints";

	
	
	/**
	 * Constructor
	 */
	public AccessControlPolicyPoints() {
		// Default values
		put(POLICYADMINISTRATIONPOINT, null);
		put(POLICYDECISIONPOINT, null);
		put(POLICYENFORECEMENTPOINT, null);
		
		put(POLICYINFORMATIONPOINTS, null);
	}



	@Override
	public void setPolicyAdministrationPoint(Object obj) {
		new AccessControlPolicyPointsFacade(this).setPolicyAdministrationPoint(obj);
		
	}



	@Override
	public Object getPolicyAdministrationPoint() {
	return new AccessControlPolicyPointsFacade(this).getPolicyAdministrationPoint();
	}



	@Override
	public void setPolicyDecisionPoint(Object obj) {
		new AccessControlPolicyPointsFacade(this).setPolicyDecisionPoint(obj);
		
	}



	@Override
	public Object getPolicyDecisionPoint() {
		return new AccessControlPolicyPointsFacade(this).getPolicyDecisionPoint();
	}



	@Override
	public void setPolicyEnforcementPoint(Object obj) {
		new AccessControlPolicyPointsFacade(this).setPolicyEnforcementPoint(obj);
		
	}



	@Override
	public Object getPolicyEnforcementPoint() {
		return new AccessControlPolicyPointsFacade(this).getPolicyEnforcementPoint();
	}



	@Override
	public void setPolicyInformationPoints(Object obj) {
		new AccessControlPolicyPointsFacade(this).setPolicyInformationPoints(obj);
		
	}



	@Override
	public Object getPolicyInformationPoints() {
		return new AccessControlPolicyPointsFacade(this).getPolicyInformationPoints();
	}
}
