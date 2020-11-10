package org.eclipse.basyx.testsuite.regression.vab.protocol.https;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * A Default Trust manager class which trusts everything
 * Used for testing with self signed certificate
 * @author haque
 *
 */
public class DefaultTrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
