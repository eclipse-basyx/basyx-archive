#include <BaSyx/submodel/map_v2/SubModel.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::map;

SubModel::SubModel(const std::string & idShort, const simple::Identifier & identifier, ModelingKind kind)
	: Identifiable(idShort, identifier)
{
	this->map.insertKey("submodelElements", this->elementContainer.getMap());
	this->map.insertKey("semanticId", semanticId.getMap());
  this->map.insertKey("kind", ModelingKind_::to_string(kind));
};

IElementContainer<ISubmodelElement> & SubModel::submodelElements()
{
	return this->elementContainer;
}

const IElementContainer<ISubmodelElement> & SubModel::submodelElements() const
{
	return this->elementContainer;
}

ModelingKind SubModel::getKind() const
{
	return ModelingKind_::from_string(this->map.getProperty("kind").GetStringContent());
}

const IReference & SubModel::getSemanticId() const
{
	return this->semanticId;
}

void SubModel::setSemanticId(const IReference & semanticId)
{
	this->semanticId = semanticId;
}
