/*
 * IAsset.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IAsset_H_
#define BASYX_METAMODEL_IAsset_H_


#include "submodel/api/qualifier/IHasDataSpecification.h"
#include "submodel/api/qualifier/IHasKind.h"
#include "submodel/api/qualifier/IIdentifiable.h"
#include "submodel/api/reference/IReference.h"

namespace basyx {
namespace aas {

class IAsset :
	public submodel::IHasDataSpecification,
	public submodel::IHasKind,
	public submodel::IIdentifiable
{
public:
  struct Path
  {
    static constexpr char AssetIdentificationModel[] = "assetIdentificationModel";
    static constexpr char ModelType[] = "Asset";
    static constexpr char BillOfMaterial[] = "billOfMaterial";
  };
public:
	virtual ~IAsset() = default;

	virtual std::shared_ptr<submodel::IReference> getAssetIdentificationModel() const = 0;
  virtual std::shared_ptr<submodel::IReference> getBillOfMaterial() const = 0;
};

}
}

#endif