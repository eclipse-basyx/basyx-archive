#include <BaSyx/submodel/map_v2/SubModel.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::map;

SubModel::SubModel(const std::string & idShort, const simple::Identifier & identifier)
	: Identifiable(idShort, identifier)
{
	this->map.insertKey("submodelElements", this->elementContainer.getMap());
	this->map.insertKey("semanticId", semanticId.getMap());
};

IElementContainer<ISubmodelElement> & SubModel::submodelElements()
{
	return this->elementContainer;
}

Kind SubModel::getKind() const
{
	return Kind::Instance;
}


IReference & SubModel::getSemanticId()
{
	return this->semanticId;
}
