#include <BaSyx/submodel/map_v2/submodelelement/property/ReferenceElement.h>

using namespace basyx::submodel;
using namespace basyx::submodel::api;
using namespace basyx::submodel::map;

constexpr char ReferenceElement::Path::Value[];
constexpr char ReferenceElement::Path::Kind[];

ReferenceElement::ReferenceElement(const std::string & idShort, ModelingKind kind)
	: DataElement(idShort, kind)
{
	this->map.insertKey(Path::Value, object::make_null());
  this->map.insertKey(Path::Kind, ModelingKind_::to_string(kind));
}

ReferenceElement::ReferenceElement(basyx::object obj)
	: ElementMap{}
	, DataElement{obj}
{
	this->map.insertKey(Path::Kind, obj.getProperty(Path::Kind).GetStringContent());

	if ( not obj.getProperty(Path::Value).IsNull() )
	{
		this->value = util::make_unique<Reference>(obj.getProperty(Path::Value));
		this->map.insertKey(Path::Value, this->value->getMap());
	}
}

const api::IReference * const ReferenceElement::getValue() const
{
	if ( this->map.getProperty(Path::Value).IsNull() )
		return nullptr;
	return this->value.get();
}

void ReferenceElement::setValue(const api::IReference & valueId)
{
	this->value = util::make_unique<Reference>( valueId );
	this->map.insertKey(Path::Value, this->value->getMap());
}