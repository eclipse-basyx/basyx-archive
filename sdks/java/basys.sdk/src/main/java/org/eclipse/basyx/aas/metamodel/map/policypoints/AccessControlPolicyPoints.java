package org.eclipse.basyx.aas.metamodel.map.policypoints;

import java.util.HashMap;

import org.eclipse.basyx.aas.metamodel.api.policypoints.IAccessControlPolicyPoints;
import org.eclipse.basyx.aas.metamodel.facade.policypoints.AccessControlPolicyPointsFacade;

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

	public static final String POLICYADMINISTRATIONPOINT = "policyAdministrationPoint";
	public static final String POLICYDECISIONPOINT = "policyDecisionPoint";
	public static final String POLICYENFORECEMENTPOINT = "policyEnforcementPoint";
	public static final String POLICYINFORMATIONPOINTS = "policyInformationPoints";

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

	public void setPolicyAdministrationPoint(Object obj) {
		new AccessControlPolicyPointsFacade(this).setPolicyAdministrationPoint(obj);
	}

	@Override
	public Object getPolicyAdministrationPoint() {
		return new AccessControlPolicyPointsFacade(this).getPolicyAdministrationPoint();
	}

	public void setPolicyDecisionPoint(Object obj) {
		new AccessControlPolicyPointsFacade(this).setPolicyDecisionPoint(obj);
	}

	@Override
	public Object getPolicyDecisionPoint() {
		return new AccessControlPolicyPointsFacade(this).getPolicyDecisionPoint();
	}

	public void setPolicyEnforcementPoint(Object obj) {
		new AccessControlPolicyPointsFacade(this).setPolicyEnforcementPoint(obj);
	}

	@Override
	public Object getPolicyEnforcementPoint() {
		return new AccessControlPolicyPointsFacade(this).getPolicyEnforcementPoint();
	}

	public void setPolicyInformationPoints(Object obj) {
		new AccessControlPolicyPointsFacade(this).setPolicyInformationPoints(obj);
	}

	@Override
	public Object getPolicyInformationPoints() {
		return new AccessControlPolicyPointsFacade(this).getPolicyInformationPoints();
	}
}
