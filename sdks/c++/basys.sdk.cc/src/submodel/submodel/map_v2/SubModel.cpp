#include <BaSyx/submodel/map_v2/SubModel.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::map;

constexpr char SubModel::Path::SubmodelElements[];
constexpr char SubModel::Path::SemanticId[];
constexpr char SubModel::Path::Kind[];

SubModel::SubModel(const std::string & idShort, const simple::Identifier & identifier, ModelingKind kind)
	: Identifiable(idShort, identifier)
{
	this->map.insertKey(Path::SubmodelElements, this->elementContainer.getMap());
	this->map.insertKey(Path::SemanticId, semanticId.getMap());
  this->map.insertKey(Path::Kind, ModelingKind_::to_string(kind));
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
	return ModelingKind_::from_string(this->map.getProperty(Path::Kind).GetStringContent());
}

const IReference & SubModel::getSemanticId() const
{
	return this->semanticId;
}

void SubModel::setSemanticId(const IReference & semanticId)
{
	this->semanticId = semanticId;
}
