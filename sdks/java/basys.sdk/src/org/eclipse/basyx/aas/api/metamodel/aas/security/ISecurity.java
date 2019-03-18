package org.eclipse.basyx.aas.api.metamodel.aas.security;
/**
 * Interface for Security
 * @author rajashek
 *
*/
public interface ISecurity {
	
	public Object getAccessControlPolicyPoints();
	
	public void setAccessControlPolicyPoints(Object obj);
	
	public Object getTrustAnchor();
	
	public void setTrustAnchor(Object obj);

}
