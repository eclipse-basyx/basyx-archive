/*
 * HasKind.cpp
 *
 *      Author: wendel
 */

#include <BaSyx/submodel/map/qualifier/HasKind.h>

using namespace basyx::submodel;

HasKind::HasKind(Kind kind) 
	: vab::ElementMap()
{
	this->Init(kind);
}

void HasKind::Init(Kind kind)
{
	this->map.insertKey(Path::Kind, util::to_string(kind));
};

HasKind::HasKind(basyx::object object)
	: vab::ElementMap(object)
{
}

basyx::submodel::HasKind::HasKind(const IHasKind & other) :
  vab::ElementMap{}
{
  this->setHasKindReference(other.getHasKindReference());
}

Kind HasKind::getHasKindReference() const
{
	return util::from_string<Kind>(this->map.getProperty(Path::Kind).GetStringContent());
}

void HasKind::setHasKindReference(Kind kind)
{
	this->map.insertKey(Path::Kind, util::to_string(kind), true);
}

static const std::string kind_to_string[] = {
	"NotSpecified",
	"Type",
	"Instance"
};

static const std::unordered_map<std::string, Kind> string_to_kind{
	{ kind_to_string[static_cast<char>(Kind::Instance)] , Kind::Instance },
	{ kind_to_string[static_cast<char>(Kind::Type)] , Kind::Type },
	{ kind_to_string[static_cast<char>(Kind::NotSpecified)] , Kind::NotSpecified }
};

const std::string & util::to_string(Kind kind)
{
	return kind_to_string[static_cast<char>(kind)];
}

template<>
Kind util::from_string<Kind>(const std::string & str)
{
	return string_to_kind.at(str);
};
