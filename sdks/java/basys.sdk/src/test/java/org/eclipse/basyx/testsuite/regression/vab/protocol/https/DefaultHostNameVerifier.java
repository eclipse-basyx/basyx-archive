package org.eclipse.basyx.testsuite.regression.vab.protocol.https;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * A default host name verifier which verifies every host name
 * Used for testing with self signed certificate
 * @author haque
 *
 */
public class DefaultHostNameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String s, SSLSession sslSession) {
        return true;
    }
}
