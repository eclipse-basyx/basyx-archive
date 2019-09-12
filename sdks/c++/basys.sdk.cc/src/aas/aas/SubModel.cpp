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

std::unordered_map<std::string, std::shared_ptr<submodelelement::property::IProperty>> SubModel::getProperties() const
{
  return this->properties;
}

void SubModel::setProperties(const std::unordered_map<std::string, std::shared_ptr<submodelelement::property::IProperty>> & properties)
{
  this->properties = properties;
}

std::unordered_map<std::string, std::shared_ptr<IOperation>> SubModel::getOperations() const
{
  return this->operations;
}

void SubModel::setOperations(const std::unordered_map<std::string, std::shared_ptr<IOperation>> & operations)
{
  this->properties = properties;
}

std::unordered_map<std::string, basyx::any> SubModel::getElements() const
{
  return this->submodel_elements;
}

}
}
