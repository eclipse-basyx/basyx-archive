#include "Reference.h"
/*
 * Reference.cpp
 *
 *      Author: wendel
 */

#include "Reference.h"

#include "submodel/map/reference/Key.h"

namespace basyx {
namespace submodel {

Reference::Reference() 
	: vab::ElementMap{}
{
	map.insertKey("keys", basyx::object::make_list<basyx::object>());
}

Reference::Reference(const basyx::specificCollection_t<IKey> & keys)
	: vab::ElementMap{}
{
	this->setKeys(keys);
}

Reference::Reference(const std::initializer_list<Key> keys)
	: vab::ElementMap{}
{
	auto list = basyx::object::make_list<basyx::object>();

	for (auto & key : keys)
	{
		list.insert(key.getMap());
	}

	map.insertKey("keys", list);
}

Reference::Reference(const std::shared_ptr<IReference> reference)
  : vab::ElementMap{}
{
  this->setKeys(reference->getKeys());
}

Reference::Reference(const IReference & reference) :
  vab::ElementMap{}
{
  this->setKeys(reference.getKeys());
}

Reference::Reference(basyx::object object)
	: vab::ElementMap{ object }
{
}

const basyx::specificCollection_t<IKey> Reference::getKeys() const
{
	auto & obj_list = this->map.getProperty("keys").Get<basyx::object::object_list_t&>();
	basyx::specificCollection_t<IKey> keys;

	for (auto & obj : obj_list)
	{
		keys.emplace_back(std::make_shared<Key>(obj));
	};

	return keys;
}

void Reference::setKeys(const basyx::specificCollection_t<IKey>& keys)
{
	auto list = basyx::object::make_list<basyx::object>();

	for (auto & key : keys)
	{
		Key newKey{
			key->getType(),
			key->isLocal(),
			key->getValue(),
			key->getidType()
		};

		list.insert(newKey.getMap());
	}
	map.insertKey("keys", list);
}

Reference Reference::FromIdentifiable(const std::string & keyElementType, bool local, IIdentifiable & identifiable)
{
	Key key{ keyElementType, local, identifiable.getIdentification()->getId(), identifiable.getIdentification()->getIdType() };
	return Reference{ key };
}

}
}
