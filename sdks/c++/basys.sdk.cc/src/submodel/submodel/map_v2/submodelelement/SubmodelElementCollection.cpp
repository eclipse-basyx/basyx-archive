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
};


api::IElementContainer<ISubmodelElement> & SubmodelElementCollection::getSubmodelElements()
{
	return this->elementContainer;
};

bool SubmodelElementCollection::isOrdered() const
{
	return this->map.getProperty(Path::Ordered).Get<bool>();
};

bool SubmodelElementCollection::isAllowDuplicates() const
{
	return this->map.getProperty(Path::AllowDuplicates).Get<bool>();
};