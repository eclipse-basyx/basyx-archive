/*
 * Reference.cpp
 *
 *      Author: wendel
 */

#include "Reference.h"

#include "submodel/map/reference/Key.h"

namespace basyx {
namespace aas {
namespace reference {
namespace impl {

Reference::Reference() 
	: map{ basyx::object::make_map() }
{
	map.insertKey("keys", basyx::object::make_list<basyx::object>());
}

Reference::Reference(const basyx::specificCollection_t<IKey> & keys)
	: map{ basyx::object::make_map() }
{
	map.insertKey("keys", basyx::object::make_list<basyx::object>());

	for (auto & key : keys)
	{
		basyx::submodel::metamodel::map::reference::Key mapKey{
			key->getType(),
			key->isLocal(),
			key->getValue(),
			key->getidType()
		};

		map.getProperty("keys").insert(mapKey.getMap());
	}
}

Reference::Reference(const basyx::object::object_map_t & reference_map)
{
  auto keys = reference_map.at(internalReferencePaths::KEY);
 // this->keys = keys.Get<basyx::specificCollection_t<IKey>>();
}

const basyx::specificCollection_t<IKey> Reference::getKeys() const
{
	auto & obj_list = this->map.getProperty("keys").Get<basyx::object::object_list_t&>();
	basyx::specificCollection_t<IKey> keys;

	for (auto & obj : obj_list)
	{
		keys.emplace_back(std::make_shared<basyx::submodel::metamodel::map::reference::Key>(obj));
	};

	return keys;
}

void Reference::setKeys(const basyx::specificCollection_t<IKey>& keys)
{
}

}
}
}
}
