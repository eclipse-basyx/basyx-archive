#include <BaSyx/submodel/map_v2/reference/Reference.h>

#include <BaSyx/submodel/api_v2/reference/KeyType.h>
#include <BaSyx/submodel/api_v2/reference/KeyElements.h>

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

const std::vector<simple::Key> Reference::getKeys() const
{
	std::vector<simple::Key> keys;

	auto keyList = this->map.getProperty("keys").Get<basyx::object::object_list_t&>();
	for (auto & keyMap : keyList)
	{
		keys.emplace_back(
			  KeyElementsUtil::fromString(keyMap.getProperty(KeyPath::Type).Get<std::string&>()),
			  keyMap.getProperty(KeyPath::Local).Get<bool>(),
			  KeyTypeUtil::fromString(keyMap.getProperty(KeyPath::IdType).Get<std::string&>()),
			  keyMap.getProperty(KeyPath::Value).Get<std::string&>()
		);
	};

	return keys;
};


void Reference::addKey(const simple::Key & key)
{
	basyx::object keyMap = basyx::object::make_map();
	keyMap.insertKey(KeyPath::IdType, KeyTypeUtil::toString(key.getIdType()));
	keyMap.insertKey(KeyPath::Type, KeyElementsUtil::toString(key.getType()));
	keyMap.insertKey(KeyPath::Value, key.getValue());
	keyMap.insertKey(KeyPath::Local, key.isLocal());
	this->map.getProperty("keys").insert(keyMap);
}