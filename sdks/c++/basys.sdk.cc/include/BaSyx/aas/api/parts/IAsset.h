/*
 * IAsset.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IAsset_H_
#define BASYX_METAMODEL_IAsset_H_


#include <BaSyx/submodel/api/qualifier/IHasDataSpecification.h>
#include <BaSyx/submodel/api/qualifier/IHasKind.h>
#include <BaSyx/submodel/api/qualifier/IIdentifiable.h>
#include <BaSyx/submodel/api/reference/IReference.h>

namespace basyx {
namespace aas {

class IAsset :
	public virtual submodel::IHasDataSpecification,
	public virtual submodel::IHasKind,
	public virtual submodel::IIdentifiable
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