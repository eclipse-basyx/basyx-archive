package org.eclipse.basyx.testsuite.regression.aas.metamodel.map.security;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.aas.metamodel.map.policypoints.AccessControlPolicyPoints;
import org.eclipse.basyx.aas.metamodel.map.security.Security;
import org.junit.Test;

/**
 * Tests constructor, setter and getter of {@link Security} for their
 * correctness
 * 
 * @author haque
 *
 */
public class TestSecurity {
	
	@Test
	public void testSetAccessControlPolicyPoints() {
		AccessControlPolicyPoints points = new AccessControlPolicyPoints();
		Security security = new Security();
		security.setAccessControlPolicyPoints(points);
		assertEquals(points, security.getAccessControlPolicyPoints());
	}
}
