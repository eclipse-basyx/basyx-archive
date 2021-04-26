/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 *    Copyright 2014-2018 (c) Fraunhofer IOSB (Author: Julius Pfrommer)
 *    Copyright 2014-2017 (c) Florian Palm
 *    Copyright 2014-2016 (c) Sten Grüner
 *    Copyright 2015 (c) Chris Iatrou
 *    Copyright 2015 (c) Oleksiy Vasylyev
 *    Copyright 2017 (c) Stefan Profanter, fortiss GmbH
 *    Copyright 2017-2018 (c) Mark Giraud, Fraunhofer IOSB
 *    Copyright 2019 (c) Kalycito Infotech Private Limited
 */

#include "ua_services.h"
#include "ua_server_internal.h"
#include "ua_session_manager.h"

static UA_StatusCode
signCreateSessionResponse(UA_Server *server, UA_SecureChannel *channel,
                          const UA_CreateSessionRequest *request,
                          UA_CreateSessionResponse *response) {
    if(channel->securityMode != UA_MESSAGESECURITYMODE_SIGN &&
       channel->securityMode != UA_MESSAGESECURITYMODE_SIGNANDENCRYPT)
        return UA_STATUSCODE_GOOD;

    const UA_SecurityPolicy *const securityPolicy = channel->securityPolicy;
    UA_SignatureData *signatureData = &response->serverSignature;

    /* Prepare the signature */
    size_t signatureSize = securityPolicy->certificateSigningAlgorithm.
        getLocalSignatureSize(securityPolicy, channel->channelContext);
    UA_StatusCode retval = UA_String_copy(&securityPolicy->certificateSigningAlgorithm.uri,
                                          &signatureData->algorithm);
    retval |= UA_ByteString_allocBuffer(&signatureData->signature, signatureSize);
    if(retval != UA_STATUSCODE_GOOD)
        return retval;

    /* Allocate a temp buffer */
    size_t dataToSignSize = request->clientCertificate.length + request->clientNonce.length;
    UA_ByteString dataToSign;
    retval = UA_ByteString_allocBuffer(&dataToSign, dataToSignSize);
    if(retval != UA_STATUSCODE_GOOD)
        return retval; /* signatureData->signature is cleaned up with the response */

    /* Sign the signature */
    memcpy(dataToSign.data, request->clientCertificate.data, request->clientCertificate.length);
    memcpy(dataToSign.data + request->clientCertificate.length,
           request->clientNonce.data, request->clientNonce.length);
    retval = securityPolicy->certificateSigningAlgorithm.
        sign(securityPolicy, channel->channelContext, &dataToSign, &signatureData->signature);

    /* Clean up */
    UA_ByteString_deleteMembers(&dataToSign);
    return retval;
}

