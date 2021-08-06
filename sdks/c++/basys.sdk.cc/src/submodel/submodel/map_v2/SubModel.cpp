#include <BaSyx/submodel/map_v2/SubModel.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::map;

using IdPath = Identifiable::Path;

constexpr char SubModel::Path::SubmodelElements[];
constexpr char SubModel::Path::SemanticId[];
constexpr char SubModel::Path::Kind[];

SubModel::SubModel(const std::string & idShort, const simple::Identifier & identifier, ModelingKind kind)
	: Identifiable(idShort, identifier)
{
	this->map.insertKey(Path::SubmodelElements, this->elementContainer.getMap());
  this->map.insertKey(Path::Kind, ModelingKind_::to_string(kind));
}

SubModel::SubModel(basyx::object object)
  : ElementMap{}
  , Identifiable{
  object.getProperty(Referable::Path::IdShort).GetStringContent(),
  simple::Identifier(IdentifierType_::from_string(object.getProperty(IdPath::Identifier).getProperty(IdPath::IdType).GetStringContent()),
                     object.getProperty(IdPath::Identifier).getProperty(IdPath::Id).GetStringContent())}
  , HasDataSpecification{object}
  , Qualifiable{object}
{
  if ( not object.getProperty(Path::SubmodelElements).IsNull() )
  {
    auto elements = object.getProperty(Path::SubmodelElements).Get<object::object_map_t>();
    for ( auto element : elements )
    {
      auto submodelElement = SubmodelElementFactory::Create(element.second);
      this->elementContainer.addElement(std::move(submodelElement));
    }
  }

  if ( not object.getProperty(Path::SemanticId).IsNull() )
    this->semanticId = util::make_unique<Reference>(object.getProperty(Path::SemanticId));

  if ( not object.getProperty(IdPath::AdministrativeInformation).IsNull() )
  {
    AdministrativeInformation administrativeInformation(object.getProperty(IdPath::AdministrativeInformation));
    this->setAdministrativeInformation(administrativeInformation);
  }

  this->setCategory(object.getProperty(Referable::Path::Category).GetStringContent());
  this->setDescription(LangStringSet::from_object(object.getProperty(Referable::Path::Description)));
}

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

const IReference * SubModel::getSemanticId() const
{
	return this->semanticId.get();
}

void SubModel::setSemanticId(std::unique_ptr<Reference> semanticId)
{
	this->semanticId = std::move(semanticId);
  this->map.insertKey(Path::SemanticId, this->semanticId->getMap());
}
