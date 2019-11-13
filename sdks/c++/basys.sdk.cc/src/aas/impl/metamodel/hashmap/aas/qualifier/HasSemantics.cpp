/*
 * HasSemantics.cpp
 *
 *      Author: wendel
 */

#include "HasSemantics.h"
#include "impl/metamodel/hashmap/aas/reference/Reference.h"

namespace basyx {
namespace submodel {
namespace metamodel {
namespace map {
namespace qualifier {

using namespace aas::reference;

HasSemantics::HasSemantics() :
  reference(new aas::reference::impl::Reference)
{}

HasSemantics::HasSemantics(const std::shared_ptr<aas::reference::IReference>& reference) :
  reference(reference)
{}

std::shared_ptr<IReference> HasSemantics::getSemanticId() const
{
  return this->reference;
}

void HasSemantics::setSemanticId(const std::shared_ptr<aas::reference::IReference>& reference)
{
  this->reference = reference;
}

}
}
}
}
}