void
Service_CreateSession(UA_Server *server, UA_SecureChannel *channel,
                      const UA_CreateSessionRequest *request,
                      UA_CreateSessionResponse *response) {
    if(!channel) {
        response->responseHeader.serviceResult = UA_STATUSCODE_BADINTERNALERROR;
        return;
    }

    if(!channel->connection) {
        response->responseHeader.serviceResult = UA_STATUSCODE_BADINTERNALERROR;
        return;
    }

    UA_LOG_DEBUG_CHANNEL(&server->config.logger, channel, "Trying to create session");

    if(channel->securityMode == UA_MESSAGESECURITYMODE_SIGN ||
       channel->securityMode == UA_MESSAGESECURITYMODE_SIGNANDENCRYPT) {
        /* Compare the clientCertificate with the remoteCertificate of the channel.
         * Both the clientCertificate of this request and the remoteCertificate
         * of the channel may contain a partial or a complete certificate chain.
         * The compareCertificate function of the channelModule will compare the
         * first certificate of each chain. The end certificate shall be located
         * first in the chain according to the OPC UA specification Part 6 (1.04),
         * chapter 6.2.3.*/
        UA_StatusCode retval = channel->securityPolicy->channelModule.
            compareCertificate(channel->channelContext, &request->clientCertificate);
        if(retval != UA_STATUSCODE_GOOD) {
            UA_LOG_WARNING_CHANNEL(&server->config.logger, channel,
                                   "The client certificate did not validate");
            response->responseHeader.serviceResult = UA_STATUSCODE_BADCERTIFICATEINVALID;
            return;
        }
    }

    if(channel->securityToken.channelId == 0) {
        response->responseHeader.serviceResult = UA_STATUSCODE_BADSECURECHANNELIDINVALID;
        return;
    }

    if(!UA_ByteString_equal(&channel->securityPolicy->policyUri,
                            &UA_SECURITY_POLICY_NONE_URI) &&
       request->clientNonce.length < 32) {
        response->responseHeader.serviceResult = UA_STATUSCODE_BADNONCEINVALID;
        return;
    }

    /* TODO: Compare application URI with certificate uri (decode certificate) */
    UA_CertificateVerification *cv = channel->securityPolicy->certificateVerification;
    if(cv && cv->verifyApplicationURI) {
        response->responseHeader.serviceResult =
            cv->verifyApplicationURI(cv->context, &request->clientCertificate,
                                     &request->clientDescription.applicationUri);
        if(response->responseHeader.serviceResult != UA_STATUSCODE_GOOD) {
            UA_LOG_WARNING_CHANNEL(&server->config.logger, channel,
                                   "The client's ApplicationURI did not match the certificate");
            return;
        }
    }

    UA_Session *newSession = NULL;
    response->responseHeader.serviceResult =
        UA_SessionManager_createSession(&server->sessionManager, channel, request, &newSession);
    if(response->responseHeader.serviceResult != UA_STATUSCODE_GOOD) {
        UA_LOG_WARNING_CHANNEL(&server->config.logger, channel,
                               "Processing CreateSessionRequest failed");
        return;
    }

    UA_assert(newSession != NULL);

    /* Allocate the response */
    response->serverEndpoints = (UA_EndpointDescription *)
        UA_Array_new(server->config.endpointsSize,
                     &UA_TYPES[UA_TYPES_ENDPOINTDESCRIPTION]);
    if(!response->serverEndpoints) {
        response->responseHeader.serviceResult = UA_STATUSCODE_BADOUTOFMEMORY;
        UA_SessionManager_removeSession(&server->sessionManager,
                                        &newSession->header.authenticationToken);
        return;
    }
    response->serverEndpointsSize = server->config.endpointsSize;

    /* Copy the server's endpointdescriptions into the response */
    for(size_t i = 0; i < server->config.endpointsSize; ++i)
        response->responseHeader.serviceResult |=
            UA_EndpointDescription_copy(&server->config.endpoints[i],
                                        &response->serverEndpoints[i]);
    if(response->responseHeader.serviceResult != UA_STATUSCODE_GOOD) {
        UA_SessionManager_removeSession(&server->sessionManager,
                                        &newSession->header.authenticationToken);
        return;
    }

    /* Mirror back the endpointUrl */
    for(size_t i = 0; i < response->serverEndpointsSize; ++i) {
        UA_String_deleteMembers(&response->serverEndpoints[i].endpointUrl);
        response->responseHeader.serviceResult |=
            UA_String_copy(&request->endpointUrl,
                           &response->serverEndpoints[i].endpointUrl);
    }

    /* Attach the session to the channel. But don't activate for now. */
    UA_Session_attachToSecureChannel(newSession, channel);

    /* Fill the session information */
    newSession->maxResponseMessageSize = request->maxResponseMessageSize;
    newSession->maxRequestMessageSize =
        channel->connection->config.maxMessageSize;
    response->responseHeader.serviceResult |=
        UA_ApplicationDescription_copy(&request->clientDescription,
                                       &newSession->clientDescription);

    /* Prepare the response */
    response->sessionId = newSession->sessionId;
    response->revisedSessionTimeout = (UA_Double)newSession->timeout;
    response->authenticationToken = newSession->header.authenticationToken;
    response->responseHeader.serviceResult |=
        UA_String_copy(&request->sessionName, &newSession->sessionName);

    UA_ByteString_init(&response->serverCertificate);

    if(server->config.endpointsSize > 0)
       for(size_t i = 0; i < response->serverEndpointsSize; ++i) {
          if(response->serverEndpoints[i].securityMode==channel->securityMode &&
             UA_ByteString_equal(&response->serverEndpoints[i].securityPolicyUri,
                                 &channel->securityPolicy->policyUri) &&
             UA_String_equal(&response->serverEndpoints[i].endpointUrl,
                             &request->endpointUrl))
          {
             response->responseHeader.serviceResult |=
                 UA_ByteString_copy(&response->serverEndpoints[i].serverCertificate,
                                    &response->serverCertificate);
          }
       }

    /* Create a session nonce */
    response->responseHeader.serviceResult |= UA_Session_generateNonce(newSession);
    response->responseHeader.serviceResult |=
        UA_ByteString_copy(&newSession->serverNonce, &response->serverNonce);

    /* Sign the signature */
    response->responseHeader.serviceResult |=
       signCreateSessionResponse(server, channel, request, response);

    /* Failure -> remove the session */
    if(response->responseHeader.serviceResult != UA_STATUSCODE_GOOD) {
        UA_SessionManager_removeSession(&server->sessionManager,
                                        &newSession->header.authenticationToken);
        return;
    }

    UA_LOG_INFO_CHANNEL(&server->config.logger, channel,
                        "Session " UA_PRINTF_GUID_FORMAT " created",
                        UA_PRINTF_GUID_DATA(newSession->sessionId.identifier.guid));
}

