/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 *    Copyright 2014-2018 (c) Fraunhofer IOSB (Author: Julius Pfrommer)
 *    Copyright 2014, 2017 (c) Florian Palm
 *    Copyright 2015-2016 (c) Sten Grüner
 *    Copyright 2015 (c) Chris Iatrou
 *    Copyright 2015-2016 (c) Oleksiy Vasylyev
 *    Copyright 2016-2017 (c) Stefan Profanter, fortiss GmbH
 *    Copyright 2017 (c) Julian Grothoff
 *    Copyright 2019 (c) Kalycito Infotech Private Limited
 */

#ifndef UA_SERVER_INTERNAL_H_
#define UA_SERVER_INTERNAL_H_

#include <open62541/server.h>
#include <open62541/server_config.h>
#include <open62541/plugin/nodestore.h>

#include "ua_connection_internal.h"
#include "ua_securechannel_manager.h"
#include "ua_session_manager.h"
#include "ua_timer.h"
#include "ua_util_internal.h"
#include "ua_workqueue.h"

_UA_BEGIN_DECLS

#ifdef UA_ENABLE_PUBSUB
#include "ua_pubsub_manager.h"
#endif

#ifdef UA_ENABLE_DISCOVERY
#include "ua_discovery_manager.h"
#endif

#ifdef UA_ENABLE_SUBSCRIPTIONS
#include "ua_subscription.h"

typedef struct {
    UA_MonitoredItem monitoredItem;
    void *context;
    union {
        UA_Server_DataChangeNotificationCallback dataChangeCallback;
        /* UA_Server_EventNotificationCallback eventCallback; */
    } callback;
} UA_LocalMonitoredItem;

#endif

typedef enum {
    UA_SERVERLIFECYCLE_FRESH,
    UA_SERVERLIFECYLE_RUNNING
} UA_ServerLifecycle;

struct UA_Server {
    /* Config */
    UA_ServerConfig config;
    UA_DateTime startTime;
    UA_DateTime endTime; /* Zeroed out. If a time is set, then the server shuts
                          * down once the time has been reached */

    /* Nodestore */
    void *nsCtx;

    UA_ServerLifecycle state;

    /* Security */
    UA_SecureChannelManager secureChannelManager;
    UA_SessionManager sessionManager;
    UA_Session adminSession; /* Local access to the services (for startup and
                              * maintenance) uses this Session with all possible
                              * access rights (Session Id: 1) */

    /* Namespaces */
    size_t namespacesSize;
    UA_String *namespaces;

    /* Callbacks with a repetition interval */
    UA_Timer timer;

    /* WorkQueue and worker threads */
    UA_WorkQueue workQueue;

    /* For bootstrapping, omit some consistency checks, creating a reference to
     * the parent and member instantiation */
    UA_Boolean bootstrapNS0;

    /* Discovery */
#ifdef UA_ENABLE_DISCOVERY
    UA_DiscoveryManager discoveryManager;
#endif

    /* DataChange Subscriptions */
#ifdef UA_ENABLE_SUBSCRIPTIONS
    /* Num active subscriptions */
    UA_UInt32 numSubscriptions;
    /* Num active monitored items */
    UA_UInt32 numMonitoredItems;
    /* To be cast to UA_LocalMonitoredItem to get the callback and context */
    LIST_HEAD(LocalMonitoredItems, UA_MonitoredItem) localMonitoredItems;
    UA_UInt32 lastLocalMonitoredItemId;
#endif

    /* Publish/Subscribe */
#ifdef UA_ENABLE_PUBSUB
    UA_PubSubManager pubSubManager;
#endif
};

/*****************/
/* Node Handling */
/*****************/

/* Deletes references from the node which are not matching any type in the given
 * array. Could be used to e.g. delete all the references, except
 * 'HASMODELINGRULE' */
void UA_Node_deleteReferencesSubset(UA_Node *node, size_t referencesSkipSize,
                                    UA_NodeId* referencesSkip);

/* Calls the callback with the node retrieved from the nodestore on top of the
 * stack. Either a copy or the original node for in-situ editing. Depends on
 * multithreading and the nodestore.*/
typedef UA_StatusCode (*UA_EditNodeCallback)(UA_Server*, UA_Session*,
                                             UA_Node *node, void*);
UA_StatusCode UA_Server_editNode(UA_Server *server, UA_Session *session,
                                 const UA_NodeId *nodeId,
                                 UA_EditNodeCallback callback,
                                 void *data);

/*********************/
/* Utility Functions */
/*********************/

/* A few global NodeId definitions */
extern const UA_NodeId subtypeId;
extern const UA_NodeId hierarchicalReferences;

void setupNs1Uri(UA_Server *server);
UA_UInt16 addNamespace(UA_Server *server, const UA_String name);

UA_Boolean
UA_Node_hasSubTypeOrInstances(const UA_Node *node);

/* Recursively searches "upwards" in the tree following specific reference types */
UA_Boolean
isNodeInTree(void *nsCtx, const UA_NodeId *leafNode,
             const UA_NodeId *nodeToFind, const UA_NodeId *referenceTypeIds,
             size_t referenceTypeIdsSize);

/* Returns an array with the hierarchy of nodes. The start nodes can be returned
 * as well. The returned array starts at the leaf and continues "upwards" or
 * "downwards". Duplicate entries are removed. The parameter `walkDownwards`
 * indicates the direction of search. */
