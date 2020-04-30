#include <BaSyx/submodel/map_v2/qualifier/HasDataSpecification.h>

#include <BaSyx/submodel/simple/reference/Reference.h>
#include <BaSyx/submodel/map_v2/reference/Reference.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::map;

HasDataSpecification::HasDataSpecification()
	: vab::ElementMap()
	, dataSpecification()
{
	this->map.insertKey("dataSpecification", basyx::object::make_object_ref(&dataSpecification));
}

void HasDataSpecification::addDataSpecification(const simple::Reference & reference)
{
	map::Reference ref;
	for (const auto & key : reference.getKeys()) {
		ref.addKey(key);
	}

	this->dataSpecification.emplace_back(ref.getMap());
};

const std::vector<simple::Reference> HasDataSpecification::getDataSpecificationReference() const
{
	std::vector<simple::Reference> dataSpecs;

	for (auto & refObj : dataSpecification) {
		map::Reference mapRef{ refObj };
		dataSpecs.emplace_back(mapRef.getKeys());
	};

	return dataSpecs;
}