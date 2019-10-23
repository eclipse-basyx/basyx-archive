/*
 * SubModel.cpp
 *
 *      Author: wendel
 */

#include "SubModel.h"

namespace basyx {
namespace aas {

SubModel::SubModel()
{

}

void SubModel::setProperties(const basyx::objectMap_t & properties)
{
  this->properties = properties;
}

void SubModel::setOperations(const basyx::objectMap_t & operations)
{
  this->properties = properties;
}

}
}
