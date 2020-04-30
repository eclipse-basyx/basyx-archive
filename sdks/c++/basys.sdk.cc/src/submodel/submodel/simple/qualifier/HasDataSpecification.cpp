#include <BaSyx/submodel/simple/qualifier/HasDataSpecification.h>

#include <BaSyx/submodel/simple/reference/Reference.h>

namespace basyx {
namespace submodel {
namespace simple {

HasDataSpecification::HasDataSpecification()
{
}

void HasDataSpecification::addDataSpecification(const Reference & reference)
{
	this->dataSpecification.emplace_back(reference);
};

const std::vector<Reference> HasDataSpecification::getDataSpecificationReference() const
{
	return this->dataSpecification;
}

}
}
}
