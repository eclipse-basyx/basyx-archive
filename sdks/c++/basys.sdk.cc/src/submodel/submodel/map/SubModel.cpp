/*
 * SubModel.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/SubModel.h>

namespace basyx {
namespace submodel {

SubModel::SubModel()
{

}

void SubModel::setProperties(const basyx::object::object_map_t & properties)
{
  this->properties = properties;
}

void SubModel::setOperations(const basyx::object::object_map_t & operations)
{
  this->properties = properties;
}

}
}
