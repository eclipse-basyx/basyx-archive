package org.eclipse.basyx.vab.backend.server.opcua;

import java.util.concurrent.CompletableFuture;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

public interface BaSyxOpcUaClient {

    void run(OpcUaClient client, CompletableFuture<OpcUaClient> future) throws Exception;

}
