/*
 * IAsset.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IAsset_H_
#define BASYX_METAMODEL_IAsset_H_


#include "IHasDataSpecification.h"
#include "qualifier/haskind/IHasKind.h"
#include "IIdentifiable.h"
#include "IReference.h"

class IAsset : virtual IHasDataSpecification, IHasKind, IIdentifiable
{
public:
	virtual ~IAsset() = default;

	virtual IReference getAssetIdentificationModel() = 0;
	virtual void setAssetIdentificationModel(IReference submodel) = 0;
};

#endif
