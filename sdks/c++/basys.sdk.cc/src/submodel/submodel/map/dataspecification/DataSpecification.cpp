/*
 * DataSpecification.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/dataspecification/DataSpecification.h>
#include <BaSyx/submodel/map/dataspecification/DataSpecificationContent.h>

namespace basyx {
namespace submodel {

DataSpecification::DataSpecification(basyx::object obj)
  : vab::ElementMap(obj)
{}

std::shared_ptr<IDataSpecificationContent> DataSpecification::getContent() const
{
  return std::make_shared<DataSpecificationContent>(this->map.getProperty(IDataSpecification::Path::Content));
}

void DataSpecification::setContent(const std::shared_ptr<IDataSpecificationContent>& content)
{
  this->map.insertKey(IDataSpecification::Path::Content, DataSpecificationContent(*content).getMap());
}

}
}
