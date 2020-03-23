/*
 * DataSpecificationContent.h
 *
 *      Author: wendel
 */

#ifndef DATASPECIFICATIONCONTENT_H_
#define DATASPECIFICATIONCONTENT_H_

#include <BaSyx/submodel/api/dataspecification/IDataSpecificationContent.h>

namespace basyx {
namespace submodel {


class DataSpecificationContent
  : public IDataSpecificationContent
  , public virtual vab::ElementMap  
{
public:
	using vab::ElementMap::ElementMap;

	DataSpecificationContent() = default;
	DataSpecificationContent(const IDataSpecificationContent & other);

	~DataSpecificationContent() = default;
};

}
}

#endif
