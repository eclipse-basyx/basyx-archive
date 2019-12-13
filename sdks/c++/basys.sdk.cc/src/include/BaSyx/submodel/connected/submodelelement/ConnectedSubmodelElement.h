/*
 * ConnectedSubmodelElement.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_CONNECTED_CONNECTEDSUBMODELELEMENT_H_
#define AAS_BACKEND_SUBMODELELEMENT_CONNECTED_CONNECTEDSUBMODELELEMENT_H_

#include <BaSyx/submodel/api/submodelelement/ISubmodelElement.h>
#include <BaSyx/submodel/connected/ConnectedElement.h>
#include <BaSyx/vab/core/proxy/IVABElementProxy.h>

namespace basyx {
namespace submodel {

class ConnectedSubmodelElement : public ConnectedElement, public ISubmodelElement
{
public:
  ConnectedSubmodelElement(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy);
  ~ConnectedSubmodelElement() = default;

  basyx::specificCollection_t<IReference> getDataSpecificationReferences() const override;
  std::string getIdShort() const override;
  std::string getCategory() const override;
  Description getDescription() const override;
  std::shared_ptr<IReference> getParent() const override;
  basyx::specificCollection_t<IConstraint> getQualifier() const override;
  std::shared_ptr<IReference> getSemanticId() const override;
  Kind getHasKindReference() const override;
   
protected:
  std::string getIdWithLocalCheck() const;
  void setIdWithLocalCheck(const std::string & id);
  basyx::object::object_list_t getProxyCollection(const std::string & path) const;
};

}
}

#endif
