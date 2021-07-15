#include <BaSyx/submodel/map_v2/submodelelement/RelationshipElement.h>


namespace basyx {
namespace submodel {
namespace map {

constexpr char RelationshipElement::Path::First[];
constexpr char RelationshipElement::Path::Second[];

RelationshipElement::RelationshipElement(const Reference & first, const Reference & second, const std::string & idShort, ModelingKind kind)
  : SubmodelElement(idShort, kind)
  , first(first)
  , second(second)
{
  this->map.insertKey(Path::First, first.getMap());
  this->map.insertKey(Path::Second, second.getMap());
}

RelationshipElement::RelationshipElement(basyx::object obj)
  : SubmodelElement{obj}
  , first{obj.getProperty(Path::First)}
  , second{obj.getProperty(Path::Second)}
{
  this->map.insertKey(Path::First, this->first.getMap());
  this->map.insertKey(Path::Second, this->second.getMap());
}

const Reference & RelationshipElement::getFirst() const
{
  return first;
}

const Reference & RelationshipElement::getSecond() const
{
  return second;
}

}
}
}
