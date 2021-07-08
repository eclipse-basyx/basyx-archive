#include <BaSyx/submodel/simple/submodelelement/property/MultiLanguageProperty.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::simple;

MultiLanguageProperty::MultiLanguageProperty(const std::string & idShort, ModelingKind kind )
	: SubmodelElement(idShort, kind)
{}

const api::ILangStringSet * const MultiLanguageProperty::getValue()
{
	return this->value.get();
}

const api::IReference * const MultiLanguageProperty::getValueId() const
{
	return this->valueId.get();
}

void MultiLanguageProperty::setValueId(std::unique_ptr<Reference> valueId)
{
	this->valueId = std::move(valueId);
}