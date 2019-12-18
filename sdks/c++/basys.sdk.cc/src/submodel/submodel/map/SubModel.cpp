/*
 * SubModel.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/SubModel.h>

namespace basyx {
namespace submodel {

SubModel::SubModel() : HasSemantics(),
                       Identifiable(),
                       HasDataSpecification(),
                       HasKind()
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

basyx::specificMap_t<IDataElement> SubModel::getDataElements() const
{
   basyx::log log;
   log.warn("Not implemented");
   return basyx::specificMap_t<IDataElement>();
}

basyx::specificMap_t<IOperation> SubModel::getOperations() const
{
   basyx::log log;
   log.warn("Not implemented");
   return basyx::specificMap_t<IOperation>();
}

void SubModel::addSubModelElement(const std::shared_ptr<ISubmodelElement> &element)
{
    basyx::log log;
    log.warn("Not implemented");
}

}
}
