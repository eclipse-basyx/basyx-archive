#include <BaSyx/submodel/simple/submodelelement/RelationshipElement.h>
#include <BaSyx/submodel/api_v2/qualifier/IReferable.h>


namespace basyx {
namespace submodel {
namespace simple {

RelationshipElement::RelationshipElement(const Referable & first, const Referable & second, const std::string & idShort, ModelingKind kind)
  : SubmodelElement(idShort, kind)
  , first(first)
  , second(second)
{}

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
