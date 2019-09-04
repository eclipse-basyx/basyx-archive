/*
 * SubModel.h
 *
 *      Author: wendel
 */

#include "SubModel.h"

SubModel::SubModel()
{

}

std::unordered_map<std::string, std::shared_ptr<IProperty>> SubModel::getProperties() const
{
  return this->properties;
}

void SubModel::setProperties(const std::unordered_map<std::string, std::shared_ptr<IProperty>> & properties)
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