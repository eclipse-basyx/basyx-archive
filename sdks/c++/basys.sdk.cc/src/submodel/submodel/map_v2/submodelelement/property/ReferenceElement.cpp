#include <BaSyx/submodel/map_v2/submodelelement/property/ReferenceElement.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::map;

constexpr char ReferenceElement::Path::Value[];
constexpr char ReferenceElement::Path::Kind[];

ReferenceElement::ReferenceElement(const std::string & idShort, ModelingKind kind)
	: SubmodelElement(idShort, kind)
{
	this->map.insertKey(Path::Value, value.getMap());
  this->map.insertKey(Path::Kind, ModelingKind_::to_string(kind));
};

const api::IReference * const ReferenceElement::getValue() const
{
	return &this->value;
};

void ReferenceElement::setValue(const api::IReference & valueId)
{
	this->value = Reference( valueId.getKeys() );
};