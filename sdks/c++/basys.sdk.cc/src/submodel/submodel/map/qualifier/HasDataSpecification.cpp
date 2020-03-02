/*
 * HasDataSpecification.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/qualifier/HasDataSpecification.h>

#include <BaSyx/submodel/map/reference/Reference.h>

namespace basyx {
namespace submodel {

HasDataSpecification::HasDataSpecification()
	: vab::ElementMap{}
{
	this->map.insertKey(Path::HasDataSpecification, basyx::object::make_list<basyx::object>());
}

HasDataSpecification::HasDataSpecification(basyx::object & obj)
	: vab::ElementMap{obj}
{

}

HasDataSpecification::HasDataSpecification(const basyx::specificCollection_t<IReference>& refs)
	: vab::ElementMap{}
{
	this->setDataSpecificationReferences(refs);
}

HasDataSpecification::HasDataSpecification(const IHasDataSpecification & hasDataSpecification)
  : vab::ElementMap{}
{
  this->setDataSpecificationReferences(hasDataSpecification.getDataSpecificationReferences());
}

basyx::specificCollection_t<IReference> HasDataSpecification::getDataSpecificationReferences() const
{
	auto obj_list = this->map.getProperty(Path::HasDataSpecification).Get<basyx::object::object_list_t&>();
	return vab::ElementMap::make_specific_collection<IReference, Reference>(obj_list);
}

void HasDataSpecification::setDataSpecificationReferences(const basyx::specificCollection_t<IReference> & references)
{
	auto list = basyx::object::make_list<basyx::object>();

	for (const auto & ref : references)
	{
		Reference reference{ ref->getKeys() };
		list.insert(reference.getMap());
	};

	this->map.insertKey(Path::HasDataSpecification, list, true);
}

}
}
