/*
 * ConnectedPropertyFactory.h
 *
 *      Author: wendel
 */

#ifndef AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDPROPERTYFACTORY_H_
#define AAS_BACKEND_SUBMODELELEMENT_PROPERTY_CONNECTEDPROPERTYFACTORY_H_

#include "submodel/api/submodelelement/property/IProperty.h"
#include "vab/core/proxy/IVABElementProxy.h"
#include "submodel/api/ISubModel.h"
#include "submodel/connected/submodelelement/property/ConnectedContainerProperty.h"
#include "submodel/connected/submodelelement/property/ConnectedMapProperty.h"
#include "submodel/connected/submodelelement/property/ConnectedSingleProperty.h"
#include "submodel/api/submodelelement/property/ISingleProperty.h"
#include "submodel/map/submodelelement/property/valuetypedef/PropertyValueTypeDef.h"


namespace basyx {
namespace submodel {
namespace backend {
namespace connected {
namespace support {

using namespace submodelelement::property;

namespace ConnectedPropertyFactory {
// Forward declaration
static std::shared_ptr<ConnectedProperty> createSuitableProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy, basyx::object::object_map_t & originalPropertyMap, basyx::object::object_map_t & valueTypeMap);
static std::shared_ptr<ConnectedProperty> createSingleProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy, basyx::object::object_map_t & originalPropertyMap);
static std::shared_ptr<ConnectedProperty> createProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy)
{
  auto property = proxy->readElementValue("").Get<basyx::object::object_map_t>();


  // todo
  //if ( property.find(api::ISubModel::SubmodelPaths::PROPERTIES) != property.end() )
  //{
  //  return std::make_shared<ConnectedContainerProperty>(proxy);
  //}

  auto valueTypePtr = property.find(IProperty::Path::ValueType);
  // Check if valueType is set
  if ( valueTypePtr != property.end() )
  {
    auto valueType = valueTypePtr->second.Get<basyx::object::object_map_t>();
    return createSuitableProperty(proxy, property, valueType);
  }

  // Property with no value type set
  if ( property.find(IProperty::Path::Value) != property.end() and property.find(IReferable::Path::IdShort) != property.end() )
  {
    return createSingleProperty(proxy, property);
  }

  // If nothing suits return null
  return nullptr;
}

static std::shared_ptr<ConnectedProperty> createSuitableProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy, basyx::object::object_map_t & originalPropertyMap, basyx::object::object_map_t & valueTypeMap)
{
  auto valueType_typeObject = valueTypeMap.at(impl::metamodel::PropertyValueTypeIdentifier::TYPE_OBJECT).Get<basyx::object::object_map_t>();
  auto propertyValueTypeName = valueType_typeObject.at(impl::metamodel::PropertyValueTypeIdentifier::TYPE_NAME).GetStringContent();

  // if map -> create map todo
  //if ( propertyValueTypeName.compare(impl::metamodel::PropertyValueTypeDef::Map) == 0 )
  //{
  //  return std::make_shared<ConnectedMapProperty>(proxy);
  //}

  // if collection -> create collection todo
  //if ( propertyValueTypeName.compare(impl::metamodel::PropertyValueTypeDef::Collection) == 0 )
  //{
  //  return std::make_shared<ConnectedCollectionProperty>(proxy);
  //}

  // if no map and no collection -> must be single property
  //else
  //{
  //  return createSingleProperty(proxy, originalPropertyMap);
  //}
  return nullptr;
}

static std::shared_ptr<ConnectedProperty> createSingleProperty(std::shared_ptr<vab::core::proxy::IVABElementProxy> proxy, basyx::object::object_map_t & originalPropertyMap)
{
  ConnectedSingleProperty singleProperty(proxy);
  for ( auto & element : originalPropertyMap )
  {
    singleProperty.setLocalValue(element.first, element.second);
  }
  return std::make_shared<ConnectedCollectionProperty>(proxy);
}
}

}
}
}
}
}

#endif
