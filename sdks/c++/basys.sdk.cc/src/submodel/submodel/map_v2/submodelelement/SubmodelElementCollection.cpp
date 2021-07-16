#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElementCollection.h>

using namespace basyx::submodel;
using namespace basyx::submodel::map;
using namespace basyx::submodel::api;

constexpr char SubmodelElementCollection::Path::AllowDuplicates[];
constexpr char SubmodelElementCollection::Path::Ordered[];
constexpr char SubmodelElementCollection::Path::Value[];

SubmodelElementCollection::SubmodelElementCollection(const std::string & idShort, ModelingKind kind, bool ordered, bool allowDuplicates)
	: SubmodelElement(idShort, kind)
{
	this->map.insertKey(Path::AllowDuplicates, allowDuplicates);
	this->map.insertKey(Path::Ordered, ordered);
	this->map.insertKey(Path::Value, this->elementContainer.getMap());
}

SubmodelElementCollection::SubmodelElementCollection(basyx::object obj)
	: SubmodelElement{ obj }
{
	if ( not obj.getProperty(Path::AllowDuplicates).IsNull() )
		this->map.insertKey(Path::AllowDuplicates, obj.getProperty(Path::AllowDuplicates).Get<bool>());
	else
		this->map.insertKey(Path::AllowDuplicates, false);

	if ( not obj.getProperty(Path::Ordered).IsNull() )
		this->map.insertKey(Path::Ordered, obj.getProperty(Path::Ordered).Get<bool>());
	else
		this->map.insertKey(Path::Ordered, false);

	if ( not obj.getProperty(Path::Value).IsNull() )
	{
		auto obj_list = obj.getProperty(Path::Value).Get<object::object_map_t>();
		for ( auto elem : obj_list )
		{
			this->elementContainer.addElement(SubmodelElementFactory::Create(elem.second));
		}
    this->map.insertKey(Path::Value, this->elementContainer.getMap());
	}
}

api::IElementContainer<ISubmodelElement> & SubmodelElementCollection::getSubmodelElements()
{
	return this->elementContainer;
}

bool SubmodelElementCollection::isOrdered() const
{
	return this->map.getProperty(Path::Ordered).Get<bool>();
}

bool SubmodelElementCollection::isAllowDuplicates() const
{
	return this->map.getProperty(Path::AllowDuplicates).Get<bool>();
}

void SubmodelElementCollection::addElement(std::unique_ptr<SubmodelElement> submodelElement)
{
  this->elementContainer.addElement(std::move(submodelElement));
}
