/*
 * ConnectedSubmodelElement.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_CONNECTED_CONNECTEDSUBMODELELEMENT_H_
#define AAS_BACKEND_SUBMODELELEMENT_CONNECTED_CONNECTEDSUBMODELELEMENT_H_

#include "aas/submodelelement/ISubmodelElement.h"
#include "backend/connected/aas/ConnectedElement.h"
#include "vab/core/proxy/IVABElementProxy.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

class ConnectedSubmodelElement : public backend::ConnectedElement, public submodelelement::ISubmodelElement
{
public:
  ConnectedSubmodelElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
  ~ConnectedSubmodelElement() = default;

  basyx::specificCollection_t<reference::IReference> getDataSpecificationReferences() const override;
  std::string getIdShort() const override;
  std::string getCategory() const override;
  qualifier::impl::Description getDescription() const override;
  std::shared_ptr<reference::IReference> getParent() const override;
  basyx::objectCollection_t getQualifier() const override;
  std::shared_ptr<reference::IReference> getSemanticId() const override;
  std::string getHasKindReference() const override;
   
protected:
  std::string getIdWithLocalCheck() const;
  void setIdWithLocalCheck(const std::string & id);
  basyx::objectCollection_t getProxyCollection(const std::string & path) const;
};

}
}
}
}

#endif
