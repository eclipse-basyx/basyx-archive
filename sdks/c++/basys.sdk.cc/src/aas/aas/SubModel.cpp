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

void SubModel::setProperties(const std::unordered_map<std::string, std::shared_ptr<submodelelement::property::IProperty>> & properties)
{
  this->properties = properties;
}

void SubModel::setOperations(const std::unordered_map<std::string, std::shared_ptr<submodelelement::operation::IOperation>> & operations)
{
  this->properties = properties;
}

}
}
