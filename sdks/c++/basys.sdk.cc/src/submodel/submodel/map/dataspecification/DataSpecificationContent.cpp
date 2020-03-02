/*
 * DataSpecificationContent.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/dataspecification/DataSpecificationContent.h>

namespace basyx {
namespace submodel {

DataSpecificationContent::DataSpecificationContent(basyx::object obj)
  : vab::ElementMap(obj)
{}

DataSpecificationContent::DataSpecificationContent(const IDataSpecificationContent & other)
  : vab::ElementMap()
{
}

}
}
