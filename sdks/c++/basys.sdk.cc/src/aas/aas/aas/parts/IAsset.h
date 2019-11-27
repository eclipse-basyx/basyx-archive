/*
 * IAsset.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IAsset_H_
#define BASYX_METAMODEL_IAsset_H_


#include "submodel/api/qualifier/IHasDataSpecification.h"
#include "submodel/api/qualifier/haskind/IHasKind.h"
#include "submodel/api/qualifier/IIdentifiable.h"
#include "submodel/api/reference/IReference.h"

namespace basyx {
namespace aas {
namespace parts {

class IAsset : virtual qualifier::IHasDataSpecification, qualifier::haskind::IHasKind, qualifier::IIdentifiable
{
public:
  virtual ~IAsset() = default;

  virtual std::shared_ptr<reference::IReference> getAssetIdentificationModel() const = 0;
  virtual void setAssetIdentificationModel(const std::shared_ptr<reference::IReference> & submodel) = 0;
};

}
}
}

#endif