UA_StatusCode
browseRecursive(UA_Server *server,
                size_t startNodesSize, const UA_NodeId *startNodes,
                size_t refTypesSize, const UA_NodeId *refTypes,
                UA_BrowseDirection browseDirection, UA_Boolean includeStartNodes,
                size_t *resultsSize, UA_ExpandedNodeId **results);

/* If refTypes is non-NULL, tries to realloc and increase the length */
UA_StatusCode
referenceSubtypes(UA_Server *server, const UA_NodeId *refType,
                  size_t *refTypesSize, UA_NodeId **refTypes);

/* Returns the recursive type and interface hierarchy of the node */ 
UA_StatusCode
getParentTypeAndInterfaceHierarchy(UA_Server *server, const UA_NodeId *typeNode,
                                   UA_NodeId **typeHierarchy, size_t *typeHierarchySize);

/* Returns the type node from the node on the stack top. The type node is pushed
 * on the stack and returned. */
const UA_Node * getNodeType(UA_Server *server, const UA_Node *node);

/* Write a node attribute with a defined session */
UA_StatusCode
UA_Server_writeWithSession(UA_Server *server, UA_Session *session,
                           const UA_WriteValue *value);


/* Many services come as an array of operations. This function generalizes the
 * processing of the operations. */
typedef void (*UA_ServiceOperation)(UA_Server *server, UA_Session *session,
                                    const void *context,
                                    const void *requestOperation,
                                    void *responseOperation);

UA_StatusCode
UA_Server_processServiceOperations(UA_Server *server, UA_Session *session,
                                   UA_ServiceOperation operationCallback,
                                   const void *context,
                                   const size_t *requestOperations,
                                   const UA_DataType *requestOperationsType,
                                   size_t *responseOperations,
                                   const UA_DataType *responseOperationsType)
    UA_FUNC_ATTR_WARN_UNUSED_RESULT;

/***************************************/
/* Check Information Model Consistency */
/***************************************/

/* Read a node attribute in the context of a "checked-out" node. So the
 * attribute will not be copied when possible. The variant then points into the
 * node and has UA_VARIANT_DATA_NODELETE set. */
void
ReadWithNode(const UA_Node *node, UA_Server *server, UA_Session *session,
             UA_TimestampsToReturn timestampsToReturn,
             const UA_ReadValueId *id, UA_DataValue *v);

UA_StatusCode
readValueAttribute(UA_Server *server, UA_Session *session,
                   const UA_VariableNode *vn, UA_DataValue *v);

/* Test whether the value matches a variable definition given by
 * - datatype
 * - valueranke
 * - array dimensions.
 * Sometimes it can be necessary to transform the content of the value, e.g.
 * byte array to bytestring or uint32 to some enum. If editableValue is non-NULL,
 * we try to create a matching variant that points to the original data. */
UA_Boolean
compatibleValue(UA_Server *server, UA_Session *session, const UA_NodeId *targetDataTypeId,
                UA_Int32 targetValueRank, size_t targetArrayDimensionsSize,
                const UA_UInt32 *targetArrayDimensions, const UA_Variant *value,
                const UA_NumericRange *range);

UA_Boolean
compatibleArrayDimensions(size_t constraintArrayDimensionsSize,
                          const UA_UInt32 *constraintArrayDimensions,
                          size_t testArrayDimensionsSize,
                          const UA_UInt32 *testArrayDimensions);

UA_Boolean
compatibleValueArrayDimensions(const UA_Variant *value, size_t targetArrayDimensionsSize,
                               const UA_UInt32 *targetArrayDimensions);

UA_Boolean
compatibleValueRankArrayDimensions(UA_Server *server, UA_Session *session,
                                   UA_Int32 valueRank, size_t arrayDimensionsSize);

UA_Boolean
compatibleDataType(UA_Server *server, const UA_NodeId *dataType,
                   const UA_NodeId *constraintDataType, UA_Boolean isValue);

UA_Boolean
compatibleValueRanks(UA_Int32 valueRank, UA_Int32 constraintValueRank);

struct BrowseOpts {
    UA_UInt32 maxReferences;
    UA_Boolean recursive;
};

void
Operation_Browse(UA_Server *server, UA_Session *session, const UA_UInt32 *maxrefs,
                 const UA_BrowseDescription *descr, UA_BrowseResult *result);

UA_DataValue
UA_Server_readWithSession(UA_Server *server, UA_Session *session,
                          const UA_ReadValueId *item,
                          UA_TimestampsToReturn timestampsToReturn);

/*****************************/
/* AddNodes Begin and Finish */
/*****************************/

/* Creates a new node in the nodestore. */
UA_StatusCode
AddNode_raw(UA_Server *server, UA_Session *session, void *nodeContext,
            const UA_AddNodesItem *item, UA_NodeId *outNewNodeId);

/* Check the reference to the parent node; Add references. */
UA_StatusCode
AddNode_addRefs(UA_Server *server, UA_Session *session, const UA_NodeId *nodeId,
                const UA_NodeId *parentNodeId, const UA_NodeId *referenceTypeId,
                const UA_NodeId *typeDefinitionId);

/* Type-check type-definition; Run the constructors */
UA_StatusCode
AddNode_finish(UA_Server *server, UA_Session *session, const UA_NodeId *nodeId);

/**********************/
/* Create Namespace 0 */
/**********************/

UA_StatusCode UA_Server_initNS0(UA_Server *server);

UA_StatusCode writeNs0VariableArray(UA_Server *server, UA_UInt32 id, void *v,
                      size_t length, const UA_DataType *type);

_UA_END_DECLS

#endif /* UA_SERVER_INTERNAL_H_ */
