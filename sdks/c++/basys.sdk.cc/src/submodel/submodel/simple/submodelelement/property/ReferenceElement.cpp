#include <BaSyx/submodel/simple/submodelelement/property/ReferenceElement.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::simple;

ReferenceElement::ReferenceElement(const std::string & idShort, ModelingKind kind)
	: DataElement(idShort, kind)
{}

const api::IReference * const ReferenceElement::getValue() const
{
	return &this->value;
};

void ReferenceElement::setValue(const api::IReference & valueId)
{
	this->value = Reference( valueId.getKeys() );
};