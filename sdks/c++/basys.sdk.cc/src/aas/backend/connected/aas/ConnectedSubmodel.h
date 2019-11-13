/*
 * ConnectedSubmodel.h
 *
 *      Author: wendel
 */

#ifndef CONNECTEDSUBMODEL_H_
#define CONNECTEDSUBMODEL_H_

#include "aas/ISubModel.h"

#include "impl/metamodel/hashmap/IVABElementContainer.h"
#include "impl/metamodel/hashmap/aas/qualifier/haskind/Kind.h"
#include "backend/connected/aas/ConnectedElement.h"
#include "aas/reference/IReference.h"

namespace basyx {
namespace aas {
namespace backend {

class ConnectedSubmodel : public ISubModel, backend::ConnectedElement
{
public:
  ~ConnectedSubmodel() = default;

  // Inherited via ISubModel
  virtual std::shared_ptr<reference::IReference> getSemanticId() const override;
  virtual std::shared_ptr<qualifier::IAdministrativeInformation> getAdministration() const override;
  virtual std::shared_ptr<identifier::IIdentifier> getIdentification() const override;
  virtual basyx::specificCollection_t<reference::IReference> getDataSpecificationReferences() const override;
  virtual submodel::metamodel::map::qualifier::haskind::Kind getHasKindReference() const override;
  virtual void setProperties(const basyx::objectMap_t & properties) override;
  virtual void setOperations(const basyx::objectMap_t & operations) override;
  virtual std::string getIdShort() const override;
  virtual std::string getCategory() const override;
  virtual qualifier::impl::Description getDescription() const override;
  virtual std::shared_ptr<reference::IReference> getParent() const override;
  virtual void addSubModelElement(const std::shared_ptr<submodelelement::ISubmodelElement> & element) override;
  virtual basyx::specificMap_t<submodelelement::IDataElement> getDataElements() const override;
  virtual basyx::specificMap_t<submodelelement::operation::IOperation> getOperations() const override;

private:
  basyx::objectMap_t local_map;
};

}
}
}

#endif
