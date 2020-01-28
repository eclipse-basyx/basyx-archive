package org.eclipse.basyx.aas.metamodel.api.policypoints;

/**
 * Container for access control policy points.
 * 
 * @author rajashek, schnicke
 *
 */
public interface IAccessControlPolicyPoints {
	/**
	 * Gets the access control administration policy point of the AAS.
	 * 
	 * @return
	 */
	public Object getPolicyAdministrationPoint();

	/**
	 * Gets the access control policy decision point of the AAS.
	 * 
	 * @return
	 */
	public Object getPolicyDecisionPoint();

	/**
	 * Gets the access control policy enforcement point of the AAS.
	 * 
	 * @return
	 */
	public Object getPolicyEnforcementPoint();

	/**
	 * Gets the access control policy information points of the AAS.
	 * 
	 * @return
	 */
	public Object getPolicyInformationPoints();
}
