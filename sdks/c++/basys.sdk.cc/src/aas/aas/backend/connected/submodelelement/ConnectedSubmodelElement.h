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
  virtual void setDataSpecificationReferences(const basyx::objectCollection_t & references) override;
  virtual std::string getIdShort() const override;
  virtual void setIdShort(const std::string & idShort) override;
  virtual std::string getCategory() const override;
  virtual void setCategory(const std::string & category) override;
  virtual std::string getDescription() const override;
  virtual void setDescription(const std::string & description) override;
  virtual basyx::any getParent() const override;
  virtual void setParent(const basyx::any & parent) override;
  virtual void setQualifier(const basyx::objectCollection_t & qualifiers) override;
  virtual basyx::objectCollection_t getQualifier() const override;
  virtual basyx::any getSemanticId() const override;
  virtual void setSemanticID(const basyx::any & semantic_identifier) override;
  virtual std::string getHasKindReference() const override;
  virtual void setHasKindReference(const std::string & kind) override;
};

}
}
}
}
