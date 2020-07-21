#ifndef BASYX_SUBMODEL_API_V2_PARTS_ICONCEPTDESCRIPTION_H
#define BASYX_SUBMODEL_API_V2_PARTS_ICONCEPTDESCRIPTION_H

#include <BaSyx/submodel/api_v2/qualifier/IHasDataSpecification.h>
#include <BaSyx/submodel/api_v2/qualifier/IIdentifiable.h>
#include <BaSyx/submodel/simple/reference/Reference.h>
#include <BaSyx/submodel/api_v2/common/IElementContainer.h>
#include <BaSyx/submodel/api_v2/dataspecification/IDataSpecification.h>

#include <string>
#include <vector>

namespace basyx {
namespace submodel {
namespace api {

class IConceptDescription : 
	public virtual IHasDataSpecification, 
	public virtual IIdentifiable
{
public:
	virtual ~IConceptDescription() = 0;

	virtual const std::vector<std::unique_ptr<IReference>> & getIsCaseOf() const = 0;
	virtual const IElementContainer<IDataSpecification> & getEmbeddedDataSpecification() const = 0;
};

inline IConceptDescription::~IConceptDescription() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_PARTS_ICONCEPTDESCRIPTION_H */
