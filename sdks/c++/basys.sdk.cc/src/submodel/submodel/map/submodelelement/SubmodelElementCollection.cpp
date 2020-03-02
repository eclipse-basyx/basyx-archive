/*
 * SubmodelElementCollection.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/submodelelement/SubmodelElementCollection.h>
#include <BaSyx/submodel/api/submodelelement/property/IProperty.h>
#include <BaSyx/submodel/api/ISubModel.h>

namespace basyx {
namespace submodel {

SubmodelElementCollection::SubmodelElementCollection() :
  vab::ElementMap{},
  ModelType{ISubmodelElementCollection::Path::ModelType}
{}

SubmodelElementCollection::SubmodelElementCollection(const basyx::specificCollection_t<ISubmodelElement>& value, const bool ordered, const bool allowDuplicates) :
  vab::ElementMap{},
  ModelType{ISubmodelElementCollection::Path::ModelType}
{
	this->setValue(value);
	this->setOrdered(ordered);
	this->setAllowDuplicates(allowDuplicates);
}

void SubmodelElementCollection::setValue(const basyx::specificCollection_t<ISubmodelElement> & value)
{
  //auto obj_list = vab::ElementMap::make_object_list<ISubmodelElement, SubmodelElement>(value);
  //this->map.insertKey(IProperty::Path::Value, obj_list);
}

basyx::specificCollection_t<ISubmodelElement> SubmodelElementCollection::getValue() const
{
  basyx::specificCollection_t<ISubmodelElement> list;
  auto obj_list = this->map.getProperty(IProperty::Path::Value).Get<basyx::object::object_list_t>();
  for ( auto & elem : obj_list )
  {
    list.push_back(std::make_shared<SubmodelElement>(elem));
  }
  return list;
}

void SubmodelElementCollection::setOrdered(const bool & ordered)
{
  this->map.insertKey(ISubmodelElementCollection::Path::Ordered, ordered, true);
}

bool SubmodelElementCollection::isOrdered() const
{
  return this->map.getProperty(ISubmodelElementCollection::Path::Ordered).Get<bool>();
}

void SubmodelElementCollection::setAllowDuplicates(const bool & allowDuplicates)
{
  this->map.insertKey(ISubmodelElementCollection::Path::AllowDuplicates, allowDuplicates, true);
}

bool SubmodelElementCollection::isAllowDuplicates() const
{
  return this->map.getProperty(ISubmodelElementCollection::Path::AllowDuplicates).Get<bool>();
}

void SubmodelElementCollection::setElements(const basyx::specificMap_t<ISubmodelElement> & value)
{
  basyx::object map = basyx::object::make_map();
  for ( auto & elem : value )
  {
	auto obj = SubmodelElement{*elem.second}.getMap();
	map.insertKey(elem.first, obj, true);
  }
  this->map.insertKey(ISubModel::Path::Submodelelement, map);
}

basyx::specificMap_t<ISubmodelElement> SubmodelElementCollection::getElements() const
{
  basyx::specificMap_t<ISubmodelElement> specific_map;
  auto obj_map = this->map.getProperty(ISubModel::Path::Submodelelement).Get<object::object_map_t>();
  for ( auto & elem : obj_map )
  {
    auto submodel_elem = std::make_shared<SubmodelElement>(SubmodelElement{elem.second});
    specific_map.insert(std::pair<std::string, std::shared_ptr<ISubmodelElement>>(elem.first, submodel_elem));
  }

  return specific_map;
}

void SubmodelElementCollection::addSubmodelElement(const ISubmodelElement & element)
{

}



}
}