#include <BaSyx/submodel/simple/reference/Reference.h>
#include <BaSyx/submodel/simple/reference/Key.h>
#include <BaSyx/submodel/api_v2/qualifier/IIdentifiable.h>

using namespace basyx::submodel;
using namespace basyx::submodel::simple;
using namespace basyx::submodel::api;


Reference::Reference(const Key & key) 
{
	keyList.emplace_back(key);
}

Reference::Reference(const std::vector<Key> & keys)
	: keyList(keys)
{
}

Reference::Reference(std::vector<Key> && keys)
	: keyList(keys)
{
}

Reference::Reference(const std::initializer_list<Key> keys)
	: keyList(keys)
{
}

Reference::Reference(const IReference & other)
	: keyList(std::move(other.getKeys()))
{
};

//Reference Reference::FromIdentifiable(KeyElements keyElementType, const IIdentifiable & identifiable)
//{
//	return Reference(
//		Key(keyElementType,
//			true,
//			static_cast<KeyType>(identifiable.getIdentification().getIdType()),
//			identifiable.getIdentification().getId()) );
//};

std::vector<Key> Reference::getKeys() const
{
	return keyList;
}

void Reference::addKey(const Key & key)
{
	this->keyList.emplace_back(key);
}

bool Reference::empty() const
{
	return this->keyList.empty();
}

bool Reference::operator!=(const Reference & other) const
{
	return this->keyList != other.keyList;
};

bool Reference::operator!=(const api::IReference & other) const
{
	return this->keyList != other.getKeys();
};

