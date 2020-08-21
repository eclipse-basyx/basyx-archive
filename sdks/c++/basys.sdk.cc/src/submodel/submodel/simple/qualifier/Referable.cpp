#include <BaSyx/submodel/simple/qualifier/Referable.h>
#include <BaSyx/submodel/simple/reference/Reference.h>

using namespace basyx::submodel::simple;
using namespace basyx::submodel::api;

Referable::Referable(const std::string & idShort, Referable * parent)
	: idShort(idShort)
	, parent(parent)
{}

Referable::Referable(const IReferable &other)
  : idShort(other.getIdShort())
  , category(*other.getCategory())
  , description(other.getDescription())
  , parent(other.getParent())
{};

const std::string & Referable::getIdShort() const
{
	return this->idShort;
}

const std::string * const Referable::getCategory() const
{
	if (this->category.empty())
		return nullptr;

	return &this->category;
}

LangStringSet & Referable::getDescription()
{
	return this->description;
}

const LangStringSet & Referable::getDescription() const
{
	return this->description;
}

void Referable::setIdShort(const std::string & idShort)
{
	this->idShort = idShort;
}

void Referable::setCategory(const std::string & category)
{
	this->category = category;
}

IReferable * Referable::getParent() const
{
	return this->parent;
};

void Referable::setParent(IReferable * parent)
{
	this->parent = parent;
};

bool Referable::hasParent() const noexcept
{
	return this->parent != nullptr;
};

bool Referable::hasDescription() const noexcept
{
	return !this->description.empty();
};

bool Referable::hasCategory() const noexcept 
{
	return !this->category.empty();
}

Reference Referable::getReference() const
{
	auto key = simple::Key(KeyElements::AssetAdministrationShell, true, KeyType::IdShort, this->getIdShort());

	if (this->getParent() == nullptr)
		return simple::Reference(key);

	auto reference = this->getParent()->getReference();

	reference.addKey(key);

	return reference;
}

basyx::submodel::KeyElements Referable::getKeyElementType() const
{
	return basyx::submodel::KeyElements::Unknown;
}

basyx::submodel::simple::Key Referable::getKey(bool local) const
{
	return basyx::submodel::simple::Key(this->getKeyElementType(), local, this->getKeyType(), this->getIdShort());
}