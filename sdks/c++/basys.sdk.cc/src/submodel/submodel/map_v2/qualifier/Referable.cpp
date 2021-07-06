#include <BaSyx/submodel/map_v2/qualifier/Referable.h>
#include <BaSyx/submodel/simple/reference/Reference.h>

using namespace basyx::submodel;
using namespace basyx::submodel::map;
using namespace basyx::submodel::api;

constexpr char Referable::Path::IdShort[];
constexpr char Referable::Path::Category[];
constexpr char Referable::Path::Description[];
constexpr char Referable::Path::Parent[];

Referable::Referable(const std::string & idShort, Referable * parent)
	: parent(parent)
	, vab::ElementMap(basyx::object::make_map())
{
	this->map.insertKey(Path::IdShort, idShort);
	this->map.insertKey(Path::Description, this->description.getMap());
}

const std::string & Referable::getIdShort() const
{
	return this->map.getProperty(Path::IdShort).Get<std::string&>();
}

const std::string * const Referable::getCategory() const
{
	auto category = this->map.getProperty(Path::Category);
	if (category.IsNull())
		return nullptr;

	return &category.Get<std::string&>();
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
	this->map.insertKey(Path::IdShort, idShort);
}

void Referable::setCategory(const std::string & category)
{
	this->map.insertKey(Path::Category, category);
}

void Referable::setParent(IReferable * parent)
{
	this->parent = parent;
};

IReferable * Referable::getParent() const
{
	return this->parent;
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
	return this->map.hasProperty(Path::Category);
};

simple::Reference Referable::getReference() const
{
	auto key = this->getKey();

	if (this->getParent() == nullptr)
		return simple::Reference(key);

	auto reference = this->getParent()->getReference();

	reference.addKey(key);

	return reference;
}

simple::Key Referable::getKey(bool local) const
{
	return simple::Key(this->getKeyElementType(), local, this->getKeyType(), this->getIdShort());
}