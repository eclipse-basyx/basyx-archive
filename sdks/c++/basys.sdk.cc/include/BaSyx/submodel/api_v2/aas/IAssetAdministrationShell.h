#ifndef BASYX_SUBMODEL_API_V2_AAS_IASSETADMINISTRATIONSHELL_H
#define BASYX_SUBMODEL_API_V2_AAS_IASSETADMINISTRATIONSHELL_H

#include <BaSyx/submodel/simple/qualifier/Identifiable.h>
#include <BaSyx/submodel/api_v2/qualifier/IHasDataSpecification.h>
#include <BaSyx/submodel/api_v2/common/IModelType.h>
#include <BaSyx/submodel/api_v2/aas/IAsset.h>
#include <BaSyx/submodel/api_v2/ISubModel.h>

#include <memory>

namespace basyx {
namespace submodel {
namespace api {

class IAssetAdministrationShell : 
	public virtual IHasDataSpecification, 
	public virtual IIdentifiable,
	public virtual IModelType
{
public:
	using SubmodelContainer_t = IElementContainer<ISubModel>;
public:
	virtual ~IAssetAdministrationShell() = 0;
	
	virtual IAsset & getAsset() = 0;

	virtual IReference * getDerivedFrom() = 0;
	virtual void setDerivedFrom(const IReference & derivedFrom) = 0;

	virtual SubmodelContainer_t & getSubmodels() = 0;
};

inline IAssetAdministrationShell::~IAssetAdministrationShell() = default;


}
}
}

#endif /* BASYX_SUBMODEL_API_V2_AAS_IASSETADMINISTRATIONSHELL_H */
