package org.eclipse.basyx.testsuite.regression.vab.protocol.https;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * A Factory class containing methods creating an HTTPS client
 * with no verification and validation for self signed SSL
 * and other helper methods
 * 
 * @author haque
 *
 */
public class JerseyHttpsClientFactory {

	/**
	 * Returns an HTTPS client
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
    public static Client getJerseyHTTPSClient() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext sslContext = getSslContext();
        HostnameVerifier allHostsValid = new DefaultHostNameVerifier();

        return ClientBuilder.newBuilder()
                .sslContext(sslContext)
                .hostnameVerifier(allHostsValid)
                .build();
    }

    /**
     * Retrieves an SSL Context
     * with TLSv1 protocol
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private static SSLContext getSslContext() throws NoSuchAlgorithmException,
                                                     KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLSv1");

        KeyManager[] keyManagers = null;
        TrustManager[] trustManager = {new DefaultTrustManager()};
        SecureRandom secureRandom = new SecureRandom();

        sslContext.init(keyManagers, trustManager, secureRandom);

        return sslContext;
    }
}
