#ifndef BASYX_SUBMODEL_API_V2_DATASPECIFICATION_IDATASPECIFICATIONCONTENT_H
#define BASYX_SUBMODEL_API_V2_DATASPECIFICATION_IDATASPECIFICATIONCONTENT_H

namespace basyx {
namespace submodel {
namespace api {

class IDataSpecificationContent
{
public:
	virtual ~IDataSpecificationContent() = 0;
};

inline IDataSpecificationContent::~IDataSpecificationContent() = default;

}
}
}

#endif /* BASYX_SUBMODEL_API_V2_DATASPECIFICATION_IDATASPECIFICATIONCONTENT_H */

