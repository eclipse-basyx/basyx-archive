/*
 * ConnectedSubmodel.h
 *
 *      Author: wendel
 */

#ifndef CONNECTEDSUBMODEL_H_
#define CONNECTEDSUBMODEL_H_

#include "submodel/api/ISubModel.h"

#include "submodel/map/IVABElementContainer.h"
//#include "submodel/map/qualifier/Kind.h"
#include "submodel/connected/ConnectedElement.h"
#include "submodel/api/reference/IReference.h"

namespace basyx {
namespace submodel {
namespace backend {

class ConnectedSubmodel : 
	public api::ISubModel, 
	public backend::ConnectedElement
{
public:
  ~ConnectedSubmodel() = default;

  // Inherited via ISubModel
  virtual std::shared_ptr<reference::IReference> getSemanticId() const override;
  virtual std::shared_ptr<api::qualifier::IAdministrativeInformation> getAdministration() const override;
  virtual std::shared_ptr<identifier::IIdentifier> getIdentification() const override;
  virtual basyx::specificCollection_t<reference::IReference> getDataSpecificationReferences() const override;
  virtual api::qualifier::Kind getHasKindReference() const override;
  virtual void setProperties(const basyx::object::object_map_t & properties) override;
  virtual void setOperations(const basyx::object::object_map_t & operations) override;
  virtual std::string getIdShort() const override;
  virtual std::string getCategory() const override;
  virtual map::qualifier::Description getDescription() const override;
  virtual std::shared_ptr<reference::IReference> getParent() const override;
  virtual void addSubModelElement(const std::shared_ptr<api::submodelelement::ISubmodelElement> & element) override;
  virtual basyx::specificMap_t<submodelelement::IDataElement> getDataElements() const override;
  virtual basyx::specificMap_t<submodelelement::operation::IOperation> getOperations() const override;

private:
  basyx::object::object_map_t local_map;
};

}
}
}

#endif
