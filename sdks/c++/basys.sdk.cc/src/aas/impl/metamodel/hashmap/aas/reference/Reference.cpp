/*
 * Reference.cpp
 *
 *      Author: wendel
 */

#include "Reference.h"

namespace basyx {
namespace aas {
namespace reference {
namespace impl {

Reference::Reference() : 
  keys {}
{}

Reference::Reference(const basyx::specificCollection_t<IKey> & keys) :
  keys {keys}
{}

Reference::Reference(const basyx::object::object_map_t & reference_map)
{
  auto keys = reference_map.at(internalReferencePaths::KEY);
  this->keys = keys.Get<basyx::specificCollection_t<IKey>>();
}

basyx::specificCollection_t<IKey> Reference::getKeys() const
{
  return this->keys;
}

void Reference::setKeys(const basyx::specificCollection_t<IKey>& keys)
{
  this->keys = keys;
}

}
}
}
}
