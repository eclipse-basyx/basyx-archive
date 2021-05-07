#include <BaSyx/submodel/map_v2/submodelelement/RelationshipElement.h>


namespace basyx {
namespace submodel {
namespace map {

constexpr char RelationshipElement::Path::First[];
constexpr char RelationshipElement::Path::Second[];

RelationshipElement::RelationshipElement(const Referable & first, const Referable & second, const std::string & idShort, ModelingKind kind)
  : SubmodelElement(idShort, kind)
  , first(first)
  , second(second)
{
  this->map.insertKey(Path::First, first.getMap());
  this->map.insertKey(Path::Second, second.getMap());
}

const api::IReferable & RelationshipElement::getFirst() const
{
  return first;
}

const api::IReferable & RelationshipElement::getSecond() const
{
  return second;
}

}
}
}