static UA_StatusCode
checkSignature(const UA_Server *server, const UA_SecureChannel *channel,
               UA_Session *session, const UA_ActivateSessionRequest *request) {
    if(channel->securityMode != UA_MESSAGESECURITYMODE_SIGN &&
       channel->securityMode != UA_MESSAGESECURITYMODE_SIGNANDENCRYPT)
        return UA_STATUSCODE_GOOD;

    /* Check for zero signature length in client signature */
    if(request->clientSignature.signature.length == 0) {
        return UA_STATUSCODE_BADAPPLICATIONSIGNATUREINVALID;
    }

    if(!channel->securityPolicy)
        return UA_STATUSCODE_BADINTERNALERROR;
    const UA_SecurityPolicy *securityPolicy = channel->securityPolicy;
    const UA_ByteString *localCertificate = &securityPolicy->localCertificate;

    size_t dataToVerifySize = localCertificate->length + session->serverNonce.length;

    UA_ByteString dataToVerify;
    UA_StatusCode retval = UA_ByteString_allocBuffer(&dataToVerify, dataToVerifySize);
    if(retval != UA_STATUSCODE_GOOD)
        return retval;

    memcpy(dataToVerify.data, localCertificate->data, localCertificate->length);
    memcpy(dataToVerify.data + localCertificate->length,
           session->serverNonce.data, session->serverNonce.length);

    retval = securityPolicy->certificateSigningAlgorithm.verify(securityPolicy, channel->channelContext, &dataToVerify,
                                                                &request->clientSignature.signature);
    UA_ByteString_deleteMembers(&dataToVerify);
    return retval;
}

