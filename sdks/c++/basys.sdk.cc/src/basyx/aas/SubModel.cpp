/*
 * SubModel.h
 *
 *      Author: wendel
 */

#include "SubModel.h"

SubModel::SubModel()
{

}

std::unordered_map<std::string, std::shared_ptr<IProperty>> SubModel::getProperties()
{
  return this->properties;
}

void SubModel::setProperties(std::unordered_map<std::string, std::shared_ptr<IProperty>> properties)
{
  this->properties = properties;
}
		
std::unordered_map<std::string, std::shared_ptr<IOperation>> SubModel::getOperations()
{
  return this->operations;
}

void SubModel::setOperations(std::unordered_map<std::string, std::shared_ptr<IOperation>> operations)
{
  this->properties = properties;
}
		
std::unordered_map<std::string, basyx::any> SubModel::getElements()
{
  return this->submodel_elements;
}