#ifndef BASYX_SUBMODEL_API_V2_ISUBMODEL_H
#define BASYX_SUBMODEL_API_V2_ISUBMODEL_H

#include <BaSyx/submodel/api_v2/qualifier/IHasDataSpecification.h>
#include <BaSyx/submodel/api_v2/qualifier/IIdentifiable.h>
#include <BaSyx/submodel/api_v2/qualifier/IHasKind.h>
#include <BaSyx/submodel/api_v2/qualifier/IHasSemantics.h>
#include <BaSyx/submodel/api_v2/qualifier/IQualifiable.h>
#include <BaSyx/submodel/api_v2/common/IElementContainer.h>


namespace basyx {
namespace submodel {
namespace api {

class ISubModel : 
	public virtual IIdentifiable,
	public virtual IHasSemantics, 
  public virtual IQualifiable,
	public virtual IHasDataSpecification,
	public virtual IHasKind
{
public:
	virtual ~ISubModel() = 0;

	virtual IElementContainer<ISubmodelElement> & submodelElements() = 0;
	virtual const IElementContainer<ISubmodelElement> & submodelElements() const = 0;
};

inline ISubModel::~ISubModel() = default;


}
}
}


#endif /* BASYX_SUBMODEL_API_V2_ISUBMODEL_H */
