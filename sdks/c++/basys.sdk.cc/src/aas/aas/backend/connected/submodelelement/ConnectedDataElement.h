/*
 * ConnectedDataElement.h
 *
 *      Author: wendel
 */


#ifndef BASYX_METAMODEL_CONNECTEDDATAELEMENT_H_
#define BASYX_METAMODEL_CONNECTEDDATAELEMENT_H_

#include "vab/core/proxy/IVABElementProxy.h"
#include "aas/reference/IReference.h"
#include "aas/qualifier/qualifiable/IConstraint.h"
#include "aas/backend/connected/submodelelement/ConnectedSubmodelElement.h"

namespace basyx {
namespace aas {
namespace backend {

class ConnectedDataElement : public connected::ConnectedSubmodelElement
{
public:
  ConnectedDataElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
  ~ConnectedDataElement() = default;

  basyx::objectCollection_t getDataSpecificationReferences() const override;
  //void setDataSpecificationReferences(const basyx::objectCollection_t & data_specification_references) override;

  std::string getIdShort() const override;
  //void setIdShort(const std::string & idShort) override;

  std::string getCategory() const override;
  //void setCategory(const std::string & category) override;

  std::string getDescription() const override;
  //void setDescription(const std::string & description) override;

  basyx::any getParent() const override;
  //void setParent(const basyx::any & parent) override;

  std::string getId() const override;
  void setId(const std::string & id) override;

  basyx::objectCollection_t getQualifier() const override;
  //void setQualifier(const basyx::objectCollection_t & qualifiers) override;

  basyx::any getSemanticId() const override;
  void setSemanticID(const basyx::any & semanticId) override;

  std::string getHasKindReference() const override;
  //void setHasKindReference(const std::string & kind) override;

private:
  basyx::objectCollection_t getProxyCollection(const std::string & path) const;

protected:
  std::string getIdWithLocalCheck() const;
  void setIdWithLocalCheck(const std::string & id);
};

}
}
}

#endif
