#ifndef BASYX_SUBMODEL_SIMPLE_DATASPECIFICATION_DATASPECIFICATIONCONTENT_H
#define BASYX_SUBMODEL_SIMPLE_DATASPECIFICATION_DATASPECIFICATIONCONTENT_H

#include <BaSyx/submodel/api_v2/dataspecification/IDataSpecificationContent.h>

namespace basyx {
namespace submodel {
namespace simple {


class DataSpecificationContent
: public api::IDataSpecificationContent
{
public:
	DataSpecificationContent() = default;
	DataSpecificationContent(const api::IDataSpecificationContent & other);

	~DataSpecificationContent() = default;
};

}
}
}

#endif /* BASYX_SUBMODEL_SIMPLE_DATASPECIFICATION_DATASPECIFICATIONCONTENT_H */