#ifdef UA_ENABLE_ENCRYPTION
static UA_StatusCode
decryptPassword(UA_SecurityPolicy *securityPolicy, void *tempChannelContext,
                const UA_ByteString *serverNonce, UA_UserNameIdentityToken *userToken) {
    UA_SecurityPolicyEncryptionAlgorithm *asymEnc =
        &securityPolicy->asymmetricModule.cryptoModule.encryptionAlgorithm;
    if(!UA_String_equal(&userToken->encryptionAlgorithm, &asymEnc->uri))
        return UA_STATUSCODE_BADIDENTITYTOKENINVALID;

    UA_UInt32 tokenSecretLength;
    UA_ByteString decryptedTokenSecret, tokenServerNonce;
    if(UA_ByteString_copy(&userToken->password, &decryptedTokenSecret) != UA_STATUSCODE_GOOD)
        return UA_STATUSCODE_BADIDENTITYTOKENINVALID;

    UA_StatusCode retval = UA_STATUSCODE_BADIDENTITYTOKENINVALID;
    if(asymEnc->decrypt(securityPolicy, tempChannelContext,
                        &decryptedTokenSecret) != UA_STATUSCODE_GOOD)
        goto cleanup;

    memcpy(&tokenSecretLength, decryptedTokenSecret.data, sizeof(UA_UInt32));

    /* The decrypted data must be large enough to include the Encrypted Token
     * Secret Format and the length field must indicate enough data to include
     * the server nonce. */
    if(decryptedTokenSecret.length < sizeof(UA_UInt32) + serverNonce->length ||
       decryptedTokenSecret.length < sizeof(UA_UInt32) + tokenSecretLength ||
       tokenSecretLength < serverNonce->length)
        goto cleanup;

    /* If the Encrypted Token Secret contains padding, the padding must be
     * zeroes according to the 1.04.1 specification errata, chapter 3. */
    for(size_t i = sizeof(UA_UInt32) + tokenSecretLength; i < decryptedTokenSecret.length; i++) {
        if(decryptedTokenSecret.data[i] != 0)
            goto cleanup;
    }

    /* The server nonce must match according to the 1.04.1 specification errata,
     * chapter 3. */
    tokenServerNonce.length = serverNonce->length;
    tokenServerNonce.data = &decryptedTokenSecret.data[sizeof(UA_UInt32) + tokenSecretLength - serverNonce->length];
    if(!UA_ByteString_equal(serverNonce, &tokenServerNonce))
        goto cleanup;

    /* The password was decrypted successfully. Replace usertoken with the
     * decrypted password. The encryptionAlgorithm and policyId fields are left
     * in the UserToken as an indication for the AccessControl plugin that
     * evaluates the decrypted content. */
    memcpy(userToken->password.data, &decryptedTokenSecret.data[sizeof(UA_UInt32)],
           tokenSecretLength - serverNonce->length);
    userToken->password.length = tokenSecretLength - serverNonce->length;
    retval = UA_STATUSCODE_GOOD;

 cleanup:
    UA_ByteString_deleteMembers(&decryptedTokenSecret);
    return retval;
}
#endif

/* TODO: Check all of the following:
 *
 * Part 4, §5.6.3: When the ActivateSession Service is called for the first time
 * then the Server shall reject the request if the SecureChannel is not same as
 * the one associated with the CreateSession request. Subsequent calls to
 * ActivateSession may be associated with different SecureChannels. If this is
 * the case then the Server shall verify that the Certificate the Client used to
 * create the new SecureChannel is the same as the Certificate used to create
 * the original SecureChannel. In addition, the Server shall verify that the
 * Client supplied a UserIdentityToken that is identical to the token currently
 * associated with the Session. Once the Server accepts the new SecureChannel it
 * shall reject requests sent via the old SecureChannel. */

