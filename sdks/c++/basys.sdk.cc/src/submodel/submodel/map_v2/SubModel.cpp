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
	this->map.insertKey(Path::SemanticId, semanticId.getMap());
  this->map.insertKey(Path::Kind, ModelingKind_::to_string(kind));
};

SubModel::SubModel(object object)
  : ElementMap{}
  , Identifiable{
  object.getProperty(Referable::Path::IdShort).GetStringContent(),
  simple::Identifier(IdentifierType_::from_string(object.getProperty(IdPath::Identifier).getProperty(IdPath::IdType).GetStringContent()), object.getProperty(IdPath::Identifier).getProperty(IdPath::Id).GetStringContent())}
{
  if (not object.getProperty(Path::SubmodelElements).IsNull())
  {

  }
  this->semanticId = Reference{object.getProperty(Path::SemanticId)};

  if ( not this->map.getProperty(IdPath::AdministrativeInformation).IsNull() )
  {
    basyx::object adInObj = object.getProperty(IdPath::AdministrativeInformation);
    auto & version = adInObj.getProperty(AdministrativeInformation::Path::Version).GetStringContent();
    auto & revision = adInObj.getProperty(AdministrativeInformation::Path::Revision).GetStringContent();
    AdministrativeInformation administrativeInformation(version, revision);

    auto list = adInObj.getProperty(HasDataSpecification::Path::DataSpecification).Get<object::object_list_t&>();
    for (auto refObj : list)
      administrativeInformation.addDataSpecification(simple::Reference(Reference(refObj.Get<basyx::object>())));
  }
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

const IReference & SubModel::getSemanticId() const
{
	return this->semanticId;
}

void SubModel::setSemanticId(const IReference & semanticId)
{
	this->semanticId = Reference{semanticId};
  this->map.insertKey(Path::SemanticId, this->semanticId.getMap());
}
