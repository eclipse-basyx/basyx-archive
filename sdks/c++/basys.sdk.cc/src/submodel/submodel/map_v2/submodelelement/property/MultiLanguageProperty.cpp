#include <BaSyx/submodel/map_v2/submodelelement/property/MultiLanguageProperty.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::map;

constexpr char MultiLanguageProperty::Path::Value[];
constexpr char MultiLanguageProperty::Path::ValueId[];
constexpr char MultiLanguageProperty::Path::Kind[];

MultiLanguageProperty::MultiLanguageProperty(const std::string & idShort, ModelingKind kind)
	: SubmodelElement(idShort, kind)
{
  this->map.insertKey(Path::Kind, ModelingKind_::to_string(kind));
}

MultiLanguageProperty::MultiLanguageProperty(basyx::object obj)
  : ElementMap{}
  , SubmodelElement(obj)
{
  if ( not obj.getProperty(Path::Value).IsNull() )
  {
    this->value = util::make_unique<LangStringSet>(obj.getProperty(Path::Value));
    this->map.insertKey(Path::Value, this->value->getMap());
  }

  if ( not obj.getProperty(Path::ValueId).IsNull() )
  {
    this->valueId = util::make_unique<Reference>(obj.getProperty(Path::ValueId));
    this->map.insertKey(Path::ValueId, this->valueId->getMap());
  }
}

const api::ILangStringSet * const MultiLanguageProperty::getValue()
{
  if ( this->map.getProperty(Path::Value).IsNull() )
    return nullptr;

	return this->value.get();
}

void MultiLanguageProperty::setValue(std::unique_ptr<LangStringSet> langStringSet)
{
  this->value = std::move(langStringSet);
  this->map.insertKey(Path::Value, this->value->getMap());
}

const api::IReference * const MultiLanguageProperty::getValueId() const
{
  if ( this->map.getProperty(Path::Value).IsNull() )
    return nullptr;
	return this->valueId.get();
}

void MultiLanguageProperty::setValueId(std::unique_ptr<Reference> valueId)
{
	this->valueId = std::move(valueId);
	this->map.insertKey(Path::ValueId, this->valueId->getMap());
}
