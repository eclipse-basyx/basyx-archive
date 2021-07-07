#include <BaSyx/submodel/map_v2/submodelelement/SubmodelElement.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::map;

constexpr char SubmodelElement::Path::Kind[];
constexpr char SubmodelElement::Path::SemanticId[];

SubmodelElement::SubmodelElement(const std::string & idShort, ModelingKind kind)
	: Referable(idShort, nullptr)
	, HasDataSpecification()
	, semanticId()
	, vab::ElementMap{}
{
	this->map.insertKey(Path::Kind, ModelingKind_::to_string(kind));
}

SubmodelElement::SubmodelElement(basyx::object obj)
	: ElementMap{}
	, Qualifiable{obj}
	, HasDataSpecification{obj}
	, Referable{obj}
{
	this->map.insertKey(Path::Kind, obj.getProperty(Path::Kind).GetStringContent());

	if ( not obj.getProperty(Path::SemanticId).IsNull() )
  {
	  this->semanticId = util::make_unique<Reference>(obj.getProperty(Path::SemanticId));
	  this->map.insertKey(Path::SemanticId, this->semanticId->getMap());
  }
}

const api::IReference * SubmodelElement::getSemanticId() const
{
	return this->semanticId.get();
}

void SubmodelElement::setSemanticId(std::unique_ptr<Reference> semanticId)
{
	this->semanticId = std::move(semanticId);
	this->map.insertKey(Path::SemanticId, this->semanticId->getMap());
}

ModelingKind SubmodelElement::getKind() const
{
	return ModelingKind_::from_string(this->map.getProperty(Path::Kind).GetStringContent());
}
