#include <BaSyx/submodel/map_v2/submodelelement/AnnotatedRelationshipElement.h>


namespace basyx {
namespace submodel {
namespace map {

constexpr char AnnotatedRelationshipElement::Path::Annotation[];

AnnotatedRelationshipElement::AnnotatedRelationshipElement(const Reference & first, const Reference & second, const std::string & idShort, ModelingKind kind)
  : RelationshipElement(first, second, idShort, kind)
{
  this->map.insertKey(Path::Annotation, this->annotations.getMap());
}

const api::IElementContainer<api::IDataElement> & AnnotatedRelationshipElement::getAnnotation() const
{
  return this->annotations;
}

void AnnotatedRelationshipElement::addAnnotation(std::unique_ptr<DataElement> annotation)
{
  this->annotations.addElement(std::move(annotation));
}

}
}
}
