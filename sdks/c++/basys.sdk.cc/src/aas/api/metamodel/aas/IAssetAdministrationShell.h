/*
 * IAssetAdministrationShell.h
 *
 *      Author: wendel
 */

#ifndef AAS_IASSETADMINISTRATIONSHELL_H_
#define AAS_IASSETADMINISTRATIONSHELL_H_

#include "aas/submodelelement/IElement.h"
#include "aas/qualifier/IHasDataSpecification.h"
#include "aas/qualifier/IIdentifiable.h"
#include "aas/ISubModel.h"
#include "aas/security/ISecurity.h"
#include "aas/reference/IReference.h"
#include "aas/parts/IView.h"
#include "aas/parts/IConceptDictionary.h"
#include "basyx/types.h"
#include "impl/metamodel/hashmap/descriptor/SubModelDescriptor.h"

#include <memory>

namespace basyx {
namespace aas {
namespace api {

class IAssetAdministrationShell : public qualifier::IHasDataSpecification, public qualifier::IIdentifiable
{
public:
  virtual basyx::specificMap_t<ISubModel> getSubModels() const = 0;
  virtual void addSubModel(const descriptor::SubModelDescriptor subModelDescriptor) = 0;
  virtual std::shared_ptr<ISecurity> getSecurity() const = 0;
  virtual std::shared_ptr<reference::IReference> getDerivedFrom() const = 0;
  virtual std::shared_ptr<reference::IReference> getAsset() const = 0;
  virtual void setSubModel(const basyx::specificCollection_t<descriptor::SubModelDescriptor> submodels) const = 0;
  virtual basyx::specificCollection_t<descriptor::SubModelDescriptor> getSubModelDescriptors() const = 0;
  virtual basyx::specificCollection_t<IView> getViews() const = 0;
  virtual basyx::specificCollection_t<IConceptDictionary> getConceptDictionary() const = 0;
};

}
}
}

#endif
