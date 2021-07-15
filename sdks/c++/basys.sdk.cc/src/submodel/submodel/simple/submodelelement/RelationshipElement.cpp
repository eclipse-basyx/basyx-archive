#include <BaSyx/submodel/simple/submodelelement/RelationshipElement.h>
#include <BaSyx/submodel/api_v2/qualifier/IReferable.h>


namespace basyx {
namespace submodel {
namespace simple {

RelationshipElement::RelationshipElement(const Reference & first, const Reference & second, const std::string & idShort, ModelingKind kind)
  : SubmodelElement(idShort, kind)
  , first(first)
  , second(second)
{}

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
