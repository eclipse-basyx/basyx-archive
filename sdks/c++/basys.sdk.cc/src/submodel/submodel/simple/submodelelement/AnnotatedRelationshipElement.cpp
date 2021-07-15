#include <BaSyx/submodel/simple/submodelelement/AnnotatedRelationshipElement.h>


namespace basyx {
namespace submodel {
namespace simple {

AnnotatedRelationshipElement::AnnotatedRelationshipElement(const Reference & first, const Reference & second, const std::string & idShort, ModelingKind kind)
  : RelationshipElement(first, second, idShort, kind)
{}

const api::IElementContainer<api::IDataElement> & AnnotatedRelationshipElement::getAnnotation() const
{
  return this->annotations;
}

void AnnotatedRelationshipElement::addAnnotation(std::unique_ptr<DataElement> dataElement)
{
  this->annotations.addElement(std::move(dataElement));
}

}
}
}
