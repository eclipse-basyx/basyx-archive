package org.eclipse.basyx.aas.api.metamodel.aas.policypoints;

/**
 * Interface for AccessControlPolicyPoints
 * @author rajashek
 *
*/
public interface IAccessControlPolicyPoints {
	
	public void setPolicyAdministrationPoint(Object obj);
	
	public Object getPolicyAdministrationPoint();
	
    public void setPolicyDecisionPoint(Object obj);
	
	public Object getPolicyDecisionPoint();
	
    public void setPolicyEnforcementPoint(Object obj);
	
	public Object getPolicyEnforcementPoint();
	
    public void setPolicyInformationPoints(Object obj);
	
	public Object getPolicyInformationPoints();
	
	

}
