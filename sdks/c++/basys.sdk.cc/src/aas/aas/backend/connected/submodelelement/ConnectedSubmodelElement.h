/*
 * ConnectedSubmodelElement.h
 *
 *      Author: wendel
 */

#include "aas/submodelelement/ISubmodelElement.h"
#include "aas/backend/connected/ConnectedElement.h"
#include "vab/core/proxy/IVABElementProxy.h"

namespace basyx {
namespace aas {
namespace backend {
namespace connected {

class ConnectedSubmodelElement : public backend::ConnectedElement, public submodelelement::ISubmodelElement
{
public:
  ConnectedSubmodelElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);

  // Inherited via ConnectedElement
  virtual void setId(const std::string & id) override;
  virtual std::string getId() const override;

  // Inherited via ISubmodelElement
  virtual basyx::objectCollection_t getDataSpecificationReferences() const override;
  virtual std::string getIdShort() const override;
  virtual std::string getCategory() const override;
  virtual std::string getDescription() const override;
  virtual basyx::any getParent() const override;
  virtual basyx::objectCollection_t getQualifier() const override;
  virtual basyx::any getSemanticId() const override;
  virtual void setSemanticID(const basyx::any & semantic_identifier) override;
  virtual std::string getHasKindReference() const override;
};

}
}
}
}
