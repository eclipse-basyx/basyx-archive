/*
 * Referable.cpp
 *
 *      Author: wendel
 */

#include "Referable.h"
#include "submodel/map/reference/Reference.h"

namespace basyx {
namespace submodel {

Referable::Referable()
  : vab::ElementMap{}
{
}

basyx::submodel::Referable::Referable(basyx::object & obj)
	: vab::ElementMap{ obj }
{
	this->map.insertKey(Path::IdShort, "");
	this->map.insertKey(Path::Category, "");
	this->map.insertKey(Path::Description, Description{}.getMap());
	this->map.insertKey(Path::Parent, basyx::object::make_null());
}

Referable::Referable(const std::string & idShort, const std::string & category, const Description & description) :
  vab::ElementMap{}
{
	this->map.insertKey(Path::IdShort, idShort);
	this->map.insertKey(Path::Category, category);
	this->map.insertKey(Path::Description, description.getMap());
	this->map.insertKey(Path::Parent, basyx::object::make_null());
}

Referable::Referable(const IReferable & other) :
  vab::ElementMap{}
{
  this->setIdShort(other.getIdShort());
  this->setCategory(other.getCategory());
  this->setDescription(other.getDescription());
  this->setParent(other.getParent());
}

Referable::Referable(const std::shared_ptr<IReferable>& other)

{
  this->setIdShort(other->getIdShort());
  this->setCategory(other->getCategory());
  this->setDescription(other->getDescription());
  this->setParent(other->getParent());
}

std::string Referable::getIdShort() const
{
	return this->map.getProperty(Path::IdShort).GetStringContent();
}

std::string Referable::getCategory() const
{
	return this->map.getProperty(Path::Category).GetStringContent();
}

Description Referable::getDescription() const
{
	return Description{ this->map.getProperty(Path::Description) };
}

std::shared_ptr<IReference> Referable::getParent() const
{
	return std::make_shared<Reference>(this->map.getProperty(Path::Parent));
}

void Referable::setIdShort(const std::string & shortID)
{
	this->map.insertKey(Path::IdShort, shortID, true);
}

void Referable::setCategory(const std::string & category)
{
	this->map.insertKey(Path::Category, category, true);
}

void Referable::setDescription(const Description & description)
{
	this->map.insertKey(Path::Description, description.getMap(), true);
}

void Referable::setParent(const std::shared_ptr<IReference> & parentReference)
{
  this->insertMapElement(Path::Parent, Reference{parentReference});
}

}
}
