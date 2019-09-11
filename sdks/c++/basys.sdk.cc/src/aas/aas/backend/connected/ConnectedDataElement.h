/*
 * ConnectedDataElement.h
 *
 *      Author: wendel
 */


#ifndef BASYX_METAMODEL_CONNECTEDDATAELEMENT_H_
#define BASYX_METAMODEL_CONNECTEDDATAELEMENT_H_

#include "vab/core/proxy/VABElementProxy.h"
#include "aas/reference/IReference.h"
#include "aas/qualifier/qualifiable/IConstraint.h"
#include "aas/submodelelement/connected/ConnectedSubmodelElement.h"

namespace basyx {
namespace aas {
namespace backend {

class ConnectedDataElement : public submodelelement::connected::ConnectedSubmodelElement
{
public:
  ConnectedDataElement(std::shared_ptr<vab::core::proxy::VABElementProxy> proxy);
  ~ConnectedDataElement() = default;

  std::vector<std::shared_ptr<IReference>> getDataSpecificationReferences() const;
  void setDataSpecificationReferences(const std::vector<std::shared_ptr<IReference>> & ref);

  std::string getIdshort() const;
  void setIdshort(const std::string & idShort);

  std::string getCategory() const;
  void setCategory(const std::string & category);

  std::string getDescription() const;
  void setDescription(const std::string & description);

  std::shared_ptr<IReference> getParent() const;
  void setParent(const std::shared_ptr<IReference> & obj);

  std::string getId() const;
  void setId(const std::string & id);

  std::vector<std::shared_ptr<IConstraint>> getQualifier() const;
  void setQualifier(const std::vector<std::shared_ptr<IConstraint>> & qualifiers);

  std::shared_ptr<std::shared_ptr<IReference>> getSemanticId() const;
  void setSemanticID(const std::shared_ptr<std::shared_ptr<IReference>> & ref);

  std::string getHasKindReference() const;
  void setHasKindReference(const std::string & kind);

private:
  std::string getValue(const std::string & path) const;
  void setValue(const std::string & path, const basyx::any value) const;
};

}
}
}

#endif
