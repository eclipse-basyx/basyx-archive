#ifndef AAS_SUBMODEL_ELEMENT_H
#define AAS_SUBMODEL_ELEMENT_H

#include <BaSyx/opcua/aas/model/AASModelNode.h>
#include <BaSyx/opcua/aas/metamodel/AASSubModelElementType.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            template<typename CONNECTOR_TYPE = Client>
            class AASSubModelElement final : public AASModelNode<CONNECTOR_TYPE>
            {
                static constexpr const char loggerName[] = "basyx::opcua::aas::AASSubModelElement";
            public:
                ~AASSubModelElement() = default;

                AASSubModelElement() = delete;

                AASSubModelElement(CONNECTOR_TYPE& t_connector,
                    const NodeId& t_submodelElementNode) :
                    AASModelNode<CONNECTOR_TYPE>(t_connector,
                        t_submodelElementNode,
                        NodeId::nullNode(),
                        std::string(),
                        std::string(),
                        NodeDescription(),
                        loggerName) {}

                void setReferableIdShort(const std::string& t_idShort);
                void setReferableCategory(const std::string& t_idShort);
                void setKind(const Kind& t_kind);

                std::string getReferableIdShort();
                std::string getReferableCategory();
                Kind getKind();
            };

            template<typename CONNECTOR_TYPE>
            constexpr char AASSubModelElement<CONNECTOR_TYPE>::loggerName[];

            template<typename CONNECTOR_TYPE>
            void AASSubModelElement<CONNECTOR_TYPE>::setReferableIdShort(const std::string& t_idShort)
            {
                UA_StatusCode scode;

                auto idShortNode
                {
                   this->getServices().translateBrowsePathToNodeId(
                        metamodel::AASSubModelElementType::getIdShortPath(
                            this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                        this->getNodeId()
                   )
                };

                scode = this->getServices().writeValue(idShortNode, t_idShort);

                if (scode != UA_STATUSCODE_GOOD) {
                    this->getLogger().error("IdShort node value cannot be written -" + shared::diag::getErrorString(scode));
                }
            }

            template<typename CONNECTOR_TYPE>
            void AASSubModelElement<CONNECTOR_TYPE>::setReferableCategory(const std::string& t_category)
            {
                UA_StatusCode scode;
                auto categoryNode
                {
                   this->getServices().translateBrowsePathToNodeId(
                        metamodel::AASSubModelElementType::getCategoryPath(
                            this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                        this->getNodeId()
                   )
                };

                scode = this->getServices().writeValue(categoryNode, t_category);

                if (scode != UA_STATUSCODE_GOOD) {
                    this->getLogger().error("Category node value cannot be written -" + shared::diag::getErrorString(scode));
                }
            }

            template<typename CONNECTOR_TYPE>
            void AASSubModelElement<CONNECTOR_TYPE>::setKind(const Kind& t_kind)
            {
                UA_StatusCode scode;
                auto kindNode
                {
                   this->getServices().translateBrowsePathToNodeId(
                        metamodel::AASSubModelElementType::getKindPath(
                            this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                        this->getNodeId()
                   )
                };
                scode = this->getServices().writeValue(kindNode, KindtoString(t_kind));

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("Kind node value cannot be written -" + shared::diag::getErrorString(scode));
                }
            }

            template<typename CONNECTOR_TYPE>
            Kind AASSubModelElement<CONNECTOR_TYPE>::getKind()
            {
                std::string kindType;
                UA_StatusCode scode;

                auto kindNode
                {
                   this->getConnector().translateBrowsePathToNodeId(
                        metamodel::AASSubModelElementType::getKindPath(
                            this->getConnector().getNamespaceIndexdefault(), this->getConnector(), this->getNodeId()),
                        this->getNodeId()
                   )
                };
                scode = this->getServices().readValue(kindNode, kindType);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("Kind node value cannot be read -" + shared::diag::getErrorString(scode));
                }
                return KindfromString(kindType);
            }

            template<typename CONNECTOR_TYPE>
            std::string AASSubModelElement<CONNECTOR_TYPE>::getReferableCategory()
            {
                std::string catetgory;
                UA_StatusCode scode;
                auto categoryNode
                {
                   this->getServices().translateBrowsePathToNodeId(
                       metamodel::AASSubModelElementType::getCategoryPath(
                           this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                        this->getNodeId()
                   )
                };
                scode = this->getServices().readValue(categoryNode, catetgory);
                if (scode != UA_STATUSCODE_GOOD) {
                    this->getLogger().error("Category node value cannot be read -" + shared::diag::getErrorString(scode));
                }
                return catetgory;
            }

            template<typename CONNECTOR_TYPE>
            std::string AASSubModelElement<CONNECTOR_TYPE>::getReferableIdShort()
            {
                std::string idshort;
                UA_StatusCode scode;

                auto idShortNode{
                   this->getServices().translateBrowsePathToNodeId(
                       metamodel::AASSubModelElementType::getIdShortPath(
                           this->getConnector().getNamespaceIndexDefault(), this->getConnector(), this->getNodeId()),
                        this->getNodeId()
                   )
                };

                scode = this->getServices().readValue(idShortNode, idshort);

                if (scode != UA_STATUSCODE_GOOD)
                {
                    this->getLogger().error("IdShort node value cannot be read -" + shared::diag::getErrorString(scode));
                }

                return idshort;
            }
        }
    }
}
#endif