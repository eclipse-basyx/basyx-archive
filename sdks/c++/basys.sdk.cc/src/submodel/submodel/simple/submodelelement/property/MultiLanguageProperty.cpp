#include <BaSyx/submodel/simple/submodelelement/property/MultiLanguageProperty.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::simple;

MultiLanguageProperty::MultiLanguageProperty(const std::string & idShort, Kind kind )
	: SubmodelElement(idShort, kind)
{

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
	this->valueId = Reference( valueId.getKeys() );
};