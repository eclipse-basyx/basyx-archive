#include <BaSyx/submodel/simple/submodelelement/SubmodelElementCollection.h>

using namespace basyx::submodel;
using namespace basyx::submodel::simple;
using namespace basyx::submodel::api;


SubmodelElementCollection::SubmodelElementCollection(const std::string & idShort, ModelingKind kind, bool ordered, bool allowDuplicates)
	: SubmodelElement(idShort, kind)
	, ordered(ordered)
	, allowDuplicates(allowDuplicates)
{
};


api::IElementContainer<ISubmodelElement> & SubmodelElementCollection::getSubmodelElements()
{
	return this->elementContainer;
};

bool SubmodelElementCollection::isOrdered() const
{
	return this->ordered;
};

bool SubmodelElementCollection::isAllowDuplicates() const
{
	return this->allowDuplicates;
};