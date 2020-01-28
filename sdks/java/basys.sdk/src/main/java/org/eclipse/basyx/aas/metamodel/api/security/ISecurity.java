package org.eclipse.basyx.aas.metamodel.api.security;

import org.eclipse.basyx.aas.metamodel.api.policypoints.IAccessControlPolicyPoints;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;

/**
 * Container for security relevant information of the AAS.
 * 
 * @author rajashek, schnicke
 *
 */
public interface ISecurity {
	/**
	 * Gets the access control policy points of the AAS.
	 * 
	 * @return
	 */
	IAccessControlPolicyPoints getAccessControlPolicyPoints();

	/**
	 * Gets the certificates of the AAS.
	 * 
	 * @return
	 */
	ICertificate getCertificate();

	/**
	 * Gets the certificate extensions as required by the AAS
	 * 
	 * @return
	 */
	Reference getRequiredCertificateExtension();
}
