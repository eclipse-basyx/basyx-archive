/*
 * IAsset.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IASSET_H_
#define BASYX_METAMODEL_IASSET_H_

#include "aas/qualifier/IHasDataSpecification.h"
#include "aas/qualifier/haskind/IHasKind.h"
#include "aas/qualifier/IIdentifiable.h"
#include "aas/reference/IReference.h"

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
