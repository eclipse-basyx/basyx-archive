#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::map;

SubmodelElement::SubmodelElement(const std::string & idShort, ModelingKind kind)
	: Referable(idShort, nullptr)
	, HasDataSpecification()
	, semanticId()
	, vab::ElementMap{}
{
	this->map.insertKey("kind", ModelingKind_::to_string(kind));
	this->map.insertKey("semanticId", semanticId.getMap());
}

const api::IReference & SubmodelElement::getSemanticId() const
{
	return this->semanticId;
};

void SubmodelElement::setSemanticId(const api::IReference & semanticId)
{
	this->semanticId = semanticId;
};

ModelingKind SubmodelElement::getKind() const
{
	return ModelingKind_::from_string(this->map.getProperty("kind").GetStringContent());
}