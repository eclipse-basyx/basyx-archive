/*
 * IAssetAdministrationShell.h
 *
 *      Author: wendel
 */

#ifndef AAS_IASSETADMINISTRATIONSHELL_H_
#define AAS_IASSETADMINISTRATIONSHELL_H_

#include "submodel/api/qualifier/IHasDataSpecification.h"
#include "submodel/api/qualifier/IIdentifiable.h"
#include "submodel/api/ISubModel.h"
#include "aas/aas/security/ISecurity.h"
#include "submodel/api/reference/IReference.h"
#include "aas/aas/parts/IAsset.h"
#include "aas/aas/parts/IView.h"
#include "aas/aas/parts/IConceptDictionary.h"
#include "basyx/types.h"
#include "impl/metamodel/hashmap/descriptor/SubModelDescriptor.h"

#include <memory>

namespace basyx {
namespace aas {
namespace api {

class IAssetAdministrationShell : 
	public submodel::IHasDataSpecification, 
	public submodel::IIdentifiable
{
public:
  virtual basyx::specificMap_t<submodel::ISubModel> getSubModels() const = 0;
  virtual void addSubModel(const descriptor::SubModelDescriptor & subModelDescriptor) = 0;
  virtual std::shared_ptr<security::ISecurity> getSecurity() const = 0;
  virtual std::shared_ptr<submodel::IReference> getDerivedFrom() const = 0;
  virtual std::shared_ptr<IAsset> getAsset() const = 0;
  virtual void setSubmodels(const basyx::specificCollection_t<descriptor::SubModelDescriptor> & submodels) = 0;
  virtual basyx::specificCollection_t<descriptor::SubModelDescriptor> getSubModelDescriptors() const = 0;
  virtual basyx::specificCollection_t<IView> getViews() const = 0;
  virtual basyx::specificCollection_t<IConceptDictionary> getConceptDictionary() const = 0;
};

}
}
}

#endif