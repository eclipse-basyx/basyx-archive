/*
 * ConnectedSubmodelElement.h
 *
 *      Author: wendel
 */

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

  basyx::objectCollection_t getDataSpecificationReferences() const override;
  std::string getIdShort() const override;
  std::string getCategory() const override;
  std::string getDescription() const override;
  basyx::any getParent() const override;
  basyx::objectCollection_t getQualifier() const override;
  basyx::any getSemanticId() const override;
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
