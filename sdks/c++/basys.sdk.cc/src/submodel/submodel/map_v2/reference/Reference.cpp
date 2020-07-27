#include <BaSyx/submodel/map_v2/reference/Reference.h>

#include <BaSyx/submodel/enumerations/KeyType.h>
#include <BaSyx/submodel/enumerations/KeyElements.h>

#include <vector>

using namespace basyx::submodel;
using namespace basyx::submodel::map;

struct KeyPath
{
	static constexpr char IdType[] = "idType";
	static constexpr char Type[] = "type";
	static constexpr char Value[] = "value";
	static constexpr char Local[] = "local";
};

constexpr char KeyPath::IdType[];
constexpr char KeyPath::Type[];
constexpr char KeyPath::Value[];
constexpr char KeyPath::Local[];

Reference::Reference() 
	: vab::ElementMap{}
{
	this->map.insertKey("keys", basyx::object::make_object_list());
}

Reference::Reference(const simple::Key & key)
	: Reference{}
{
	this->addKey(key);
};

Reference::Reference(const std::vector<simple::Key> & keys)
	: Reference{}
{
	for (const auto & key : keys)
		this->addKey(key);
};

Reference::Reference(const IReference & other)
	: Reference{other.getKeys()}
{};


Reference::Reference(basyx::object &object)
  : Reference(keyMapList_to_keyList(object.getProperty("keys").Get<object::object_list_t&>()))
{}


std::vector<simple::Key> Reference::getKeys() const
{
	return this->keyMapList_to_keyList(this->map.getProperty("keys").Get<basyx::object::object_list_t&>());
};


void Reference::addKey(const simple::Key & key)
{
	basyx::object keyMap = basyx::object::make_map();
	keyMap.insertKey(KeyPath::IdType, KeyType_::to_string(key.getIdType()));
	keyMap.insertKey(KeyPath::Type, KeyElements_::to_string(key.getType()));
	keyMap.insertKey(KeyPath::Value, key.getValue());
	keyMap.insertKey(KeyPath::Local, key.isLocal());
	this->map.getProperty("keys").insert(keyMap);
}

Reference & Reference::operator=(const api::IReference & other)
{
	this->map.insertKey("keys", basyx::object::make_object_list());

	for (const auto & key : other.getKeys())
		this->addKey(key);

	return *this;
}


bool Reference::empty() const
{
	return this->map.getProperty("keys").empty();
}

simple::Key Reference::keyMap_to_key(basyx::object &keyMap)
{
  return simple::Key
  (
    KeyElements_::from_string(keyMap.getProperty(KeyPath::Type).Get<std::string&>()),
    keyMap.getProperty(KeyPath::Local).Get<bool>(),
    KeyType_::from_string(keyMap.getProperty(KeyPath::IdType).Get<std::string&>()),
    keyMap.getProperty(KeyPath::Value).Get<std::string&>()
  );
}

std::vector<simple::Key> Reference::keyMapList_to_keyList(basyx::object::object_list_t &keyMapList)
{
  std::vector<simple::Key> keys;

  for (auto & keyMap : keyMapList)
  {
    keys.emplace_back(keyMap_to_key(keyMap));
  };

  return keys;
}

bool Reference::operator!=(const Reference & other) const
{
  return this->getKeys() != other.getKeys();
};

bool Reference::operator!=(const api::IReference & other) const
{
  return this->getKeys() != other.getKeys();
};

