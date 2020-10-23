#ifndef BASYX_SUBMODEL_API_V2_DATASPECIFICATION_IDATASPECIFICATION_H
#define BASYX_SUBMODEL_API_V2_DATASPECIFICATION_IDATASPECIFICATION_H

#include <BaSyx/submodel/api_v2/dataspecification/IDataSpecificationContent.h>

#include <BaSyx/submodel/api_v2/qualifier/IIdentifiable.h>
#include <BaSyx/submodel/api_v2/qualifier/IReferable.h>

namespace basyx {
namespace submodel {	
namespace api {


/**
 * Mandatory members:
 *    DataSpecificationContent
 */
class IDataSpecification : public virtual IIdentifiable
{
public:
	virtual ~IDataSpecification() = 0;

	virtual IDataSpecificationContent & getContent() = 0;

	virtual KeyElements getKeyElementType() const override;
};

inline KeyElements IDataSpecification::getKeyElementType() const { return KeyElements::DataElement; };

inline IDataSpecification::~IDataSpecification() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_DATASPECIFICATION_IDATASPECIFICATION_H */