void
Service_ActivateSession(UA_Server *server, UA_SecureChannel *channel,
                        UA_Session *session, const UA_ActivateSessionRequest *request,
                        UA_ActivateSessionResponse *response) {
    UA_LOG_DEBUG_SESSION(&server->config.logger, session, "Execute ActivateSession");

    if(session->validTill < UA_DateTime_nowMonotonic()) {
        UA_LOG_INFO_SESSION(&server->config.logger, session,
                            "ActivateSession: SecureChannel %i wants "
                            "to activate, but the session has timed out",
                            channel->securityToken.channelId);
        response->responseHeader.serviceResult =
            UA_STATUSCODE_BADSESSIONIDINVALID;
        return;
    }

    /* Check if the signature corresponds to the ServerNonce that was last sent
     * to the client */
    response->responseHeader.serviceResult = checkSignature(server, channel, session, request);
    if(response->responseHeader.serviceResult != UA_STATUSCODE_GOOD) {
        UA_LOG_INFO_SESSION(&server->config.logger, session,
                            "Signature check failed with status code %s",
                            UA_StatusCode_name(response->responseHeader.serviceResult));
        return;
    }

    /* Find the matching endpoint */
    const UA_EndpointDescription *ed = NULL;
    for(size_t i = 0; ed == NULL && i < server->config.endpointsSize; ++i) {
        const UA_EndpointDescription *e = &server->config.endpoints[i];

        /* Match the Security Mode */
        if(e->securityMode != channel->securityMode)
            continue;

        /* Match the SecurityPolicy */
        if(!UA_String_equal(&e->securityPolicyUri, &channel->securityPolicy->policyUri))
            continue;

        /* Match the UserTokenType */
        for(size_t j = 0; j < e->userIdentityTokensSize; j++) {
            const UA_UserTokenPolicy *u = &e->userIdentityTokens[j];
            if(u->tokenType == UA_USERTOKENTYPE_ANONYMOUS) {
                /* Part 4, Section 5.6.3.2, Table 17: A NULL or empty
                 * UserIdentityToken should be treated as Anonymous */
                if(request->userIdentityToken.content.decoded.type != &UA_TYPES[UA_TYPES_ANONYMOUSIDENTITYTOKEN] &&
                   request->userIdentityToken.encoding != UA_EXTENSIONOBJECT_ENCODED_NOBODY)
                    continue;
            } else if(u->tokenType == UA_USERTOKENTYPE_USERNAME) {
                if(request->userIdentityToken.content.decoded.type != &UA_TYPES[UA_TYPES_USERNAMEIDENTITYTOKEN])
                    continue;
            } else if(u->tokenType == UA_USERTOKENTYPE_CERTIFICATE) {
                if(request->userIdentityToken.content.decoded.type != &UA_TYPES[UA_TYPES_X509IDENTITYTOKEN])
                    continue;
            } else if(u->tokenType == UA_USERTOKENTYPE_ISSUEDTOKEN) {
                if(request->userIdentityToken.content.decoded.type != &UA_TYPES[UA_TYPES_ISSUEDIDENTITYTOKEN])
                    continue;
            } else {
                response->responseHeader.serviceResult = UA_STATUSCODE_BADIDENTITYTOKENINVALID;
                return;
            }

            /* Match found */
            ed = e;
            break;
        }

    }

    /* No matching endpoint found */
    if(!ed) {
        response->responseHeader.serviceResult = UA_STATUSCODE_BADIDENTITYTOKENINVALID;
        return;
    }

#ifdef UA_ENABLE_ENCRYPTION
    /* If it is a UserNameIdentityToken, decrypt the password if encrypted */
    if((request->userIdentityToken.encoding == UA_EXTENSIONOBJECT_DECODED) &&
       (request->userIdentityToken.content.decoded.type == &UA_TYPES[UA_TYPES_USERNAMEIDENTITYTOKEN])) {
       UA_UserNameIdentityToken *userToken = (UA_UserNameIdentityToken *)
           request->userIdentityToken.content.decoded.data;

       /* Find the UserTokenPolicy */
       UA_Byte tokenIndex = 0;
       for(; tokenIndex < ed->userIdentityTokensSize; tokenIndex++) {
           if(ed->userIdentityTokens[tokenIndex].tokenType != UA_USERTOKENTYPE_USERNAME)
               continue;
           if(UA_String_equal(&userToken->policyId, &ed->userIdentityTokens[tokenIndex].policyId))
               break;
       }
       if(tokenIndex == ed->userIdentityTokensSize) {
           response->responseHeader.serviceResult = UA_STATUSCODE_BADIDENTITYTOKENINVALID;
           return;
       }

       /* Get the SecurityPolicy. If the userTokenPolicy doesn't specify a
        * security policy the security policy of the secure channel is used. */
       UA_SecurityPolicy* securityPolicy;
       if(ed->userIdentityTokens[tokenIndex].securityPolicyUri.data == NULL)
           securityPolicy = UA_SecurityPolicy_getSecurityPolicyByUri(server, &ed->securityPolicyUri);
       else
           securityPolicy = UA_SecurityPolicy_getSecurityPolicyByUri(server, &ed->userIdentityTokens[tokenIndex].securityPolicyUri);
       if(!securityPolicy) {
          response->responseHeader.serviceResult = UA_STATUSCODE_BADINTERNALERROR;
          return;
       }

       /* Encrypted password? */
       if(!UA_String_equal(&securityPolicy->policyUri, &UA_SECURITY_POLICY_NONE_URI)) {
           /* Test if the encryption algorithm is correctly specified */
           if(!UA_String_equal(&userToken->encryptionAlgorithm,
                               &securityPolicy->asymmetricModule.cryptoModule.
                               encryptionAlgorithm.uri)) {
               response->responseHeader.serviceResult = UA_STATUSCODE_BADIDENTITYTOKENINVALID;
               return;
           }

           /* Create a temporary channel context if a different SecurityPolicy is
            * used for the password from the SecureChannel */
           void *tempChannelContext = channel->channelContext;
           if(securityPolicy != channel->securityPolicy) {
               /* TODO: This is a hack. We use our own certificate to create a
                * channel context. Because the client does not provide one in a
                * #None SecureChannel. We should not need a ChannelContext at all
                * for asymmetric decryption where the remote certificate is not
                * used. */
               response->responseHeader.serviceResult =
                   securityPolicy->channelModule.newContext(securityPolicy,
                                                            &securityPolicy->localCertificate,
                                                            &tempChannelContext);
               if(response->responseHeader.serviceResult != UA_STATUSCODE_GOOD) {
                   UA_LOG_WARNING_SESSION(&server->config.logger, session, "ActivateSession: "
                                          "Failed to create a context for the SecurityPolicy %.*s",
                                          (int)securityPolicy->policyUri.length,
                                          securityPolicy->policyUri.data);
                   return;
               }
           }

           /* Decrypt */
           response->responseHeader.serviceResult =
               decryptPassword(securityPolicy, tempChannelContext, &session->serverNonce, userToken);

           /* Remove the temporary channel context */
           if(securityPolicy != channel->securityPolicy)
               securityPolicy->channelModule.deleteContext(tempChannelContext);
       }

       if(response->responseHeader.serviceResult != UA_STATUSCODE_GOOD) {
           UA_LOG_INFO_SESSION(&server->config.logger, session, "ActivateSession: "
                               "Failed to decrypt the password with the status code %s",
                               UA_StatusCode_name(response->responseHeader.serviceResult));
       }

    }
#endif

    /* Callback into userland access control */
    response->responseHeader.serviceResult =
        server->config.accessControl.activateSession(server, &server->config.accessControl,
                                                     ed, &channel->remoteCertificate,
                                                     &session->sessionId,
                                                     &request->userIdentityToken,
                                                     &session->sessionHandle);
    if(response->responseHeader.serviceResult != UA_STATUSCODE_GOOD) {
        UA_LOG_INFO_SESSION(&server->config.logger, session,
                            "ActivateSession: The AccessControl plugin "
                            "denied the access with the status code %s",
                            UA_StatusCode_name(response->responseHeader.serviceResult));
        return;
    }

    if(session->header.channel && session->header.channel != channel) {
        UA_LOG_INFO_SESSION(&server->config.logger, session,
                            "ActivateSession: Detach from old channel");
        /* Detach the old SecureChannel and attach the new */
        UA_Session_detachFromSecureChannel(session);
        UA_Session_attachToSecureChannel(session, channel);
    }

    /* Activate the session */
    session->activated = true;
    UA_Session_updateLifetime(session);

    /* Generate a new session nonce for the next time ActivateSession is called */
    response->responseHeader.serviceResult = UA_Session_generateNonce(session);
    response->responseHeader.serviceResult |=
        UA_ByteString_copy(&session->serverNonce, &response->serverNonce);
    if(response->responseHeader.serviceResult != UA_STATUSCODE_GOOD) {
        UA_Session_detachFromSecureChannel(session);
        session->activated = false;
        UA_LOG_INFO_SESSION(&server->config.logger, session,
                            "ActivateSession: Could not generate a server nonce");
        return;
    }

    UA_LOG_INFO_SESSION(&server->config.logger, session,
                        "ActivateSession: Session activated");
}

void
Service_CloseSession(UA_Server *server, UA_Session *session,
                     const UA_CloseSessionRequest *request,
                     UA_CloseSessionResponse *response) {
    UA_LOG_INFO_SESSION(&server->config.logger, session, "CloseSession");

    response->responseHeader.serviceResult =
        UA_SessionManager_removeSession(&server->sessionManager,
                                        &session->header.authenticationToken);
}
