/*
 * HasDataSpecification.cpp
 *
 *      Author: wendel
 */

#include "HasDataSpecification.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {

using namespace aas::reference;

basyx::specificCollection_t<IReference> HasDataSpecification::getDataSpecificationReferences() const
{
  return this->references;
}

void HasDataSpecification::setDataSpecificationReferences(const basyx::specificCollection_t<aas::reference::IReference> & references)
{
  this->references = references;
}

}
}
}
}
}

