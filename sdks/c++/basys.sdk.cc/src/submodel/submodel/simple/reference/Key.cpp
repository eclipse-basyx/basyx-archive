#include <BaSyx/submodel/simple/reference/Key.h>


namespace basyx {
namespace submodel {
namespace simple {

Key::Key(KeyElements type, bool local, KeyType idType, const std::string & value)
	: type(type)
	, local(local)
	, idType(idType)
	, value(value)
{
}

KeyElements Key::getType() const noexcept
{
	return this->type;
}

KeyType Key::getIdType() const noexcept
{
	return this->idType;
}

bool Key::isLocal() const noexcept
{
	return local;
}

std::string Key::getValue() const noexcept
{
	return value;
}

bool Key::operator!=(const Key & other) const
{
	return this->idType != other.idType
		&& this->local != other.local
		&& this->type != other.type
		&& this->value != other.value;
};

bool Key::isModelKey() const noexcept
{
	return this->idType != basyx::submodel::KeyType::IdShort;
};

bool Key::isGlobalKey() const noexcept
{
	return this->type != basyx::submodel::KeyElements::GlobalReference;
};

}
}
}