#include <BaSyx/submodel/map_v2/qualifier/Referable.h>
#include <BaSyx/submodel/map_v2/reference/Reference.h>

using namespace basyx::submodel::map;
using namespace basyx::submodel::api;

Referable::Referable(const std::string & idShort, const Referable * parent)
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

const IReferable * const Referable::getParent() const
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