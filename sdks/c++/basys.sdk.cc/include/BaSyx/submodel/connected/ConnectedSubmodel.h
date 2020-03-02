/*
 * ConnectedSubmodel.h
 *
 *      Author: wendel
 */

#ifndef CONNECTEDSUBMODEL_H_
#define CONNECTEDSUBMODEL_H_

#include <BaSyx/submodel/api/ISubModel.h>

#include <BaSyx/submodel/map/IVABElementContainer.h>
//#include <BaSyx/submodel/map/qualifier/Kind.h>
#include <BaSyx/submodel/connected/ConnectedElement.h>
#include <BaSyx/submodel/api/reference/IReference.h>

namespace basyx {
namespace submodel {

class ConnectedSubmodel : 
	public ISubModel, 
	public ConnectedElement
{
public:
  ~ConnectedSubmodel() = default;

  // Inherited via ISubModel
  virtual std::shared_ptr<IReference> getSemanticId() const override;
  virtual std::shared_ptr<IAdministrativeInformation> getAdministration() const override;
  virtual std::shared_ptr<IIdentifier> getIdentification() const override;
  virtual basyx::specificCollection_t<IReference> getDataSpecificationReferences() const override;
  virtual Kind getHasKindReference() const override;
  virtual void setDataElements(const basyx::specificMap_t<IProperty> & properties);
  virtual void setOperations(const basyx::specificMap_t<IOperation> & operations);
  virtual std::string getIdShort() const override;
  virtual std::string getCategory() const override;
  virtual Description getDescription() const override;
  virtual std::shared_ptr<IReference> getParent() const override;
  virtual void addSubModelElement(const std::shared_ptr<ISubmodelElement> & element) override;
  virtual basyx::specificMap_t<IDataElement> getDataElements() const override;
  virtual basyx::specificMap_t<IOperation> getOperations() const override;

private:
  basyx::object::object_map_t local_map;
};

}
}

#endif
