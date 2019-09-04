/*
 * IAsset.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IAsset_H_
#define BASYX_METAMODEL_IAsset_H_


#include "qualifier/IHasDataSpecification.h"
#include "qualifier/haskind/IHasKind.h"
#include "qualifier/IIdentifiable.h"
#include "reference/IReference.h"

class IAsset : virtual IHasDataSpecification, IHasKind, IIdentifiable
{
public:
	virtual ~IAsset() = default;

	virtual IReference getAssetIdentificationModel() const = 0;
	virtual void setAssetIdentificationModel(const IReference & submodel) = 0;
};

#endif
