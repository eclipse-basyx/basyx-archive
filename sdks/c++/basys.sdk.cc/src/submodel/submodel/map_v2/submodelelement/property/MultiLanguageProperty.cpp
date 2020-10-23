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
	this->map.insertKey(Path::Value, value.getMap());
	this->map.insertKey(Path::ValueId, valueId.getMap());
  this->map.insertKey(Path::Kind, ModelingKind_::to_string(kind));
};

api::ILangStringSet & MultiLanguageProperty::getValue()
{
	return this->value;
};

const api::IReference * const MultiLanguageProperty::getValueId() const
{
	return &this->valueId;
};

void MultiLanguageProperty::setValueId(const api::IReference & valueId)
{
	this->valueId = Reference(valueId.getKeys());
};